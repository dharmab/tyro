package com.dharmab.tyro.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;

public class ActivityMapperImpl implements ActivityMapper {

    @Inject
    public ActivityMapperImpl() {
    }

    @Override
    public Activity getActivity(Place place) {
        return null;
    }
}
