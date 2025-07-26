
package com.prathvi.blogApp.payloads;

public class ApiResponse {

    public String message;
    public boolean success;

    public ApiResponse(String message,boolean success){
        this.message=message;
        this.success=success;
    }
}
