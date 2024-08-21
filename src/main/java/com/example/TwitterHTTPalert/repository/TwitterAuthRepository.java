package com.example.TwitterHTTPalert.repository;

import com.example.TwitterHTTPalert.model.AuthTwitterAPI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwitterAuthRepository extends JpaRepository<AuthTwitterAPI, Long> {
}
