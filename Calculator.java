/**
 * Класс, реализующий простой калькулятор для вычисления арифметических выражений.
 */
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение:");
        String expression = scanner.nextLine();
        scanner.close();

        try {
            double result = calculate(expression);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    private static final String OPERATORS = "/*-+";
    private static final Map<Character, Integer> PRECEDENCES = Map.of(
            '*',1,
            '/',1,
            '-',2,
            '+',2
    );

    /**
     * Метод для вычисления значения арифметического выражения.
     * @param expression - арифметическое выражение для вычисления, экземпляр класса String.
     * @return результат вычисления выражения типа double.
     */
    public static double calculate(String expression) {
        expression = expression.replaceAll("\\s+", "");

        ArrayDeque<Double> operands = new ArrayDeque<>();
        ArrayDeque<Character> operators = new ArrayDeque<>();

        for (int i = 0; i < expression.length(); i++) {
            char token = expression.charAt(i);

            if (Character.isDigit(token)) {
                StringBuilder num = new StringBuilder();
                num.append(token);

                while (i + 1 < expression.length() && (Character.isDigit(expression.charAt(i + 1)) || expression.charAt(i + 1) == '.')) {
                    num.append(expression.charAt(i + 1));
                    i++;
                }

                operands.push(Double.parseDouble(num.toString()));
            } else if (token == '(') {
                operators.push(token);
            } else if (token == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    double result = performOperation(operands, operators);
                    operands.push(result);
                }

                if (!operators.isEmpty() && operators.peek() == '(') {
                    operators.pop();
                } else {
                    throw new IllegalArgumentException("Некорректное выражение: непарная скобка");
                }
            } else if (OPERATORS.contains(String.valueOf(token))) {
                while (!operators.isEmpty() &&
                        (PRECEDENCES.get(token)!=null?PRECEDENCES.get(token):0) <=
                                (PRECEDENCES.get(operators.peek())!=null?PRECEDENCES.get(operators.peek()):0)) {
                    double result = performOperation(operands, operators);
                    operands.push(result);
                }

                operators.push(token);
            } else {
                throw new IllegalArgumentException("Некорректное выражение: недопустимый символ " + token);
            }
        }

        while (!operators.isEmpty()) {
            double result = performOperation(operands, operators);
            operands.push(result);
        }

        if (operands.size() != 1) {
            throw new IllegalArgumentException("Некорректное выражение: неправильное количество операндов и операторов");
        }

        return operands.pop();
    }

    /**
     * Метод для выполнения операции над двумя операндами и оператором.
     * @param numbers - стек чисел, экзмепляр класса ArrayDeque.
     * @param operators - стек операторов, экзмепляр класса ArrayDeque.
     * @return результат операции типа double.
     */
    private static double performOperation(ArrayDeque<Double> numbers, ArrayDeque<Character> operators) {
        double operand2 = numbers.pop();
        double operand1 = numbers.pop();
        char operator = operators.pop();

        return switch (operator) {
            case '+' -> operand1 + operand2;
            case '-' -> operand1 - operand2;
            case '*' -> operand1 * operand2;
            case '/' -> {
                if (operand2 == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                yield operand1 / operand2;
            }
            default -> throw new IllegalArgumentException("Некорректный оператор: " + operator);
        };
    }
}