package com.security.Services;


import com.security.Configurations.JwtGenerator;
import com.security.DataTransferObjects.Requests.Login;
import com.security.DataTransferObjects.Requests.PostRequest;
import com.security.DataTransferObjects.Requests.Register;
import com.security.DataTransferObjects.Responses.AuthenticationResponse;
import com.security.Models.PostModel;
import com.security.Models.UserModel;
import com.security.Models.UserRoles;
import com.security.Repositories.PostRepo;
import com.security.Repositories.RoleRepo;
import com.security.Repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static java.time.Instant.now;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;

    private final PostRepo postRepo;

    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

    @Transactional
    public String register(Register register){
        if(userRepo.existsByUsername(register.getUsername())){
            return "Username has been taken";
        }
        UserModel uModel = new UserModel();
        uModel.setLastName(register.getLastname());
        uModel.setFirstName(register.getFirstname());
        uModel.setUsername(register.getUsername());
        uModel.setPassword(encodePassword(register.getPassword()));
        uModel.setEmail(register.getEmail());
        uModel.setCreated(now());
        uModel.setEnabled(false);

        UserRoles roles = roleRepo.findByName("USER").get();
        uModel.setRoles(Collections.singletonList(roles));

        userRepo.save(uModel);

        return "User successfully registered";
    }


    public AuthenticationResponse login(Login login){
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
                );
//        log.info("{}", authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new AuthenticationResponse(token);
    }

    @Transactional(readOnly = true)
    public UserModel getCurrentUser() {
        Object result = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if(result instanceof UserDetails){
            username = ((UserDetails) result).getUsername();
        } else {
            username = result.toString();
        }

        return userRepo.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found" + username)
        );

    }

    @Transactional
    public String savePost(PostRequest postRequest){
        UserModel user = getCurrentUser();
        if(userRepo.existsByUsername(user.getUsername())){
            PostModel postModel = new PostModel();
            postModel.setPost(postRequest.getPost());
            postModel.setUserModel(user);
            postRepo.save(postModel);

            return "Post Successfully Saved";
        } else {
            return "Unknown username";
        }

    }


}
