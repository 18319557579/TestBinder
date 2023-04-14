package com.example.testbinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.example.testbinder.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private IBinder mRemoteBinder;

    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.btnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String action = "android.intent.action.server.gradeservice";
                Intent intent = new Intent(action);
                intent.setPackage(getPackageName());
                bindService(intent, serviceConnection, BIND_AUTO_CREATE);
            }
        });

        mainBinding.btnInvoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "hsf";
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                int grade = 0;
                data.writeString(name);

                try {
                    if (mRemoteBinder == null) {
                        throw new IllegalStateException("Need Bind Remote Server...");
                    }
                    mRemoteBinder.transact(GradeService.REQUEST_CODE, data, reply, 0);
                    grade = reply.readInt();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                Log.d("Daisy", "查询到的值:" + grade);

                data.recycle();
                reply.recycle();
            }
        });

        mainBinding.btnProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo 由于现在Service在不同进程，所以这里的强转会报错。这时把Service的:process干掉，那么这里就不报错了
                int progress = ((GradeService.DownloadBinder)mRemoteBinder).getProcess();
                Log.d("Daisy", "拿到了进度：" + progress);
            }
        });
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            iBinder instanceof GradeService.DownloadBinder
            mRemoteBinder = iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mRemoteBinder = null;
        }
    };


}