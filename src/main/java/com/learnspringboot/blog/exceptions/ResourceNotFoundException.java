package com.learnspringboot.blog.exceptions;

public class ResourceNotFoundException extends RuntimeException{

     private String fieldName;
     private String resourceName;

     private long fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s: %s", resourceName, fieldName, fieldValue));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.resourceName = resourceName;
    }
}
