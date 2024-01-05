package com.ug.webCalculator.service.impl;

import com.ug.webCalculator.service.CalculatorService;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.*;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    public String getEquation(List<String> inputList) {
        if (inputList.isEmpty())
            return "0";
        return StringUtils.join(inputList, "");
    }

    public String evaluateExpression(String expression) {
        Double result = evaluate(expression);
        String resultString = result.toString();
        return resultString.substring(0,Math.min(resultString.indexOf('.')+3,resultString.length()));
    }

    private Double evaluate(String expression) {
        Queue<String> outputQueue = new LinkedList<>();
        Stack<String> operatorStack = new Stack<>();

        Map<String, Integer> precedence = new HashMap<>();
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);

        String[] tokens = expression.split("\\s+");
        for (String token : tokens) {
            if (token.matches("[0-9]+(\\.[0-9]+)?")) {
                // Token is a number
                outputQueue.add(token);
            } else if (token.matches("[+\\-*/]")) {
                // Token is an operator
                while (!operatorStack.isEmpty() &&
                        precedence.get(token) <= precedence.get(operatorStack.peek())) {
                    outputQueue.add(operatorStack.pop());
                }
                operatorStack.push(token);
            } else if (token.equals("(")) {
                // Token is an opening parenthesis
                operatorStack.push(token);
            } else if (token.equals(")")) {
                // Token is a closing parenthesis
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    outputQueue.add(operatorStack.pop());
                }
                if (!operatorStack.isEmpty() && operatorStack.peek().equals("(")) {
                    operatorStack.pop();
                } else {
                    throw new IllegalArgumentException("Mismatched parentheses.");
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek().equals("(")) {
                throw new IllegalArgumentException("Mismatched parentheses.");
            }
            outputQueue.add(operatorStack.pop());
        }

        Stack<Double> evaluationStack = new Stack<>();
        for (String token : outputQueue) {
            if (token.matches("[0-9]+(\\.[0-9]+)?")) {
                evaluationStack.push(Double.parseDouble(token));
            } else if (token.matches("[+\\-*/]")) {
                double operand2 = evaluationStack.pop();
                double operand1 = evaluationStack.pop();
                switch (token) {
                    case "+":
                        evaluationStack.push(operand1 + operand2);
                        break;
                    case "-":
                        evaluationStack.push(operand1 - operand2);
                        break;
                    case "*":
                        evaluationStack.push(operand1 * operand2);
                        break;
                    case "/":
                        if (operand2 == 0) {
                            throw new ArithmeticException("Division by zero.");
                        }
                        evaluationStack.push(operand1 / operand2);
                        break;
                }
            }
        }

        if (evaluationStack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression.");
        }

        return evaluationStack.pop();
    }


}
