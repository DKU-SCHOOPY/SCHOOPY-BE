package com.schoopy.back.home.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoopy.back.event.entity.EventEntity;
import com.schoopy.back.home.service.HomeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schoopy/v1/home")
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/home") // 홈화면
    public ResponseEntity<List<EventEntity>> getAllEvents() {
        return ResponseEntity.ok(homeService.getAllEvents());
    }
}
