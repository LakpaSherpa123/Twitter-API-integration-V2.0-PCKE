package com.example.TwitterHTTPalert.repository;
import com.example.TwitterHTTPalert.model.ApplicationLogin;

import com.twitter.clientlib.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface LoginRepository extends JpaRepository <ApplicationLogin, Long> {

   public Optional<ApplicationLogin> findByUsername(String username);

}
