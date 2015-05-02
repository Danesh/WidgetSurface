package com.sample.widgetsurface;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;

public class MainActivity extends Activity implements Callback {

    public static final String SERVICE_PACKAGE_NAME = "com.sample.widgetsurface";
    public static final String SERVICE_CLASS_NAME = SERVICE_PACKAGE_NAME + ".MyService";

    // Used to send touch events
    private static final String TOUCH_EXTRA = "touch";

    // Used to send surface
    private static final String SURFACE_EXTRA = "surface";

    private static final Intent SERVICE_INTENT = new Intent();
    static {
        SERVICE_INTENT.setClassName(SERVICE_PACKAGE_NAME, SERVICE_CLASS_NAME);
    }

    private ISurfaceService mService;
    private Surface mSurface;
    private ServiceConnection mServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SurfaceView view = new SurfaceView(this);
        view.getHolder().addCallback(this);
        setContentView(view);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mService != null) {
                    try {
                        mService.onTouch(event);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });

        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ISurfaceService.Stub.asInterface(service);
                if (mSurface != null) {
                    try {
                        mService.setSurface(mSurface);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mService = null;
            }
        };
        bindService(SERVICE_INTENT, mServiceConnection, Context.BIND_AUTO_CREATE);
        
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }


    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        mSurface = holder.getSurface();
        if (mService == null) {
            return;
        }
        try {
            mService.setSurface(holder.getSurface());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mSurface = holder.getSurface();
        if (mService == null) {
            return;
        }
        try {
            mService.setSurface(null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }   
}
