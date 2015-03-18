package com.dharmab.tyro.client.presenters.activities;

import com.dharmab.tyro.client.presenters.HomePresenter;
import com.dharmab.tyro.client.views.HomeView;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

/**
 * Simple placeholder activity
 */
public class HomeActivity extends Activity implements HomePresenter {
    private HomeView view;

    @Inject
    public HomeActivity(HomeView view) {
        this.view = view;
    }

    @Override
    public void start(AcceptsOneWidget panel) {
        panel.setWidget(view);
    }
}
