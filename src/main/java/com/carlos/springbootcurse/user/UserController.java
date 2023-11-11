package com.carlos.springbootcurse.user;

import com.carlos.springbootcurse.system.Result;
import com.carlos.springbootcurse.system.StatusCode;
import com.carlos.springbootcurse.user.converter.UserDtoToUserConverter;
import com.carlos.springbootcurse.user.converter.UserToUserDtoConverter;
import com.carlos.springbootcurse.user.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class UserController {
    private final UserService userService;
    private final UserDtoToUserConverter userDtoToUserConverter;
    private final UserToUserDtoConverter userToUserDtoConverter;


    public UserController(UserService userService, UserDtoToUserConverter userDtoToUserConverter, UserToUserDtoConverter toUserDtoConverter) {
        this.userService = userService;
        this.userDtoToUserConverter = userDtoToUserConverter;
        this.userToUserDtoConverter = toUserDtoConverter;
    }

    @GetMapping("/{userId}")
    public Result findById(@PathVariable Integer userId) {
        User foundUser = this.userService.findById(userId);
        UserDto userDto = this.userToUserDtoConverter.convert(foundUser);

        return new Result(true, StatusCode.SUCCESS, "Find One Success", userDto);
    }
    @GetMapping
    public Result findAllUsers(){
        List<User> foundUsers = this.userService.findAll();
        List<UserDto> userDtos = foundUsers.stream()
                .map(this.userToUserDtoConverter::convert)
                .collect(Collectors.toList());
        return  new Result(true, StatusCode.SUCCESS,"Find All Success",userDtos);
    }

    @PostMapping
    public Result addUser(@Valid @RequestBody User newUser){
        User savedUser = this.userService.save(newUser);
        UserDto savedUserDto = this.userToUserDtoConverter.convert(savedUser);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedUserDto);
    }
    @PutMapping("/{userId}")
    public Result updateUser(@PathVariable Integer userId, @Valid @RequestBody UserDto userDto){
        User update = this.userDtoToUserConverter.convert(userDto);
        User updatedUser = this.userService.update(userId, update);
        UserDto updatedUserDto = this.userToUserDtoConverter.convert(updatedUser);

        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedUserDto);
    }

    @DeleteMapping("/{userId}")
    public Result deleteUser(@PathVariable Integer userId){
        this.userService.delete(userId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");

    }

}












