package com.security.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "xpost")
@AllArgsConstructor
@NoArgsConstructor
public class PostModel {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String post;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(
            name = "userxpost",
            referencedColumnName = "userId"
    )
    private UserModel userModel;

}
