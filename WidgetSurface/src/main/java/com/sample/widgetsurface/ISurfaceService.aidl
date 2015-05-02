package com.sample.widgetsurface;

import android.view.MotionEvent;
import android.view.Surface;

oneway interface ISurfaceService {
    void setSurface(in Surface surface);
    void onTouch(in MotionEvent touchEvent);
} 