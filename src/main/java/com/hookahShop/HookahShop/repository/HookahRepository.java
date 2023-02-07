package com.hookahShop.HookahShop.repository;

import com.hookahShop.HookahShop.model.Hookah;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HookahRepository extends JpaRepository<Hookah, Long> {

    Hookah findById(long id);
}
