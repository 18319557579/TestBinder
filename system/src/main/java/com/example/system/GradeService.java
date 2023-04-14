package com.example.system;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.system.aaa.IGradeService;

import java.util.Objects;

public class GradeService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Daisy", "绑定成功");
        return new IGradeService.Stub() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }

            @Override
            public int getStudentGrade(String name) throws RemoteException {
                return Objects.equals(name, "hsf") ? 325 : 888;
            }
        };
    }
}
