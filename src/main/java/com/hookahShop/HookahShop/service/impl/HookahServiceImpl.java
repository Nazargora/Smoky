package com.hookahShop.HookahShop.service.impl;

import com.hookahShop.HookahShop.exception.HookahNotFoundException;
import com.hookahShop.HookahShop.model.Hookah;
import com.hookahShop.HookahShop.model.Order;
import com.hookahShop.HookahShop.repository.HookahRepository;
import com.hookahShop.HookahShop.service.HookahService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HookahServiceImpl implements HookahService {

    private final HookahRepository hookahRepository;

    @Override
    public Hookah getHookahById(long id) {
        return hookahRepository.findById(id);
    }

    @Override
    public List<Hookah> getHookahs(final String name,
                                   final String color,
                                   final Double price) {
        return hookahRepository.findAll().stream()
                .filter(Objects::nonNull)
                .filter(hookah -> hookah.getCount() > 0)
                .filter(hookah -> hookah.getName()
                        .equals((name != null && !name.equals("")) ? name : hookah.getName()))
                .filter(hookah -> hookah.getColor()
                        .equals(color != null && !color.equals("") ? color : hookah.getColor()))
                .filter(hookah -> hookah.getPrice() <= ((price != null) ? price : hookah.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateHookahOrderById(final long id, final Order order) {
        final Hookah hookah = getHookahById(id);
        List<Order> orders = hookah.getOrders();
        if (orders == null) {
            orders = new ArrayList<>();
        }
        orders.add(order);
        hookah.setCount(hookah.getCount() - 1);

        hookahRepository.save(hookah);
    }

    @Override
    public void deleteOrderFromHookahListById(final long hookahId, final Order order) {
        final Hookah hookah = hookahRepository.findById(hookahId);
        if (hookah != null) {
            final List<Order> orders = hookah.getOrders();
            orders.remove(order);

            hookahRepository.save(hookah);
        } else {
            throw new HookahNotFoundException(hookahId);
        }
    }
}
