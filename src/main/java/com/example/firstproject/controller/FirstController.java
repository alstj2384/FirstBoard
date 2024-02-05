package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/")
    public String niceToMeetYou(Model model){
        model.addAttribute("username", "민서");
        return "index.html"; // 템플릿 파일의 이
    }

    @GetMapping("/bye")
    public String goodBye(Model model){
        model.addAttribute("nickname", "민서");
        return "goodbye";
    }
}
