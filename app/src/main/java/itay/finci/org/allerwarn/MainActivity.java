package itay.finci.org.allerwarn;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,FragmentChangeListener{
    TextView tvVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    }



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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_newUser) {
            NewUserFragment nuf = new NewUserFragment();
            this.replaceFragment(nuf);
        }else if (id == R.id.nav_home) {
            MainScreenFragment msf = new MainScreenFragment();
            this.replaceFragment(msf);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void replaceFragment(Fragment fragment) {
        //FragmentManager fragmentManager = getSupportFragmentManager();;
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.fragment_cointainer, fragment, null).commit();
    }
}

