package com.hookahShop.HookahShop.service.impl;

import com.hookahShop.HookahShop.dto.UserDto;
import com.hookahShop.HookahShop.exception.UserNotFoundException;
import com.hookahShop.HookahShop.model.Address;
import com.hookahShop.HookahShop.model.User;
import com.hookahShop.HookahShop.model.enums.UserRole;
import com.hookahShop.HookahShop.repository.AddressRepository;
import com.hookahShop.HookahShop.repository.UserRepository;
import com.hookahShop.HookahShop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AddressRepository addressRepository;

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            return user.getId();
        }
        throw new UserNotFoundException(authentication.getName());
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(UserDto userDto) {
        User userByEmail = getUserByEmail(userDto.getEmail());
        if (userByEmail == null) {
            final Address address = Address.builder()
                    .city(userDto.getCity())
                    .streetName(userDto.getStreetName())
                    .streetNumber(userDto.getStreetNumber())
                    .build();
            final Address savedAddress = addressRepository.save(address);

            final User user = User.builder()
                    .email(userDto.getEmail())
                    .firstName(userDto.getFirstName())
                    .lastName(userDto.getLastName())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .role(UserRole.CLIENT)
                    .phoneNumber(userDto.getPhoneNumber())
                    .address(savedAddress)
                    .build();

            return userRepository.save(user);
        }
        throw new RuntimeException("User with: " + userDto.getEmail() + " already exist.");
    }

}
