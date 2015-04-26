package com.sample.widgetsurface;

import com.android.internal.policy.PolicyManager;

import android.os.Bundle;
import android.os.SystemProperties;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.view.Display;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewTreeObserver.OnDrawListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity implements Callback {

    Window mWindow;
    private WindowManager mWindowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SurfaceView view = new SurfaceView(this);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        view.getHolder().addCallback(this);
        setContentView(view);
        view.setOnTouchListener(new View.OnTouchListener() {
            
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mRoot.dispatchTouchEvent(event);
            }
        });
        //try {Thread.sleep(5000);}catch(Exception e) {};
        
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
//        if (this.getParent() == null) {
//            View decor = mWindow.getDecorView();
//            System.out.println("Here1");
//            if (decor != null && decor.getParent() != null) {
//                System.out.println("Here2");
//                getWindowManager().updateViewLayout(decor, params);
//            }
//        }
    }
    
    @Override
    protected void onResume() {
        System.out.println("onResume");
        super.onResume();
        
    }

    public void addView(View view, ViewGroup.LayoutParams params,
            Display display, Window parentWindow) {
        if (view == null) {
            throw new IllegalArgumentException("view must not be null");
        }
        if (display == null) {
            throw new IllegalArgumentException("display must not be null");
        }
        if (!(params instanceof WindowManager.LayoutParams)) {
            throw new IllegalArgumentException("Params must be WindowManager.LayoutParams");
        }

        final WindowManager.LayoutParams wparams = (WindowManager.LayoutParams)params;
        if (parentWindow != null) {
            //parentWindow.adjustLayoutParamsForSubWindow(wparams);
        }

        ViewRootImpl root;
        View panelParentView = null;

       
            root = new ViewRootImpl(view.getContext(), display);
            wparams.token = getWindow().peekDecorView().getWindowToken();
            view.setLayoutParams(wparams);

         

        // do this last because it fires off messages to start doing things
        try {
            root.setView(view, null, panelParentView);
        } catch (RuntimeException e) {
            // BadTokenException or InvalidDisplayException, clean up.
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        System.out.println("onStart");
        super.onStart();
    }


    @Override
    protected void onPostResume() {
        System.out.println("onPostResume");
//        mWindow = PolicyManager.makeNewWindow(this);
//        mWindow.setCallback(this);
//        mWindow.getLayoutInflater().setPrivateFactory(this);
//        mWindow.setWindowManager(
//                (WindowManager) getSystemService(Context.WINDOW_SERVICE),
//                this.getActivityToken(), null,
//                false);
//        mWindow.setContentView(R.layout.activity_main);
//        mWindowManager = mWindow.getWindowManager();
//        mWindow.getDecorView().setVisibility(View.VISIBLE);
//        ViewManager wm = getWindowManager();
//        //wm.addView(mWindow.getDecorView(), getWindow().getAttributes());
//        addView(mWindow.getDecorView(), mWindow.getAttributes(), mWindowManager.getDefaultDisplay(), mWindow.getContainer());
//        final Handler handle = new Handler();
//        handle.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                TextView txt = (TextView) mWindow.findViewById(R.id.text);
//                txt.setText(String.valueOf(System.currentTimeMillis()));
//                System.out.println("Setting text");
//                txt.invalidate();
//                handle.postDelayed(this, 2000);
//            }
//        }, 5000);
//        mWindow.makeActive();
        super.onPostResume();
    }

    View mRoot;
    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        mRoot = getLayoutInflater().inflate(R.layout.activity_main, null);
        mRoot.setLayoutParams(new LinearLayout.LayoutParams(500, 500));
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(500, MeasureSpec.AT_MOST);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(500, MeasureSpec.AT_MOST);
        mRoot.measure(widthMeasureSpec, heightMeasureSpec);
        DisplayManager disp = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        ViewRootImpl root = new ViewRootImpl(mRoot.getContext(), disp.getDisplay(Display.DEFAULT_DISPLAY));
        TextView txt = (TextView) mRoot.findViewById(R.id.text);
        txt.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
              ((TextView) v).setText(String.valueOf(System.currentTimeMillis()));
                
            }
        });
        mRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                TextView txt = (TextView) mRoot.findViewById(R.id.text);
                txt.setLayoutParams(new LinearLayout.LayoutParams(500, 500));
                int widthMeasureSpec = MeasureSpec.makeMeasureSpec(500, MeasureSpec.AT_MOST);
                int heightMeasureSpec = MeasureSpec.makeMeasureSpec(500, MeasureSpec.AT_MOST);
                txt.measure(widthMeasureSpec, heightMeasureSpec);
                Canvas c = holder.lockCanvas();
                c.drawColor(Color.RED);
                txt.draw(c);
                holder.unlockCanvasAndPost(c);
            }
        });
        //view.setLayoutParams(wparams);
        WindowManager.LayoutParams wparams = new WindowManager.LayoutParams();
        wparams.type = WindowManager.LayoutParams.TYPE_HIDDEN_NAV_CONSUMER;
        //wparams.token = getActivityToken();
        mRoot.setLayoutParams(wparams);
        root.setViewer(mRoot, wparams);
        final Handler handle = new Handler();
//        handle.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                TextView txt = (TextView) v.findViewById(R.id.text);
//                txt.setText(String.valueOf(System.currentTimeMillis()));
//                System.out.println("Setting text");
//                handle.postDelayed(this, 2000);
//            }
//        }, 5000);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        
    }    
    
    
}
