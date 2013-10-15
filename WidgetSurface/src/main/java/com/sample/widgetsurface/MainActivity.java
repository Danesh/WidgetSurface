package com.sample.widgetsurface;

import com.android.internal.policy.PolicyManager;

import android.os.Bundle;
import android.os.SystemProperties;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewRootImpl;
import android.view.WindowManager;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewTreeObserver.OnDrawListener;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends Activity {

    Window mWindow;
    private WindowManager mWindowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //try {Thread.sleep(5000);}catch(Exception e) {};
        
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        if (this.getParent() == null) {
            View decor = mWindow.getDecorView();
            System.out.println("Here1");
            if (decor != null && decor.getParent() != null) {
                System.out.println("Here2");
                getWindowManager().updateViewLayout(decor, params);
            }
        }
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
        mWindow = PolicyManager.makeNewWindow(this);
        mWindow.setCallback(this);
        mWindow.getLayoutInflater().setPrivateFactory(this);
        mWindow.setWindowManager(
                (WindowManager) getSystemService(Context.WINDOW_SERVICE),
                this.getActivityToken(), null,
                false);
        mWindow.setContentView(R.layout.activity_main);
        mWindowManager = mWindow.getWindowManager();
        mWindow.getDecorView().setVisibility(View.VISIBLE);
        ViewManager wm = getWindowManager();
        //wm.addView(mWindow.getDecorView(), getWindow().getAttributes());
        addView(mWindow.getDecorView(), mWindow.getAttributes(), mWindowManager.getDefaultDisplay(), mWindow.getContainer());
        final Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView txt = (TextView) mWindow.findViewById(R.id.text);
                txt.setText(String.valueOf(System.currentTimeMillis()));
                System.out.println("Setting text");
                txt.invalidate();
                handle.postDelayed(this, 2000);
            }
        }, 5000);
        mWindow.makeActive();
        super.onPostResume();
    }    
    
    
}
