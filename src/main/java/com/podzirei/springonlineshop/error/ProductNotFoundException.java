package com.podzirei.springonlineshop.error;

public class ProductNotFoundException extends Exception{
    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String message){
        super(message);
    }
}
