package com.dharmab.tyro.client.views.uibinder;

import com.dharmab.tyro.client.views.HomeView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.inject.Inject;

/**
 * Simple static page to act as a placeholder for new applications
 */
public class HomeViewImpl extends Composite implements HomeView {
    private static HomeViewImplUiBinder uiBinder = GWT.create(HomeViewImplUiBinder.class);

    @Inject
    public HomeViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    interface HomeViewImplUiBinder extends UiBinder<HTMLPanel, HomeViewImpl> {
    }
}