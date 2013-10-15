package com.sample.widgetsurface;

import com.android.internal.policy.PolicyManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListenerViewGroup extends LinearLayout {

    public ListenerViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public ListenerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ListenerViewGroup(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        System.out.println("onLayout");
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public ViewParent invalidateChildInParent(int[] location, Rect dirty) {
        System.out.println("invalidateChildInParent");
        return super.invalidateChildInParent(location, dirty);
    }

    @Override
    protected void onAttachedToWindow() {
        Thread.dumpStack();
        System.out.println("onattachedToWindow");
        super.onAttachedToWindow();
    }
    
    
    
}
