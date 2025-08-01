package com.prathvi.blogApp.exceptions;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String fieldName;
    long fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Integer fieldValue) {
       super(String.format("%s not found with %s : %d",resourceName , fieldName ,fieldValue));
       this.resourceName = resourceName;
       this.fieldName = fieldName;
       this.fieldValue = fieldValue;
    }
}
