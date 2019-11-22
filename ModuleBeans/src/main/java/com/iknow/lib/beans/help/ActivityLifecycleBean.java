package com.iknow.lib.beans.help;

import android.app.Activity;

import androidx.lifecycle.Lifecycle;

public class ActivityLifecycleBean {

    private Activity target;

    /**
     * @see Lifecycle.Event
     */
    private Lifecycle.Event event;

    public ActivityLifecycleBean(Activity target, Lifecycle.Event event) {
        this.target = target;
        this.event = event;
    }

    public Activity getTarget() {
        return target;
    }

    public Lifecycle.Event getEvent() {
        return event;
    }

}
