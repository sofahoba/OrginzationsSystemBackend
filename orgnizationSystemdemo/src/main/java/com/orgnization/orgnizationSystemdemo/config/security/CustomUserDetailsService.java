package com.orgnization.orgnizationSystemdemo.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.orgnization.orgnizationSystemdemo.model.entity.User;
import com.orgnization.orgnizationSystemdemo.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;


  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
    User user= userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("user not found please try again"));
    return new CustomUserDetails(user);
  }
}
