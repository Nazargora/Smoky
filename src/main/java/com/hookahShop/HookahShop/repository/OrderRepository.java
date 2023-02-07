package com.hookahShop.HookahShop.repository;

import com.hookahShop.HookahShop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findById(long id);

}
