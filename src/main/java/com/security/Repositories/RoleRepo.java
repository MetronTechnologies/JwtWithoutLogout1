package com.security.Repositories;



import com.security.Models.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;



public interface RoleRepo extends JpaRepository<UserRoles, Integer> {

    Optional<UserRoles> findByName(String name);

}
