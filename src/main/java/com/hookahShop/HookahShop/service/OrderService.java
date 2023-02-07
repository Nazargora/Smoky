package com.hookahShop.HookahShop.service;

import com.hookahShop.HookahShop.model.Hookah;
import com.hookahShop.HookahShop.model.Order;

import java.util.List;

public interface OrderService {

    Order getCurrentOrderByUserId(long userId);

    List<Order> getClosedOrdersByUserId(long userId);

    Order addHookahToCurrentOrder(Hookah hookah, Order currentOrder, long userId);

    void deleteHookahFromOrderById(long orderId, Hookah hookah);

    Order getOrderById(long id);

    void finishOrder(Order order);

}
