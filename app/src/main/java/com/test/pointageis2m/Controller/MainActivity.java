package com.test.pointageis2m.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentViewHolder;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.test.pointageis2m.R;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FragmentContainerView fragmentContainerView;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.init();
    }

    private void init(){
        this.bottomNavigationView = findViewById(R.id.bottom_navigation);
        this.fragmentContainerView = findViewById(R.id.fragmentContainerView);
        fragmentManager = getSupportFragmentManager();
        this.setListener();
    }

    private void setListener(){
        this.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new AboutFragment();
                if (item.getTitle() == getString(R.string.scan)){
                    fragment = new ScanFragment();
                }
                else if(item.getTitle() == getString(R.string.manual_input)){
                    fragment = new ManualInputFragment();
                }
                else if(item.getTitle() == getString(R.string.history)){
                    fragment = new HistoryFragment();
                }
                else if(item.getTitle() == getString(R.string.synchronize)){
                    fragment = new SynchronizeFragment();
                }
                else if(item.getTitle() == getString(R.string.about)){
                    fragment = new AboutFragment();
                }

                FragmentTransaction transaction = fragmentManager.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.fragmentContainerView, fragment);
                transaction.commit();
                return true;
            }
        });
        this.bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

            }
        });
    }
}