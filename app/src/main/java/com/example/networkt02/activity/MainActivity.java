package com.example.networkt02.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.networkt02.R;
import com.example.networkt02.fragment.IllegalFragment;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainACT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //replaceFragment(new TrafficLightFragment());
        replaceFragment(new IllegalFragment());

    }


    // 切换碎片的方法
    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment).commitAllowingStateLoss();
    }

}
