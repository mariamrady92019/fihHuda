package com.example.fihhuda.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.fihhuda.tafseer.views.TafseerFragment;
import com.example.fihhuda.azkar.views.AzkarFragment;
import com.example.fihhuda.R;
import com.example.fihhuda.quran.views.QuranFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected FrameLayout fragmentContainer;
    protected BottomNavigationView navView;
    protected ConstraintLayout container;
    protected EditText searchBar;
    BottomNavigationView.OnNavigationItemSelectedListener
            onNavigationItemSelectedListener ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_home);
        trackSelectedItem();
        initView();

    }

    private void trackSelectedItem() {
      onNavigationItemSelectedListener=  new BottomNavigationView.
              OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Fragment fragment=null;
                switch (id){
                    case R.id.navigation_quran:{
                        fragment= new QuranFragment();
                        break;
                    }
                    case R.id.navigation_azkar:{
                       fragment =new AzkarFragment();
                        break;
                    }
                    case R.id.navigation_ahadeeth:{
                      //  fragment =new AhadeethFragment();
                        break;
                    }
                    case R.id.navigation_tafseer:{
                     fragment =new TafseerFragment();
                        break;
                    }
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,fragment)
                        .commit();

                return true;
            }
        };
    }

    @SuppressLint("WrongViewCast")
    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        navView = (BottomNavigationView) findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_quran);
        container = (ConstraintLayout) findViewById(R.id.container);
        searchBar = (EditText) findViewById(R.id.search_bar);
    }
}
