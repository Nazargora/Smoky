package com.hookahShop.HookahShop.controller;

import com.hookahShop.HookahShop.model.Hookah;
import com.hookahShop.HookahShop.service.HookahService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/hookahs")
public class HookahController {

    private static final Logger logger = LoggerFactory.getLogger(HookahController.class);

    private final HookahService hookahService;

    @GetMapping
    public String getHookahs(@RequestParam(name = "name", required = false) final String hookahName,
                             @RequestParam(required = false) final String color,
                             @RequestParam(required = false) final Double price,
                             final Model model) {
        logger.info("Show filtered hookahs.");
        final List<Hookah> hookahs = hookahService.getHookahs(hookahName, color, price);
        model.addAttribute("hookahs", hookahs);

        return "hookahs";
    }


}
