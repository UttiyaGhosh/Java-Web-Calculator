package com.ug.webCalculator.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CalculatorService {

    String getEquation(List<String> inputList);
    String evaluateExpression(String expression);
}
