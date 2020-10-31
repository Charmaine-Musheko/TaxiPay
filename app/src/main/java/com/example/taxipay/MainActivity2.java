package com.example.taxipay;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle mDrawerToggle;
    ImageView menuIcon;
    String drawer_open, drawer_close;
    LinearLayout contentView;
    static final float END_SCALE = 0.7f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        contentView = findViewById(R.id.content);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
                @Override
                public void onDrawerClosed (View drawerView)
                {
                    super.onDrawerClosed(drawerView);
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                }

                @Override
                public void onDrawerOpened (View drawerView)
                {
                    super.onDrawerOpened(drawerView);
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                }
            }

            ;
// Set the drawer toggle as the DrawerListener
        drawerLayout.addDrawerListener(mDrawerToggle);
        };


// Set the drawer toggle as the DrawerListener
@Override
 public void onBackPressed() {
    DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
        drawerLayout.closeDrawer(GravityCompat.START);
    } else {
        super.onBackPressed();
    }
}
    private void naviagtionDrawer() {

            //Naviagtion Drawer
            navigationView.bringToFront();
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setCheckedItem(R.id.nav_home);

            menuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (drawerLayout.isDrawerVisible(GravityCompat.START))
                        drawerLayout.closeDrawer(GravityCompat.START);
                    else drawerLayout.openDrawer(GravityCompat.START);
                }
            });

            animateNavigationDrawer();
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }
    public void generatorActivity(){
        Intent intent = new Intent(this, QRGenerator.class);
        startActivity(intent);
    }
    public void scanActivity(){
        Intent intent = new Intent(this, ScannerQR.class);
        startActivity(intent);
    }
    public void paymentActivity(){
        Intent intent = new Intent(this, Payment.class);
        startActivity(intent);
    }
    public void loginActivity(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.generateQR_pay) {
            generatorActivity();
        } else if (id == R.id.scan_payment) {
            scanActivity();
        } else if (id == R.id.nav_bank) {
//            handleSelection(R.string.nav_share_message);
            paymentActivity();
        } else if (id == R.id.login) {
            loginActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void animateNavigationDrawer() {

        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }
}
