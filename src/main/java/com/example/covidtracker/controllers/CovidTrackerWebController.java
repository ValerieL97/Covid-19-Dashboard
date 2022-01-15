package com.example.covidtracker.controllers;

import com.example.covidtracker.services.CovidInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class CovidTrackerWebController {

    @GetMapping("/")
    public String getPage() {
        return "index";
    }
}
