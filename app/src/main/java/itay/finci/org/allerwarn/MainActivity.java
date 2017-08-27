package itay.finci.org.allerwarn;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import itay.finci.org.allerwarn.NFC.NFCManager;
import itay.finci.org.allerwarn.fragments.AddAlergyFragment;
import itay.finci.org.allerwarn.fragments.DetailFragment;
import itay.finci.org.allerwarn.fragments.NFCfragment;
import itay.finci.org.allerwarn.intro.IntroActivity;
import itay.finci.org.allerwarn.user.UserList;

public class MainActivity extends AppCompatActivity
        implements FragmentChangeListener {

    public static boolean show = true;
    public static NdefMessage message = null;
    public static NFCManager nfcMger;
    public static ProgressDialog dialog;
    Tag currentTag;
    private View v;
    int NFCSTATUS = 0;


    private final static int NFC_NOT_SUPPORTED = 1;
    private final static int NFC_NOT_ENABLED = 2;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    AddAlergyFragment aaf = new AddAlergyFragment();
                    replaceFragment(aaf);
                    return true;
                case R.id.navigation_dashboard:
                    DetailFragment detailFragment = new DetailFragment();
                    replaceFragment(detailFragment);
                    return true;
                case R.id.navigation_notifications:
                    switch (NFCSTATUS) {
                        case 0:
                            NFCfragment nfcfragment = new NFCfragment();
                            replaceFragment(nfcfragment);
                            break;
                        case NFC_NOT_SUPPORTED:
                            Snackbar.make(getWindow().getDecorView(), "NFC is not supported on your phone", Snackbar.LENGTH_LONG).show();
                            navigation.setSelectedItemId(R.id.navigation_dashboard);
                            break;
                        case NFC_NOT_ENABLED:
                            Snackbar.make(getWindow().getDecorView(), "NFC is not enabled", Snackbar.LENGTH_LONG).show();
                            navigation.setSelectedItemId(R.id.navigation_dashboard);
                            break;
                    }
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onStart() {
        super.onStart();
        show = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        show = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private ViewPager mViewPager;
    BottomNavigationView navigation;

    private void read() {
        try {
            UserList u = UserList.getInstance();
            if (UserList.getInstance().getActiveUser() != null) {
                return;
            }
            File f = new File("UserList.allr");
            byte[] data = new byte[(int) f.length()];
            new FileInputStream(f).read(data);
            String s = new String(data, "UTF-8");
            u.add(s, getApplicationContext());
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dialog = new ProgressDialog(getApplicationContext());
        nfcMger = new NFCManager(this);
        v = findViewById(R.id.content);
        read();
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        if (UserList.getInstance().getActiveUser() == null) {
            getSupportActionBar().hide();
            startActivity(new Intent(this, IntroActivity.class));
        } else {
            getSupportActionBar().show();
            setContentView(R.layout.activity_second_main);
            show = false;
            Bundle arguments = new Bundle();
            arguments.putString(DetailFragment.ARG_ITEM_ID, UserList.getInstance()
                    .getActiveUser().getName());
            DetailFragment df = new DetailFragment();
            df.setArguments(arguments);
            System.out.println(show);
            this.replaceFragment(df);
            navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            navigation.setSelectedItemId(R.id.navigation_dashboard);
            switch (NFCSTATUS) {
                case 0:
                    break;
                case NFC_NOT_SUPPORTED:
                    Snackbar.make(getWindow().getDecorView(), "NFC is not supported on your phone", Snackbar.LENGTH_LONG).show();
                    break;
                case NFC_NOT_ENABLED:
                    Snackbar.make(getWindow().getDecorView(), "NFC is not enabled", Snackbar.LENGTH_LONG).show();
                    break;
            }

        }
    }

    /**
     * replace fragment function
     *
     * @param fragment fragment object to change to
     */
    @Override
    public void replaceFragment(Fragment fragment) {
        //FragmentManager fragmentManager = getSupportFragmentManager();;
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment, null);
        ft.detach(fragment);
        ft.attach(fragment);
        ft.commit();
    }

    public static void rewrite() {
        try {
            FileOutputStream fileOut = new FileOutputStream("UserList.allr");
            fileOut.write(UserList.getInstance().getActiveUser().getByteCode());
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.d("Nfc", "New intent");
        // It is the time to write the tag
        currentTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (message != null) {
            nfcMger.writeTag(currentTag, message);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            DetailFragment detailfragment = new DetailFragment();
            this.replaceFragment(detailfragment);
            navigation.setSelectedItemId(R.id.navigation_dashboard);
            Snackbar.make(getWindow().getDecorView(), "Tag written", Snackbar.LENGTH_LONG).show();
            message = null;
        } else {
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
                        String user = readText(ndefRecord);
                        UserList.getInstance().add(user, getApplicationContext());
                        DetailFragment detailfragment = new DetailFragment();
                        this.replaceFragment(detailfragment);
                        navigation.setSelectedItemId(R.id.navigation_dashboard);
                        // msf.refresh();
                        rewrite();
                    } catch (UnsupportedEncodingException e) {
                        Log.e("TAG", "Unsupported Encoding", e);
                    }
                }
            }
            if (dialog.isShowing()) {
                dialog.dismiss();
                Snackbar.make(getWindow().getDecorView(), "Tag read", Snackbar.LENGTH_LONG).show();
                rewrite();
            } else {
                Snackbar.make(getWindow().getDecorView(), "Tag read", Snackbar.LENGTH_LONG).show();
                rewrite();
            }

        }
    }

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

    @Override
    protected void onResume() {
        super.onResume();

        try {
            nfcMger.verifyNFC();
            //nfcMger.enableDispatch();

            Intent nfcIntent = new Intent(this, getClass());
            nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, nfcIntent, 0);
            IntentFilter[] intentFiltersArray = new IntentFilter[]{};
            String[][] techList = new String[][]{{android.nfc.tech.Ndef.class.getName()},
                    {android.nfc.tech.NdefFormatable.class.getName()}};
            NfcAdapter nfcAdpt = NfcAdapter.getDefaultAdapter(this);
            nfcAdpt.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techList);
        } catch (NFCManager.NFCNotSupported nfcnsup) {
            View rootView = null;
            View currentFocus = getWindow().getCurrentFocus();
            if (currentFocus != null)
                rootView = currentFocus.getRootView();
            //   Snackbar.make(rootView, "NFC not supported", Snackbar.LENGTH_LONG).show();
            NFCSTATUS = NFC_NOT_SUPPORTED;
        } catch (NFCManager.NFCNotEnabled nfcnEn) {
            View rootView = null;
            View currentFocus = getWindow().getCurrentFocus();
            if (currentFocus != null)
                rootView = currentFocus.getRootView();
            //   Snackbar.make(rootView, "NFC Not enabled", Snackbar.LENGTH_LONG).show();
            NFCSTATUS = NFC_NOT_ENABLED;
        }

    }

    public static void ReadNFC() {
        dialog.setMessage("Tag NFC Tag please");
        dialog.show();
    }
}
