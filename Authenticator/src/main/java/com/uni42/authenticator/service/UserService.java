package com.uni42.authenticator.service;

import com.uni42.authenticator.exception.DuplicateUserException;
import com.uni42.authenticator.exception.UserNotFoundException;
import com.uni42.authenticator.model.User;
import com.uni42.authenticator.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    /* - repository - */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * @return list of all users
     */
    public List<User> retrieve() {
        return userRepository.findAll();
    }


    /**
     * retrieve specific user by its username
     *
     * @param username, user identifier
     * @return user
     * @throws UserNotFoundException, when username not found
     */
    public User retrieve(String username) throws UserNotFoundException {
        return userRepository.findById(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    /**
     * create new user
     *
     * @param username, user identifier
     * @param password, user password
     * @return created user
     * @throws DuplicateUserException, when user#username already exists
     */
    public User create(String username, String password) throws DuplicateUserException {

        /* - check if already exists - */
        if (userRepository.findById(username).isPresent()) {
            throw new DuplicateUserException(username);
        }

        /* - create user - */
        User user = User.builder().username(username).password(bcryptEncoder.encode(password)).build();

        /* - save on db - */
        return userRepository.save(user);
    }

    /**
     * change password for user by its username
     *
     * @param username,        user identifier
     * @param currentPassword, user currentPassword
     * @param currentPassword, user currentPassword
     * @return old user
     * @throws UserNotFoundException, when user#username not found
     */
    public User changePassword(String username, String currentPassword, String newPassword) throws Exception {

        /* - authenticate - */
        authenticate(username, currentPassword);

        /* - retrieve current user by username - */
        User existingUser = userRepository.findById(username).orElseThrow(() -> new UserNotFoundException(username));

        /* - clone old to retrieve - */
        User oldUser = existingUser.builder()
                .username(username)
                .password(currentPassword)
                .build();

        /* - save on db - */
        existingUser.setPassword(newPassword);
        userRepository.save(existingUser);

        return oldUser;
    }

    /**
     * delete existing user by its username
     *
     * @param username, user identifier
     * @return deleted user
     * @throws UserNotFoundException, when user#username not found
     */
    public User delete(String username) throws UserNotFoundException {
        /* - check exists - */
        User user = userRepository.findById(username).orElseThrow(() -> new UserNotFoundException(username));

        /* - delete from db - */
        userRepository.delete(user);

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), new ArrayList<>());
        }
        throw new UsernameNotFoundException("user with username=" + username + ", not found");
    }

    /**
     * authenticate user
     *
     * @param username, user identifier
     * @param password, password of user
     * @throws Exception, when user authentication fail
     */
    public void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
