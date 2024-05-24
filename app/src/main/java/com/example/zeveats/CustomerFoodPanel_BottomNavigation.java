package com.example.zeveats;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.zeveats.customerFoodPanel.CustomerCartFragment;
import com.example.zeveats.customerFoodPanel.CustomerHomeFragment;
import com.example.zeveats.customerFoodPanel.CustomerOrdersFragment;
import com.example.zeveats.customerFoodPanel.CustomerProfileFragment;
import com.example.zeveats.customerFoodPanel.CustomerTrackFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomerFoodPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_food_panel_bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name= getIntent().getStringExtra("PAGE");
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        FragmentManager fragmentManager=getSupportFragmentManager();
        if (name!=null){
            if (name.equalsIgnoreCase("Homepage")){
                loadfragment(new CustomerHomeFragment());
            }
            else if (name.equalsIgnoreCase("Preparingpage")){
                loadfragment(new CustomerTrackFragment());
            }
            else if (name.equalsIgnoreCase("DeliveryOrderpage")){
                loadfragment(new CustomerTrackFragment());
            }
            else if (name.equalsIgnoreCase("Thankyoupage")){
                loadfragment(new CustomerHomeFragment());
            }
        }else {
            loadfragment(new CustomerHomeFragment());
        }
    }

    @Override

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int itemId = item.getItemId();

        if (itemId == R.id.cust_Home) {
            fragment = new CustomerHomeFragment();
        } else if (itemId == R.id.cart) {
            fragment = new CustomerCartFragment();
        } else if (itemId == R.id.cust_profile) {
            fragment = new CustomerProfileFragment();
        } else if (itemId == R.id.Cust_order) {
            fragment = new CustomerOrdersFragment();
        } else if (itemId == R.id.track) {
            fragment = new CustomerTrackFragment();
        }

        return loadfragment(fragment);
    }

    private boolean loadfragment(Fragment fragment) {

        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        return false;
    }
}