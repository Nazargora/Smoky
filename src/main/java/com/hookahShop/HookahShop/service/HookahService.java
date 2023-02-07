package com.hookahShop.HookahShop.service;

import com.hookahShop.HookahShop.model.Hookah;
import com.hookahShop.HookahShop.model.Order;

import java.util.List;

public interface HookahService {

    List<Hookah> getHookahs(String name, String color, Double price);

    Hookah getHookahById(long id);

    void updateHookahOrderById(long id, Order order);

    void deleteOrderFromHookahListById(long hookahId, Order order);

}
