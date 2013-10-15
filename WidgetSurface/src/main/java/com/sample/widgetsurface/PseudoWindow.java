package com.sample.widgetsurface;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.InputQueue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.LinearLayout;

public class PseudoWindow extends Window {

    private final LinearLayout mContent;

    public PseudoWindow(Context context) {
        super(context);
        mContent = new LinearLayout(context) {
            @Override
            public ViewParent invalidateChildInParent(int[] location, Rect dirty) {
                System.out.println("INVALIDATE CHILD IN PARENT");
                return super.invalidateChildInParent(location, dirty);
            }

            @Override
            public void invalidate(Rect dirty) {
                System.out.println("INVALIDATE CHILD IN PARENT");
                super.invalidate(dirty);
            }

            @Override
            public void invalidate(int l, int t, int r, int b) {
                System.out.println("INVALIDATE CHILD IN PARENT");
                super.invalidate(l, t, r, b);
            }

            @Override
            public void invalidate() {
                System.out.println("INVALIDATE CHILD IN PARENT");
                super.invalidate();
            }
        };
    }

    @Override
    public void takeSurface(SurfaceHolder.Callback2 callback2) {

    }

    @Override
    public void takeInputQueue(InputQueue.Callback callback) {

    }

    @Override
    public boolean isFloating() {
        return false;
    }

    @Override
    public void setContentView(int i) {
        getLayoutInflater().inflate(i, (ViewGroup) getDecorView(), true);
    }

    @Override
    public void setContentView(View view) {
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {

    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        ((ViewGroup) getDecorView()).addView(view, layoutParams);
    }

    @Override
    public View getCurrentFocus() {
        return null;
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(getContext());
    }

    @Override
    public void setTitle(CharSequence charSequence) {

    }

    @Override
    public void setTitleColor(int i) {

    }

    @Override
    public void openPanel(int i, KeyEvent keyEvent) {

    }

    @Override
    public void closePanel(int i) {

    }

    @Override
    public void togglePanel(int i, KeyEvent keyEvent) {

    }

    @Override
    public void invalidatePanelMenu(int i) {
        System.out.println("invalidatePanelMenu");
    }

    @Override
    public boolean performPanelShortcut(int i, int i2, KeyEvent keyEvent, int i3) {
        return false;
    }

    @Override
    public boolean performPanelIdentifierAction(int i, int i2, int i3) {
        return false;
    }

    @Override
    public void closeAllPanels() {

    }

    @Override
    public boolean performContextMenuIdentifierAction(int i, int i2) {
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {

    }

    @Override
    public void setBackgroundDrawable(Drawable drawable) {

    }

    @Override
    public void setFeatureDrawableResource(int i, int i2) {

    }

    @Override
    public void setFeatureDrawableUri(int i, Uri uri) {

    }

    @Override
    public void setFeatureDrawable(int i, Drawable drawable) {

    }

    @Override
    public void setFeatureDrawableAlpha(int i, int i2) {

    }

    @Override
    public void setFeatureInt(int i, int i2) {

    }

    @Override
    public void takeKeyEvents(boolean b) {

    }

    @Override
    public boolean superDispatchKeyEvent(KeyEvent keyEvent) {
        return false;
    }

    @Override
    public boolean superDispatchKeyShortcutEvent(KeyEvent keyEvent) {
        return false;
    }

    @Override
    public boolean superDispatchTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean superDispatchTrackballEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean superDispatchGenericMotionEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public View getDecorView() {
        return mContent;
    }

    @Override
    public View peekDecorView() {
        return mContent;
    }

    @Override
    public Bundle saveHierarchyState() {
        return null;
    }

    @Override
    public void restoreHierarchyState(Bundle bundle) {

    }

    @Override
    protected void onActive() {

    }

    @Override
    public void setChildDrawable(int i, Drawable drawable) {

    }

    @Override
    public void setChildInt(int i, int i2) {

    }

    @Override
    public boolean isShortcutKey(int i, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public void setVolumeControlStream(int i) {

    }

    @Override
    public int getVolumeControlStream() {
        return 0;
    }

    @Override
    public void alwaysReadCloseOnTouchAttr() {
        // TODO Auto-generated method stub
        
    }
}
