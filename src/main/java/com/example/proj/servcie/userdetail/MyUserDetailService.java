package com.example.proj.servcie.userdetail;

import com.example.proj.model.user.User;
import com.example.proj.model.userprincipal.UserPrinciple;
import com.example.proj.repository.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MyUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findUserByUsername(username);
        if(user.isPresent()){
            return new UserPrinciple(user.get());
        }else{
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User not Found");
        }
    }
}
