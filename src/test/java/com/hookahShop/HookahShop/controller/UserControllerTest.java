package com.hookahShop.HookahShop.controller;

import com.hookahShop.HookahShop.dto.UserDto;
import com.hookahShop.HookahShop.model.User;
import com.hookahShop.HookahShop.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userServiceMock;

    @Test
    public void loginTest() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void singUpPageTest() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().attribute("user", new UserDto()));
    }

//    @Test
//    public void getUserInfoTest() throws Exception {
//        final User user = User.builder()
//                .id(1L)
//                .firstName("Test")
//                .phoneNumber("213")
//                .build();
//        final UserDto userDto = UserDto.builder()
//                .id(user.getId())
//                .firstName(user.getFirstName())
//                .phoneNumber(user.getPhoneNumber())
//                .build();
//        when(userServiceMock.getUserId()).thenReturn(1L);
//        when(userServiceMock.getUserById(anyLong())).thenReturn(user);
//
//        mockMvc.perform(get("/user_info"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("user_info"))
//                .andExpect(model().attribute("user", userDto));
//    }

//    @Test
//    public void registrationProcessTest() throws Exception {
//        User user = User.builder()
//                .id(1L)
//                .firstName("Test")
//                .phoneNumber("213")
//                .build();
//        when(userServiceMock.saveUser(null)).thenReturn(user);
//
//        mockMvc.perform(post("/registration"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("redirect:/login"))
//                .andExpect(model().attribute("user", new UserDto()));
//    }

}