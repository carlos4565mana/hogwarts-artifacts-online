package com.carlos.springbootcurse.user;

import com.carlos.springbootcurse.system.exception.ObjectNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User findById(Integer userId){
        return this.userRepository.findById(userId).orElseThrow(
                ()-> new ObjectNotFoundException("user", userId));
    }
    public List<User> findAll(){
       return this.userRepository.findAll();
    }



    public User save(User newUser){
        newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
        return this.userRepository.save(newUser);
    }


    public User update(Integer userId,  User updateUser){
        User oldUser = this.userRepository.findById(userId)
                .orElseThrow(()->new ObjectNotFoundException("user",userId));

        oldUser.setUsername(updateUser.getUsername());
        oldUser.setEnabled(updateUser.isEnabled());
        oldUser.setRoles(updateUser.getRoles());

        return this.userRepository.save(oldUser);
    }

    public void delete(Integer userId){
        this.userRepository.findById(userId)
                .orElseThrow(()->new ObjectNotFoundException("user", userId));
        this.userRepository.deleteById(userId);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return this.userRepository.findByUsername(username)
                .map(MyUserPrincipal::new)
                .orElseThrow(()-> new UsernameNotFoundException("username"+username+" is not found!"));
    }

}















