package com.dharmab.tyro.client.presenters.activities;

import com.dharmab.tyro.client.inject.ActivityFactory;
import com.dharmab.tyro.client.places.HomePlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;

/**
 * the ActivityMapper determines which screen is displayed when the user enters or otherwise is directed to a particular URL
 */
public class ActivityMapperImpl implements ActivityMapper {

    ActivityFactory activityFactory;

    @Inject
    public ActivityMapperImpl(ActivityFactory activityFactory) {
        this.activityFactory = activityFactory;
    }


    @Override
    public Activity getActivity(Place place) {
        if (place instanceof HomePlace) {
            return activityFactory.create();
        }
        return null;
    }
}
