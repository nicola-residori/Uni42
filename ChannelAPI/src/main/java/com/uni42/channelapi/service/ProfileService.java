package com.uni42.channelapi.service;

import com.uni42.channelapi.client.Customer;
import com.uni42.channelapi.client.VerticalClient;
import com.uni42.channelapi.model.CustomerProfile;
import com.uni42.channelapi.model.Profile;
import com.uni42.channelapi.repository.ProfileRepository;
import com.uni42.channelapi.util.ConfigurationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class ProfileService implements UserDetailsService {

    /* - repository - */
    @Autowired
    private ProfileRepository profileRepository;

    /* - client - */
    @Autowired
    private VerticalClient verticalClient;


    /**
     * retrieve customer profile by its username
     *
     * @param username, customer identifier
     * @return CustomerProfile
     * @throws Exception, when something goes wrong
     */
    public CustomerProfile retrieve(String username) throws Exception {

        /* - call vertical service to retrieve customer - */
        Customer customer = verticalClient.retrieveCustomer(username);

        /* - create customer profile - */
        CustomerProfile.CustomerProfileBuilder cpb = CustomerProfile.builder().customer(customer);

        /* - call profile db - */
        Optional<Profile> profile = profileRepository.findById(customer.getUsername());
        profile.ifPresent(value -> cpb.lastAccess(value.getLastAccess()));

        /* - save last access - */
        Profile newProfile = Profile.builder().username(customer.getUsername()).lastAccess(new java.sql.Timestamp(new Date().getTime())).build();
        profileRepository.save(newProfile);

        return cpb.build();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new org.springframework.security.core.userdetails.User(username, null,
                new ArrayList<>());
    }
}
