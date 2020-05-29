package com.spring.security.jwt.controller;

import com.spring.security.jwt.model.AuthenticationRequest;
import com.spring.security.jwt.model.AuthenticationResponse;
import com.spring.security.jwt.service.MyuserDetailService;
import com.spring.security.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyuserDetailService detailService;
    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping( "/hello")
    public String hello(){
        return "cds";
    }

    @RequestMapping(value = "/authenticate",method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticateToken(@RequestBody AuthenticationRequest authenticationRequest)throws Exception
    {
        try{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),authenticationRequest.getPassword()));
    }
        catch (BadCredentialsException e){
                throw new Exception("incorrect username & password ",e);
        }
        final UserDetails userDetails= detailService.loadUserByUsername(authenticationRequest.getUsername());
           final String jwt= jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
