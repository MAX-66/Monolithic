package com.brenden.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 做点什么
 *
 * @author lx
 * @date 2026-03-17 09:42
 */

@RequiredArgsConstructor
@RestController
public class TestController {

    @RequestMapping("/")
    public String test() {
        return "hello security";
    }

}
