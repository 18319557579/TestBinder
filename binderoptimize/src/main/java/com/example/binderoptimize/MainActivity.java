package com.example.binderoptimize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.example.binderoptimize.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // 此处可能是BinderProxy也可能是GradeBinder
    private IGradeInterface mBinderProxy;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBinderProxy = GradeBinder.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBinderProxy = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindGradeService();
            }
        });

        binding.btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Daisy", "hsf的成绩是：" + mBinderProxy.getStudentGrade("hsf"));
            }
        });

    }

    private void bindGradeService() {
        String action = "android.intent.action.server.gradeservice";
        Intent intent = new Intent(action);
        intent.setPackage(getPackageName());
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }
}