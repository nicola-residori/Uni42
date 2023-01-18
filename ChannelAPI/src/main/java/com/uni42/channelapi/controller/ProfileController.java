package com.uni42.channelapi.controller;

import com.uni42.channelapi.exception.UserNotAuthorizedException;
import com.uni42.channelapi.model.CustomerProfile;
import com.uni42.channelapi.service.ProfileService;
import com.uni42.channelapi.util.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("profile")
public class ProfileController {

    /* - service - */
    @Autowired
    private ProfileService profileService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/{username}")
    public ResponseEntity<CustomerProfile> retrieve(@ApiIgnore @RequestHeader(name = "Authorization") String jwtToken, @PathVariable("username") String username) throws Exception {
        /* - check authorization - */
        String userid = jwtTokenUtil.getUsernameFromToken(jwtToken);
        if (!userid.equalsIgnoreCase(username)) {
            throw new UserNotAuthorizedException();
        }
        return new ResponseEntity<>(profileService.retrieve(username), HttpStatus.OK);
    }

}
