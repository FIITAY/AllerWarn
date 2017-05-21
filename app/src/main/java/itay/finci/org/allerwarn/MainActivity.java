package itay.finci.org.allerwarn;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import itay.finci.org.allerwarn.NFC.NFCManager;
import itay.finci.org.allerwarn.fragments.AddAlergyFragment;
import itay.finci.org.allerwarn.fragments.EditUserFragment;
import itay.finci.org.allerwarn.fragments.MainScreenFragment;
import itay.finci.org.allerwarn.fragments.NewUserFragment;
import itay.finci.org.allerwarn.user.User;
import itay.finci.org.allerwarn.user.UserList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,FragmentChangeListener{
    TextView tvVersion;
    private NdefMessage message = null;
    private NFCManager nfcMger;
    private ProgressDialog dialog;
    Tag currentTag;
    private View v;

    /**
     * setting the nfcManager,the progress dialog, reading , and fragment
     * @param savedInstanceState automatic get
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nfcMger = new NFCManager(this);
        v = findViewById(R.id.drawer_layout);
        dialog = new ProgressDialog(MainActivity.this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        tvVersion= (TextView)header.findViewById(R.id.tvVersion);
        setVersionCodeInTV();

        read();

        if(findViewById(R.id.fragment_cointainer) != null){

            if(savedInstanceState != null){
                return;
            }

            MainScreenFragment msf = new MainScreenFragment();

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_cointainer, msf, null).commit();
        }
        UserList ul = UserList.getInstance();
        if(ul.size() <1){
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_EditUser).setVisible(false);
            nav_Menu.findItem(R.id.nav_addAler).setVisible(false);
            nav_Menu.findItem(R.id.nav_nfcWrite).setVisible(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            nfcMger.verifyNFC();
            //nfcMger.enableDispatch();

            Intent nfcIntent = new Intent(this, getClass());
            nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, nfcIntent, 0);
            IntentFilter[] intentFiltersArray = new IntentFilter[] {};
            String[][] techList = new String[][] { { android.nfc.tech.Ndef.class.getName() }, { android.nfc.tech.NdefFormatable.class.getName() } };
            NfcAdapter nfcAdpt = NfcAdapter.getDefaultAdapter(this);
            nfcAdpt.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techList);
        }
        catch(NFCManager.NFCNotSupported nfcnsup) {
            Snackbar.make(v, "NFC not supported", Snackbar.LENGTH_LONG).show();
        }
        catch(NFCManager.NFCNotEnabled nfcnEn) {
            Snackbar.make(v, "NFC Not enabled", Snackbar.LENGTH_LONG).show();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        nfcMger.disableDispatch();
    }

    /**
     * getting the nfc intent and writing if there is message to write or reading if there is no message to write
     * @param intent get it automatic
     */
    @Override
    public void onNewIntent(Intent intent) {
        Log.d("Nfc", "New intent");
        // It is the time to write the tag
        currentTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (message != null) {
            nfcMger.writeTag(currentTag, message);
            if(dialog.isShowing()){
                dialog.dismiss();
            }
            Snackbar.make(v, "Tag written", Snackbar.LENGTH_LONG).show();
            message=null;
        }else{
            Ndef ndef = Ndef.get(currentTag);
            if (ndef == null) {
                // NDEF is not supported by this Tag.
                return;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        //Snackbar.make(v, readText(ndefRecord), Snackbar.LENGTH_LONG).show();
                        String user= readText(ndefRecord);
                        UserList.getInstance().add(user, getApplicationContext());
                        MainScreenFragment msf = new MainScreenFragment();
                        this.replaceFragment(msf);
                        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                        Menu nav_Menu = navigationView.getMenu();
                        nav_Menu.findItem(R.id.nav_EditUser).setVisible(true);
                        nav_Menu.findItem(R.id.nav_addAler).setVisible(true);
                        nav_Menu.findItem(R.id.nav_nfcWrite).setVisible(true);
                       // msf.refresh();
                    } catch (UnsupportedEncodingException e) {
                        Log.e("TAG", "Unsupported Encoding", e);
                    }
                }
            }
            if(dialog.isShowing()){
                dialog.dismiss();
            }
            Snackbar.make(v, "Tag read", Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * read the text out of the ndef record to a string
     * @param record ndef record
     * @return string content of record
     * @throws UnsupportedEncodingException
     */
    private String readText(NdefRecord record) throws UnsupportedEncodingException {
        /*
         * See NFC forum specification for "Text Record Type Definition" at 3.2.1
         *
         * http://www.nfc-forum.org/specs/
         *
         * bit_7 defines encoding
         * bit_6 reserved for future use, must be 0
         * bit_5..0 length of IANA language code
         */

        byte[] payload = record.getPayload();

        // Get the Text Encoding
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

        // Get the Language Code
        int languageCodeLength = payload[0] & 0063;

        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
        // e.g. "en"

        // Get the Text
        return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
    }

    /**
     * reading the userlist file to import all users
     */
    private void read(){
        try {
            UserList u = UserList.getInstance();
            if (u.size() > 0) {
                return;
            }
            FileInputStream fileIn = openFileInput("UserList.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ArrayList<User> alu = (ArrayList<User>) in.readObject();
            for (int i = 0; i < alu.size(); i++) {
                u.add(alu.get(i));
            }
            if(u.size() >0) {
                u.setActiveUserInPosition(0);
            }
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c) {
            System.out.println("UserList class not found");
            c.printStackTrace();
            return;
        }
    }

    /**
     * set the version code in the navBar
     */
    private void setVersionCodeInTV() {
        try {
            String s = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext()
                    .getPackageName(), 0).versionName;
            tvVersion.setText(s);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    MainScreenFragment msf = new MainScreenFragment();
    NewUserFragment nuf = new NewUserFragment();
    EditUserFragment euf = new EditUserFragment();
    AddAlergyFragment aaf = new AddAlergyFragment();

    /**
     * getting witch button you pressed in the nav bar and inflating the fragment
     * @param item get automatic
     * @return true\false
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_newUser) {
            nuf = new NewUserFragment();
            this.replaceFragment(nuf);
        }else if (id == R.id.nav_home) {
            msf = new MainScreenFragment();
            this.replaceFragment(msf);
        }else if (id == R.id.nav_EditUser){
            euf = new EditUserFragment();
            this.replaceFragment(euf);
        }else if (id == R.id.nav_addAler){
            aaf = new AddAlergyFragment();
            this.replaceFragment(aaf);
        }else if( id == R.id.nav_nfcWrite){
            onNFCwrite();
        }else if( id == R.id.nav_nfcRead){
            onNFCRead();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * when you press NFC write button this function is called ( make the massege to write and activate dialog)
     */
    private void onNFCwrite() {
        message =  nfcMger.createTextMessage(UserList.getInstance().getActiveUser().getByteCode());
        if (message != null) {
            dialog.setMessage("Tag NFC Tag please");
            dialog.show();
        }
    }
    /**
     * when you press NFC read button this function is called ( make the massege to null and activate dialog)
     */
    private void onNFCRead(){
        message =null;
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Tag NFC Tag please");
        dialog.show();
    }

    /**
     * replace fragment function
     * @param fragment fragment object to change to
     */
    @Override
    public void replaceFragment(Fragment fragment) {
        //FragmentManager fragmentManager = getSupportFragmentManager();;
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentTransaction ft=getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.fragment_cointainer, fragment, null);
        ft.detach(fragment);
        ft.attach(fragment);
        ft.commit();
    }
}

