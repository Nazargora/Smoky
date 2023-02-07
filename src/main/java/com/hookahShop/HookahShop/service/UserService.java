package com.hookahShop.HookahShop.service;

import com.hookahShop.HookahShop.dto.UserDto;
import com.hookahShop.HookahShop.model.User;

public interface UserService {

    User getUserById(long id);

    User getUserByEmail(String email);

    User saveUser(UserDto userDto);

    long getUserId();

}
