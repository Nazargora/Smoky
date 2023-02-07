package com.hookahShop.HookahShop.service.impl;

import com.hookahShop.HookahShop.model.Hookah;
import com.hookahShop.HookahShop.model.Order;
import com.hookahShop.HookahShop.model.enums.OrderStatus;
import com.hookahShop.HookahShop.repository.HookahRepository;
import com.hookahShop.HookahShop.repository.OrderRepository;
import com.hookahShop.HookahShop.service.HookahService;
import com.hookahShop.HookahShop.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HookahServiceImplTest {
    @Mock
    private HookahRepository hookahRepositoryMock;

    @Autowired
    private HookahService hookahService;

    @Before
    public void setUp() {
        hookahService = new HookahServiceImpl(hookahRepositoryMock);
    }

    @Test
    public void getHookahsTest() {
        Hookah hookah1 = Hookah.builder().id(1L).name("Test").price(30).color("red").count(3).build();
        Hookah hookah2 = Hookah.builder().id(2L).name("Test2").price(30).color("red").count(0).build();
        List<Hookah> hookahList = new ArrayList<>();
        hookahList.add(hookah1);
        hookahList.add(hookah2);
        when(hookahRepositoryMock.findAll()).thenReturn(hookahList);
        List<Hookah> actualHookahs = hookahService.getHookahs(null, null, null);
        assertEquals(Collections.singletonList(hookah1), actualHookahs);

    }

    @Test
    public void getHookahsTest_ShouldReturnFilteredList() {
        Hookah hookah1 = Hookah.builder().id(1L).name("Test").price(30).color("red").count(3).build();
        Hookah hookah2 = Hookah.builder().id(2L).name("Test2").price(30).color("red").count(0).build();
        List<Hookah> hookahList = new ArrayList<>();
        hookahList.add(hookah1);
        hookahList.add(hookah2);
        when(hookahRepositoryMock.findAll()).thenReturn(hookahList);
        List<Hookah> actualHookahs = hookahService.getHookahs("Test", "red", 300.0);
        assertEquals(Collections.singletonList(hookah1), actualHookahs);

    }

    @Test
    public void getHookahByIdTest() {
        Hookah hookah = Hookah.builder().id(1L).name("Test").price(30).color("red").count(3).build();
        when(hookahRepositoryMock.findById(anyLong())).thenReturn(hookah);
        Hookah actualHookahById = hookahService.getHookahById(1L);
        assertEquals(hookah, actualHookahById);
    }

    @Test
    public void updateHookahOrderById() {
        Hookah hookah = Hookah.builder().id(1L).name("Test").price(30).color("red").count(3).build();
        Order order = Order.builder().id(1L).status(OrderStatus.IN_PROCESS).build();

        when(hookahRepositoryMock.findById(anyLong())).thenReturn(hookah);
        when(hookahRepositoryMock.save(any(Hookah.class))).thenReturn(hookah);

        hookahService.updateHookahOrderById(1, order);
        assertEquals(2, hookah.getCount());
        assertEquals(Collections.singletonList(order), hookah.getOrders());
    }

}