package com.example.TwitterHTTPalert.service;

import com.example.TwitterHTTPalert.model.ApplicationLogin;
import com.example.TwitterHTTPalert.repository.LoginRepository;
import com.twitter.clientlib.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ApplicationService {


    private final LoginRepository loginRepository;



    public ApplicationLogin signUp(ApplicationLogin data){
        data.setId(UUID.randomUUID().toString());           //generates a random string for ID

        return loginRepository.save(data);
    }

    public ApplicationLogin findUser(ApplicationLogin user)
    {
        Optional<ApplicationLogin> optionalLoginUser = loginRepository.findByUsername(user.getUsername()); //finds the user with username

        if(optionalLoginUser.isPresent()){
            ApplicationLogin existingUserFound = optionalLoginUser.get();           //get the existing user
            if(user.getPassword().equals(existingUserFound.getPassword())){
                return existingUserFound;
            }
        }

        return null;

    }

}
