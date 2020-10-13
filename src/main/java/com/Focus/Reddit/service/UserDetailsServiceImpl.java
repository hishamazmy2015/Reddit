package com.Focus.Reddit.service;

import com.Focus.Reddit.exceptions.SpringRedditException;
import com.Focus.Reddit.model.User;
import com.Focus.Reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    //    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByUsername(s);
        User user = userOptional.orElseThrow(() -> (new SpringRedditException("User not found with this name" + s)));

        return new org.springframework.security.core.userdetails.User
                (user.getUsername(),
                        user.getPassword(),
                        user.isEnabled(), true, true, true, getAutherity("USER"));
    }

    public Collection<? extends GrantedAuthority> getAutherity(String role) throws UsernameNotFoundException {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }



}
