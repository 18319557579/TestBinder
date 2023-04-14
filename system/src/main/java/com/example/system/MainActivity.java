package com.example.system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import com.example.system.aaa.IGradeService;
import com.example.system.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private IGradeService mBinderProxy;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBinderProxy = IGradeService.Stub.asInterface(iBinder);
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
                getStudentGrade("hsf");
            }
        });
    }

    private void bindGradeService() {
        String action = "android.intent.action.server.aidl.gradeservice";
        Intent intent = new Intent(action);
        intent.setPackage(getPackageName());
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void getStudentGrade(String name) {
        int grade = 0;
        try {
            grade = mBinderProxy.getStudentGrade(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "成绩是：" + grade, Toast.LENGTH_SHORT).show();
    }
}