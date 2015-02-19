package com.dharmab.tyro.client.presenters.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public abstract class Activity extends AbstractActivity {
    @Override
    public final void start(AcceptsOneWidget panel, EventBus eventBus) {
        // discard deprecated com.google.gwt.event.shared.EventBus
        // subclasses should use com.google.web.bindery.event.shared.EventBus via constructor
        start(panel);
    }

    public abstract void start(AcceptsOneWidget panel);
}
