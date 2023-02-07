package com.hookahShop.HookahShop.controller;

import com.hookahShop.HookahShop.model.Hookah;
import com.hookahShop.HookahShop.service.HookahService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class HookahControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HookahService hookahServiceMock;

    @Test
    public void getHookahsTest() throws Exception {
        Hookah hookah = Hookah.builder()
                .id(1L)
                .name("Karma")
                .model("3.0")
                .color("red")
                .price(2000.0)
                .count(1)
                .build();
        when(hookahServiceMock.getHookahs(any(), any(), any())).thenReturn(Collections.singletonList(hookah));

        mockMvc.perform(get("/hookahs"))
                .andExpect(status().isOk())
                .andExpect(view().name("hookahs"))
                .andExpect(model().attribute("hookahs", hasSize(1)))
                .andExpect(model().attribute("hookahs", hasItem(allOf(
                        hasProperty("id", is(1L)),
                        hasProperty("name", is("Karma")),
                        hasProperty("model", is("3.0")),
                        hasProperty("color", is("red")),
                        hasProperty("price", is(2000.0)),
                        hasProperty("count", is(1))
                ))));
    }


}