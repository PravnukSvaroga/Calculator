
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CalculatorTest {

    @Test
    public void calculate() {
        String expression1 = "13+2";
        double result1 = Calculator.calculate(expression1);
        assertEquals(15, result1, 0.0001);

        String expression2 = "5-8.1";
        double result2 = Calculator.calculate(expression2);
        assertEquals(-3.1, result2, 0.0001);

        String expression3 = "2*(2-1)";
        double result3 = Calculator.calculate(expression3);
        assertEquals(2, result3, 0.0001);

        String expression4 = "10/((8-4)*2/(12-10))";
        double result4 = Calculator.calculate(expression4);
        assertEquals(2.5, result4, 0.0001);

        String expression5 = "1+2";
        double result5 = Calculator.calculate(expression5);
        assertEquals(3, result5, 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWithInvalidExpression() {
        Calculator.calculate("(77+9))");
    }

    @Test(expected = ArithmeticException.class)
    public void testCalculateWithDivideByZero() {
        Calculator.calculate("0/0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWithInvalidCharacter() {
        Calculator.calculate("1с");
    }
}

