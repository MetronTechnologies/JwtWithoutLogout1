package com.security.DataTransferObjects.Requests;


import lombok.Data;

@Data
public class Register {
    private String firstname;
    private String lastname;
    private String password;
    private String username;
    private String email;
}
