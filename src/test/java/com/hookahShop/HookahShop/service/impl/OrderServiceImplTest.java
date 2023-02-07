package com.hookahShop.HookahShop.service.impl;

import com.hookahShop.HookahShop.exception.OrderNotFoundException;
import com.hookahShop.HookahShop.model.Hookah;
import com.hookahShop.HookahShop.model.Order;
import com.hookahShop.HookahShop.model.User;
import com.hookahShop.HookahShop.model.enums.OrderStatus;
import com.hookahShop.HookahShop.repository.OrderRepository;
import com.hookahShop.HookahShop.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepositoryMock;
    @Mock
    private UserServiceImpl userServiceMock;
    @Autowired
    private OrderService orderService;

    @Before
    public void setUp() {
        orderService = new OrderServiceImpl(orderRepositoryMock, userServiceMock);
    }

    @Test
    public void getCurrentOrderByUserIdTest() {
        final User user = User.builder()
                .id(1L)
                .firstName("Test")
                .lastName("Test")
                .phoneNumber("123")
                .build();
        final Order order1 = Order.builder()
                .id(1L)
                .status(OrderStatus.NEW)
                .user(user)
                .build();
        final Order order2 = Order.builder()
                .id(2L)
                .status(OrderStatus.CLOSED)
                .user(user)
                .build();
        when(orderRepositoryMock.findAll()).thenReturn(Arrays.asList(order1, order2));

        final Order actualOrder = orderService.getCurrentOrderByUserId(1L);
        assertEquals(order1, actualOrder);
    }

    @Test
    public void getCurrentOrderByUserIdTest_ShouldReturnNull_NoCurrentOrder() {
        final User user = User.builder()
                .id(1L)
                .build();
        final Order order = Order.builder()
                .id(2L)
                .status(OrderStatus.CLOSED)
                .user(user)
                .build();
        when(orderRepositoryMock.findAll()).thenReturn(Collections.singletonList(order));

        final Order actualOrderAsNull = orderService.getCurrentOrderByUserId(1L);
        assertNull(actualOrderAsNull);
    }

    @Test
    public void getCurrentOrderByUserIdTest_ShouldReturnNull_OrderListIsEmpty() {
        when(orderRepositoryMock.findAll()).thenReturn(new ArrayList<>());

        final Order actualOrderAsNull = orderService.getCurrentOrderByUserId(1L);
        assertNull(actualOrderAsNull);
    }

    @Test
    public void getClosedOrdersByUserIdTest() {
        final User user = User.builder()
                .id(1L)
                .firstName("Test")
                .lastName("Test")
                .phoneNumber("123")
                .build();
        final Order order1 = Order.builder()
                .id(1L)
                .status(OrderStatus.NEW)
                .user(user)
                .build();
        final Order order2 = Order.builder()
                .id(2L)
                .status(OrderStatus.CLOSED)
                .user(user)
                .build();
        final Order order3 = Order.builder()
                .id(3L)
                .status(OrderStatus.CLOSED)
                .user(user)
                .build();
        when(orderRepositoryMock.findAll()).thenReturn(Arrays.asList(order1, order2, order3));

        final List<Order> actualClosedOrders = orderService.getClosedOrdersByUserId(1L);
        assertEquals(Arrays.asList(order2, order3), actualClosedOrders);
    }

    @Test
    public void getClosedOrdersByUserIdTest_ShouldReturnEmptyCollection() {
        when(orderRepositoryMock.findAll()).thenReturn(Arrays.asList(new Order(), new Order()));

        final List<Order> actualClosedOrdersEmptyList = orderService.getClosedOrdersByUserId(1L);
        assertEquals(new ArrayList<>(), actualClosedOrdersEmptyList);
    }

    @Test
    public void addHookahToCurrentOrder_OrderIsExist() {
        final Hookah hookah = Hookah.builder()
                .id(2L)
                .name("Test")
                .price(1000.1)
                .build();
        final List<Hookah> hookahs = new ArrayList<>();
        hookahs.add(hookah);

        final Order currentOrder = Order.builder()
                .id(1L)
                .status(OrderStatus.NEW)
                .fullPrice(2000.0)
                .hookahs(hookahs)
                .build();

        when(orderRepositoryMock.save(any(Order.class))).thenReturn(currentOrder);

        final Order actualOrder = orderService.addHookahToCurrentOrder(hookah, currentOrder, 1L);
        assertEquals(OrderStatus.IN_PROCESS, actualOrder.getStatus());
        assertEquals(3000.1, actualOrder.getFullPrice());
        assertEquals(2, currentOrder.getHookahs().size());
    }

    @Test
    public void deleteHookahFromOrderByIdTest() {
        final Hookah hookah1 = Hookah.builder()
                .id(1L)
                .name("Test 1")
                .price(2000.0)
                .build();
        final Hookah hookah2 = Hookah.builder()
                .id(2L)
                .name("Test 2")
                .price(1000.0)
                .build();
        final List<Hookah> hookahs = new ArrayList<>();
        hookahs.add(hookah1);
        hookahs.add(hookah2);

        final Order order = Order.builder()
                .id(1L)
                .status(OrderStatus.IN_PROCESS)
                .fullPrice(3000.0)
                .hookahs(hookahs)
                .build();

        when(orderRepositoryMock.findById(anyLong())).thenReturn(order);
        when(orderRepositoryMock.save(any(Order.class))).thenReturn(order);

        orderService.deleteHookahFromOrderById(1L, hookah1);
        assertEquals(1000.0, order.getFullPrice());
        assertEquals(1, order.getHookahs().size());
    }

    @Test
    public void getOrderByIdTest() {
        final Order order = Order.builder()
                .id(1L)
                .status(OrderStatus.IN_PROCESS)
                .build();
        when(orderRepositoryMock.findById(anyLong())).thenReturn(order);

        final Order actualOrder = orderService.getOrderById(1L);
        assertEquals(order, actualOrder);
    }

    @Test
    public void getOrderByIdTest_ThrowOrderNotFoundException() {
        when(orderRepositoryMock.findById(anyLong())).thenReturn(null);

        Exception actualException = assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(1L));
        assertEquals("Order with id: 1 not found.", actualException.getMessage());
    }

    @Test
    public void finishOrderTest() {
        final Order order = Order.builder()
                .id(1L)
                .status(OrderStatus.IN_PROCESS)
                .build();
        when(orderRepositoryMock.save(any(Order.class))).thenReturn(order);

        orderService.finishOrder(order);
        assertEquals(OrderStatus.CLOSED, order.getStatus());
    }
}