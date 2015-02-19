package com.dharmab.tyro.client.validation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;

public class ClientsideValidatorFactory extends AbstractGwtValidatorFactory {
    @Override
    public AbstractGwtValidator createValidator() {
        return GWT.create(ClientsideValidator.class);
    }
}
