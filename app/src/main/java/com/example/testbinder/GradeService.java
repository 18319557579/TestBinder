package com.example.testbinder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GradeService extends Service {
    public static final int REQUEST_CODE = 1000;

    private final DownloadBinder mBinder = new DownloadBinder();

    public class DownloadBinder extends Binder {
        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            if (code == REQUEST_CODE) {
                String name = data.readString();
                int studentGrade = getStudentGrade(name);
                if (reply != null) {
                    reply.writeInt(studentGrade);
                }
                return true;
            }
            return super.onTransact(code, data, reply, flags);
        }

        public int getProcess() {
            Log.d("Daisy", "当前进度：" + 55);
            return 55;
        }
    }

    /*private final Binder mBinder = new Binder() {
        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            if (code == REQUEST_CODE) {
                String name = data.readString();
                int studentGrade = getStudentGrade(name);
                if (reply != null) {
                    reply.writeInt(studentGrade);
                }
                return true;
            }
            return super.onTransact(code, data, reply, flags);
        }

        public int getProcess() {
            Log.d("Daisy", "当前进度：" + 55);
            return 55;
        }

    };*/

    // 根据姓名查询学生成绩
    private int getStudentGrade(String name) {
        return 666777;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}