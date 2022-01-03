package com.codex.swasthyaagro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import com.codex.swasthyaagro.R;
import com.codex.swasthyaagro.adapter.SliderPagerAdapter;
import com.codex.swasthyaagro.helper.ApiConfig;
import com.codex.swasthyaagro.helper.Constant;
import com.codex.swasthyaagro.helper.Session;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TextView tvSkip, tvNext;
    private SliderPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApiConfig.transparentStatusAndNavigation(this);
        setContentView(R.layout.activity_welcome);

        // bind views
        viewPager = findViewById(R.id.pagerIntroSlider);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tvSkip = findViewById(R.id.tvSkip);
        tvNext = findViewById(R.id.tvNext);

        // init slider pager adapter
        adapter = new SliderPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        // set adapter
        viewPager.setAdapter(adapter);

        // set dot indicators
        tabLayout.setupWithViewPager(viewPager);

        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((viewPager.getCurrentItem() + 1) < adapter.getCount()) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    new Session(WelcomeActivity.this).setIsFirstTime("is_first_time", true);
                    new Session(WelcomeActivity.this).setIsFirstTime("isCartFirstTime", true);
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class).putExtra(Constant.FROM, ""));
                    finish();
                }
            }
        });

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Session(WelcomeActivity.this).setIsFirstTime("is_first_time", true);
                new Session(WelcomeActivity.this).setIsFirstTime("isCartFirstTime", true);
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class).putExtra(Constant.FROM, ""));
                finish();
            }
        });

        /**
         * Add a listener that will be invoked whenever the page changes
         * or is incrementally scrolled
         */
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == adapter.getCount() - 1) {
                    tvNext.setText(R.string.get_started);
                } else {
                    tvNext.setText(R.string.next);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}