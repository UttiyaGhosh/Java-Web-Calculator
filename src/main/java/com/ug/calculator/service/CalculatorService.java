package com.ug.calculator.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
public interface CalculatorService {

    String getEquation(List<String> inputList);
    String evaluateExpression(String expression);
}
