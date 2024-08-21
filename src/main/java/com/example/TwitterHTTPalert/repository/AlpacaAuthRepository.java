package com.example.TwitterHTTPalert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.TwitterHTTPalert.model.AuthAlpacaAPI;
import org.springframework.stereotype.Repository;

@Repository
public interface AlpacaAuthRepository extends JpaRepository<AuthAlpacaAPI , Long> {

}
