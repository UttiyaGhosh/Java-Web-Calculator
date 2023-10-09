package com.ug.calculator.controller;

import com.ug.calculator.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CalculatorController {

    @Autowired CalculatorService calculatorService;

    String previous = "0",current = "";

    @GetMapping("/")
    public String index(Model model) {
        current = "";
        model.addAttribute("previous", previous);
        model.addAttribute("current", "0");
        return "calculator";
    }

    @PostMapping("/")
    public String addToString(@RequestParam(name = "value") String value,Model model) {
        current += value;
        model.addAttribute("previous", previous);
        model.addAttribute("current", current);
        return "calculator";
    }

    @PostMapping("/evaluate")
    public String evaluate(Model model) {
        String result = calculatorService.evaluateExpression(current);

        previous = current;
        model.addAttribute("previous", previous);
        previous = previous + " = " + result;

        current = result;
        model.addAttribute("current", current);
        current = "";

        return "calculator";
    }
}
