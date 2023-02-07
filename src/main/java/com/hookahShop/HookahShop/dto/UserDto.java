package com.hookahShop.HookahShop.dto;

import com.hookahShop.HookahShop.model.User;
import com.hookahShop.HookahShop.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;
    private String phoneNumber;
    private String password;

    private Long addressId;
    private String city;
    private String streetName;
    private String streetNumber;

    public static UserDto toDto(final User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .phoneNumber(user.getPhoneNumber())
                .password(user.getPassword())
                .addressId(user.getAddress().getId())
                .city(user.getAddress().getCity())
                .streetName(user.getAddress().getStreetName())
                .streetNumber(user.getAddress().getStreetNumber())
                .build();
    }

}
