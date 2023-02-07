package com.hookahShop.HookahShop.controller;

import com.hookahShop.HookahShop.model.Hookah;
import com.hookahShop.HookahShop.model.Order;
import com.hookahShop.HookahShop.model.enums.OrderStatus;
import com.hookahShop.HookahShop.service.HookahService;
import com.hookahShop.HookahShop.service.OrderService;
import com.hookahShop.HookahShop.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final UserService userService;
    private final OrderService orderService;
    private final HookahService hookahService;

    @GetMapping
    public String getOrdersByUserId(final Model model) {
        final long userId = userService.getUserId();
        logger.info("The list of closed user orders from id: {}", userId);
        final List<Order> closedOrders = orderService.getClosedOrdersByUserId(userId);
        model.addAttribute("orders", closedOrders);

        return "orders";
    }

    @GetMapping("/current")
    public String getCurrentOrderByUserId(final Model model) {
        final long userId = userService.getUserId();
        logger.info("The current user order from id: {}", userId);
        final Order currentOrder = orderService.getCurrentOrderByUserId(userId);
        if (!currentOrder.getStatus().equals(OrderStatus.NEW)&& currentOrder.getFullPrice() != 0.0) {
            model.addAttribute("order", currentOrder);
            return "/currentOrder";
        } else {
            return "redirect:/orders";
        }

    }

    @PostMapping("/orderHookah/{id}")
    public String addToCurrentOrder(@PathVariable(name = "id") final long hookahId) {
        final long userId = userService.getUserId();
        logger.info("The current user order from id: {}", userId);
        final Order currentOrder = orderService.getCurrentOrderByUserId(userId);

        final Hookah hookah = hookahService.getHookahById(hookahId);
        logger.info("Add hookah to the current order with id: {}", currentOrder.getId());
        final Order order = orderService.addHookahToCurrentOrder(hookah, currentOrder, userId);
        logger.info("Update hookah with id: {}", hookah.getId());
        hookahService.updateHookahOrderById(hookah.getId(), order);

        return "redirect:/orders/current";
    }

    @GetMapping("/current/{id}/deleteHookah/{hookahId}")
    public String deleteHookahFromOrder(@PathVariable(name = "id") final long orderId,
                                        @PathVariable final long hookahId) {
        final Hookah hookah = hookahService.getHookahById(hookahId);
        logger.info("Delete hookah with id: {} from current order with id: {}", hookah.getId(), orderId);
        orderService.deleteHookahFromOrderById(orderId, hookah);

        final Order order = orderService.getOrderById(orderId);
        logger.info("Delete order from hookah with id: {}", hookah.getId());
        hookahService.deleteOrderFromHookahListById(hookahId, order);

        return "redirect:/orders/current";
    }

    @GetMapping("/order")
    public String finishOrder(final Model model) {
        final long userId = userService.getUserId();
        final Order currentOrder = orderService.getCurrentOrderByUserId(userId);

        logger.info("Change order status to closed with id: {}", currentOrder.getId());
        orderService.finishOrder(currentOrder);

        model.addAttribute("order", currentOrder);
        return "order_success_page";
    }

}
