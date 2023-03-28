package com.security.Services.ServiceImplementations;


import com.security.Models.UserModel;
import com.security.Models.UserRoles;
import com.security.Repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo uRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = uRepo.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found")
        );
        return new User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles())
        );
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<UserRoles> roles) {
        return roles
                .stream()
                .map(
                        role -> new SimpleGrantedAuthority(
                                role.getName()
                        )
                )
                .collect(toList());
    }

}
