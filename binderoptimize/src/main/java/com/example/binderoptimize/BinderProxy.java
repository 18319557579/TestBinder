package com.example.binderoptimize;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class BinderProxy implements IGradeInterface{

    private final IBinder mBinder;

    public BinderProxy(IBinder mBinder) {
        this.mBinder = mBinder;
    }

    @Override
    public int getStudentGrade(String name) {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        int grade = 0;
        data.writeString(name);
        try {
            if (mBinder == null) {
                throw new IllegalStateException("Need Bind Remote Server");
            }
            mBinder.transact(GradeBinder.REQUEST_CODE, data, reply, 0);
            grade = reply.readInt();
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            data.recycle();
            reply.recycle();
        }
        return grade;
    }


}
