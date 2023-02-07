package com.hookahShop.HookahShop.service.impl;

import com.hookahShop.HookahShop.exception.OrderNotFoundException;
import com.hookahShop.HookahShop.model.Hookah;
import com.hookahShop.HookahShop.model.Order;
import com.hookahShop.HookahShop.model.User;
import com.hookahShop.HookahShop.model.enums.OrderStatus;
import com.hookahShop.HookahShop.repository.OrderRepository;
import com.hookahShop.HookahShop.service.OrderService;
import com.hookahShop.HookahShop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    public Order getCurrentOrderByUserId(long userId) {
        return orderRepository.findAll()
                .stream()
                .filter(Objects::nonNull)
                .filter(order -> order.getUser() != null)
                .filter(order -> order.getUser().getId() == userId)
                .filter(order -> order.getStatus().equals(OrderStatus.NEW)
                        || order.getStatus().equals(OrderStatus.IN_PROCESS))
                .findFirst()
                .orElse(createNewOrder(userId));

    }

    @Override
    public List<Order> getClosedOrdersByUserId(long userId) {
        return orderRepository.findAll()
                .stream()
                .filter(Objects::nonNull)
                .filter(order -> order.getUser() != null)
                .filter(order -> order.getUser().getId() == userId)
                .filter(order -> order.getStatus().equals(OrderStatus.CLOSED))
                .collect(Collectors.toList());
    }

    @Override
    public Order addHookahToCurrentOrder(final Hookah hookah, final Order currentOrder, final long userId) {
        final List<Hookah> hookahs = Optional.ofNullable(currentOrder.getHookahs())
                .orElse(new ArrayList<>());
        hookahs.add(hookah);
        currentOrder.setStatus(OrderStatus.IN_PROCESS);
        currentOrder.setFullPrice(currentOrder.getFullPrice() + hookah.getPrice());
        return orderRepository.save(currentOrder);
    }

    @Override
    public void deleteHookahFromOrderById(final long orderId, final Hookah hookah) {
        final Order order = orderRepository.findById(orderId);
        final List<Hookah> hookahs = order.getHookahs();
        hookahs.remove(hookah);
        order.setFullPrice(order.getFullPrice() - hookah.getPrice());
        orderRepository.save(order);

    }

    @Override
    public Order getOrderById(final long orderId) {
        return Optional.ofNullable(orderRepository.findById(orderId))
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @Override
    public void finishOrder(final Order order) {
        order.setStatus(OrderStatus.CLOSED);
        orderRepository.save(order);
    }

    private Order createNewOrder(final long userId) {
        final User user = userService.getUserById(userId);
        Order order = Order.builder()
                .createDateTime(LocalDateTime.now())
                .status(OrderStatus.NEW)
                .user(user)
                .build();
        return orderRepository.save(order);
    }

}
