package com.uni42.authenticator.service;

import com.uni42.authenticator.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatorService {

    /* - service - */
    @Autowired
    private UserService userService;


    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    /**
     * generate jwt token by username and password
     *
     * @param username, user identifier
     * @param password, password of user
     * @return jwt token
     * @throws Exception, when user authentication fail
     */
    public String login(String username, String password) throws Exception {

        /* - perform authentication - */
        userService.authenticate(username, password);

        /* - retrieve user info from repository - */
        final UserDetails userDetails = userService.loadUserByUsername(username);

        /* - generate jwt token - */
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return jwt;
    }

    /**
     * check if token is valid
     *
     * @param username, user identifier
     * @param jwtToken, jwt token to validate
     * @return true if token is valid, false otherwise
     */
    public boolean validate(String username, String jwtToken) {
        boolean isValid = false;
        try {
            /* - retrieve user info from repository - */
            final UserDetails userDetails = userService.loadUserByUsername(username);

            /* - check if token is valid - */
            isValid = jwtTokenUtil.validateToken(jwtToken, userDetails);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return isValid;
    }
}
