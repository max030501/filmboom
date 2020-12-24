package ru.mirea.filmboom.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import ru.mirea.filmboom.R;
import ru.mirea.filmboom.adapter.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("info",String.valueOf(R.drawable.poster));
        Log.d("info",String.valueOf(R.drawable.poster_1));
        Log.d("info",String.valueOf(R.drawable.poster_back_to_future));
        Log.d("info",String.valueOf(R.drawable.poster_brat2));
        Log.d("info",String.valueOf(R.drawable.poster_forest_gump));
        Log.d("info",String.valueOf(R.drawable.poster_green_mile));
        Log.d("info_inter",String.valueOf(R.drawable.poster_interstellar));
        Log.d("info",String.valueOf(R.drawable.poster_klaus));
        Log.d("info",String.valueOf(R.drawable.poster_list_shindler));
        Log.d("info",String.valueOf(R.drawable.poster_lord_of_rings));
        Log.d("info",String.valueOf(R.drawable.poster_redemption));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ImageLoader il =ImageLoader.getInstance();
        il.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_ticket:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_favourite:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_bought:
                        viewPager.setCurrentItem(2);
                        return true;
                }
                return false;
            }
        };
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager = findViewById(R.id.view_pager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_ticket).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_favourite).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_bought).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}