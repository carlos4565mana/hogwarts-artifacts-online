package com.carlos.springbootcurse.user.converter;

import com.carlos.springbootcurse.user.User;
import com.carlos.springbootcurse.user.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserConverter implements Converter<UserDto, User> {

    @Override
    public User convert(UserDto source) {
        User user = new User();
        user.setUsername(source.username());
        user.setEnabled(source.enabled());
        user.setRoles(source.roles());
        return user;
    }

}
