package com.jpmorgan.error;

public class ProductNotAvaialbleException extends Throwable {
public ProductNotAvaialbleException(String  productName) {

        super("Product is out of stock  not found : " + productName);
    }
}

