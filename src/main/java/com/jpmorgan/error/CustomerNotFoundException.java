package com.jpmorgan.error;

public class CustomerNotFoundException extends Throwable {
    public CustomerNotFoundException(String customerName) {

            super("customer  not found : " + customerName);
        }
    }

