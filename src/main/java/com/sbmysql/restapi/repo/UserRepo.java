package com.sbmysql.restapi.repo;

import com.sbmysql.restapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {


}
