package com.example.taxipay;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.razorpay.Checkout;


import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private ZXingScannerView scannerView;
    private TextView txtResult;

     TextView nxt;
    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;
    static final float END_SCALE = 0.7f;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    private AppBarConfiguration mAppBarConfiguration;

    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Checkout.preload(getApplicationContext());
        scannerView = (ZXingScannerView) findViewById(R.id.zxscan);
        txtResult = (TextView) findViewById(R.id.txt_result);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        nxt = findViewById(R.id.payment_demo);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


    }
    public void ClickMenu(View v){
        openDrawer(drawerLayout);
    }
    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void ClickLogo(View v){
        closeDrawer(drawerLayout);
    }

    private void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
    public void profileActivity(){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawerlayout = findViewById(R.id.drawer_layout);
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
        else if (id == R.id.nav_logout) {
            logout();
        }
        else if (id == R.id.nav_profile) {
            profileActivity();
        }

        return true;
    }





    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}