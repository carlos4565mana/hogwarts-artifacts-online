package com.carlos.springbootcurse.user;

import com.carlos.springbootcurse.system.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Integer userId){
        return this.userRepository.findById(userId).orElseThrow(
                ()-> new ObjectNotFoundException("user", userId));
    }
    public List<User> findAll(){
       return this.userRepository.findAll();
    }

    public User save(User newUser){
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
}







