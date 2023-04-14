package com.example.binderoptimize;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class GradeService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Daisy", "绑定成功");
        return new GradeBinder();
    }
}
