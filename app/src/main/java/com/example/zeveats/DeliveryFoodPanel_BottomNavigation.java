package com.example.zeveats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.zeveats.chefFoodPanel.ChefHomeFragment;
import com.example.zeveats.chefFoodPanel.ChefOrderFragment;
import com.example.zeveats.chefFoodPanel.ChefPendingOrderFragment;
import com.example.zeveats.customerFoodPanel.CustomerCartFragment;
import com.example.zeveats.customerFoodPanel.CustomerHomeFragment;
import com.example.zeveats.customerFoodPanel.CustomerOrdersFragment;
import com.example.zeveats.customerFoodPanel.CustomerProfileFragment;
import com.example.zeveats.customerFoodPanel.CustomerTrackFragment;
import com.example.zeveats.deliveryFoodPanel.DeliveryPendingOrderFragment;
import com.example.zeveats.deliveryFoodPanel.DeliveryShipOrderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DeliveryFoodPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_food_panel_bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.delivery_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name = getIntent().getStringExtra("PAGE");
        if(name!=null){
            if(name.equalsIgnoreCase("DeliveryOrderpage")){
                loaddeliveryfragment(new DeliveryPendingOrderFragment());
            }
        }else{
            loaddeliveryfragment(new DeliveryPendingOrderFragment());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        int itemId = item.getItemId();

        if (itemId == R.id.shiporders) {
            fragment = new DeliveryShipOrderFragment();
        } else if (itemId == R.id.pendingorders) {
            fragment = new DeliveryPendingOrderFragment();
        }

        return loaddeliveryfragment(fragment);
    }

    private boolean loaddeliveryfragment(Fragment fragment) {
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerbott,fragment).commit();
            return true;
        }
        return false;
    }
}