package com.security.Controllers;


import com.security.DataTransferObjects.Requests.Login;
import com.security.DataTransferObjects.Requests.PostRequest;
import com.security.DataTransferObjects.Requests.Register;
import com.security.DataTransferObjects.Responses.AuthenticationResponse;
import com.security.Services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;


@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserController {


    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Register register){
        String result = userService.register(register);
        if(Objects.equals(result, "Username has been taken")){
            return new ResponseEntity<>(result, BAD_REQUEST);
        }
        return new ResponseEntity<>(result, OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody Login login){
        AuthenticationResponse result = userService.login(login);
        return new ResponseEntity<>(result, OK);
    }

    @PostMapping("/savepost")
    public ResponseEntity<String> savePost(@RequestBody PostRequest postRequest){
        String result = userService.savePost(postRequest);
        if(Objects.equals(result, "Unknown username")){
            return new ResponseEntity<>(result, BAD_REQUEST);
        }
        return new ResponseEntity<>(result, OK);
    }


}
