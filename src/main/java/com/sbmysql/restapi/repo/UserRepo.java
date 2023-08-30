package com.sbmysql.restapi.repo;

import com.sbmysql.restapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByFirstName(String firstName);

}
