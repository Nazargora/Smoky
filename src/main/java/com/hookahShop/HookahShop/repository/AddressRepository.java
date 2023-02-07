package com.hookahShop.HookahShop.repository;

import com.hookahShop.HookahShop.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {



}
