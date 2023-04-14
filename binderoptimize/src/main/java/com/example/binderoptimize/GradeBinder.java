package com.example.binderoptimize;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class GradeBinder extends Binder implements IGradeInterface {
    public static final int REQUEST_CODE = 1000;

    @Override
    public int getStudentGrade(String name) {
        return Objects.equals(name, "hsf") ? 123 : 321;
    }

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

    public static IGradeInterface asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        if (iBinder instanceof IGradeInterface) {
            Log.d("Daisy", "当前进程");
            //这说明是GradeService返回的GradeBinder实例，它是实现了IGradeInterface的
            return (IGradeInterface) iBinder;
        } else {
            Log.d("Daisy", "远程进程");
            return new BinderProxy(iBinder);
        }
    }
}
