package itay.finci.org.allerwarn.intro;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import itay.finci.org.allerwarn.MainActivity;
import itay.finci.org.allerwarn.NFC.NFCManager;
import itay.finci.org.allerwarn.R;
import itay.finci.org.allerwarn.user.UserList;

public class IntroActivity extends FragmentActivity {

    private ViewPager mViewPager;

    public static NFCManager nfcMger;
    Tag currentTag;
    int NFCSTATUS = 0;


    private final static int NFC_NOT_SUPPORTED = 1;
    private final static int NFC_NOT_ENABLED = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.intro_layout);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        // Set an Adapter on the ViewPager
        mViewPager.setAdapter(new IntroAdapter(getSupportFragmentManager()));

        // Set a PageTransformer
        mViewPager.setPageTransformer(false, new IntroPageTransformer());

        nfcMger = new NFCManager(this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.d("Nfc", "New intent");
        // It is the time to write the tag
        currentTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
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
                    startActivity(new Intent(this, MainActivity.class));
                    // msf.refresh();
                    rewrite();
                } catch (UnsupportedEncodingException e) {
                    Log.e("TAG", "Unsupported Encoding", e);
                }
            }
        }
        Snackbar.make(getWindow().getDecorView(), "Tag read", Snackbar.LENGTH_LONG).show();
        rewrite();

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

    private void rewrite() {
        try {
            FileOutputStream fileOut = openFileOutput("UserList.ser", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            UserList u = UserList.getInstance();
            UserList.getInstance().write(out);
            out.close();
            fileOut.close();
            //System.out.printf("Serialized data is saved in /tmp/employee.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }


}