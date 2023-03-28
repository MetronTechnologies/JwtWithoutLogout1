package com.security.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "xuser")
public class UserModel {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userid;

    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "firstname cannot be empty")
    private String firstName;

    @NotBlank(message = "lastname cannot be empty")
    private String lastName;

    @NotBlank(message = "password cannot be empty")
    private String password;

    @NotBlank(message = "email cannot be empty")
    private String email;
    private Instant created;

    private boolean enabled;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "userxroles",
            joinColumns = @JoinColumn(
                    name = "userid",
                    referencedColumnName = "userid"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "roleid",
                    referencedColumnName = "roleid"
            )
    )
    private List<UserRoles> roles = new ArrayList<>();

}
