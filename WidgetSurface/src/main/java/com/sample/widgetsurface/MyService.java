package com.sample.widgetsurface;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyService extends Service {

    private View mRoot;
    private Surface mSurface;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private final ISurfaceService.Stub mBinder = new ISurfaceService.Stub() {
        public void setSurface(Surface surface) {
            System.out.println("Got surface " + surface);
            mSurface = surface;
            initView();
        }
        public void onTouch(MotionEvent touchEvent) {
            if (mSurface != null) {
                mRoot.findViewById(R.id.list).dispatchTouchEvent(touchEvent);
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private void initView() {
        if (mSurface == null) {
            return;
        }
        mHandler.post(new Runnable () {
            @Override
            public void run() {
                mRoot = LayoutInflater.from(MyService.this).inflate(R.layout.activity_main, null);
                DisplayManager disp = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
                ViewRootImpl root = new ViewRootImpl(mRoot.getContext(), disp.getDisplay(Display.DEFAULT_DISPLAY));
                ListView listView = (ListView) mRoot.findViewById(R.id.list);
                String[] items = new String[100];
                for (int i = 0; i < 100; i++) {
                    items[i] = String.valueOf(i);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyService.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, items);
                listView.setAdapter(adapter);
                mRoot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        if (mSurface != null) {
                            View txt = (View) mRoot.findViewById(R.id.text);
                            Canvas c = mSurface.lockCanvas(null);
                            c.drawColor(Color.RED);
                            txt.draw(c);
                            mSurface.unlockCanvasAndPost(c);
                        }
                        return false;
                    }
                });
                WindowManager.LayoutParams wparams = new WindowManager.LayoutParams();
                mRoot.setLayoutParams(wparams);
                root.setViewer(mRoot, wparams);
            }
        });
    }
}
