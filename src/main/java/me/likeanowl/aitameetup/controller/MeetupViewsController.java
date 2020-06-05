package me.likeanowl.aitameetup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;

@Controller
public class MeetupViewsController {


    @GetMapping("/map")
    public Rendering map() {
        return Rendering.view("map").build();
    }

    @GetMapping("/boarding")
    public Rendering boarding() {
        return Rendering.view("boarding").build();
    }
}
