package edu.course.lab03;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiscountTest {
    @Test
    void noDiscountReturnsOriginalPrice() {
        PriceCalculator calc = new PriceCalculator(new NoDiscount());
        assertEquals(100.0, calc.calculate(100.0));
    }

    @Test
    void percentDiscountAppliesCorrectly() {
        PriceCalculator calc = new PriceCalculator(new PercentDiscount(20));
        assertEquals(80.0, calc.calculate(100.0));
    }

    @Test
    void calculatorPolicyIsReplaceable() {
        // Один и тот же калькулятор, меняем только стратегию
        PriceCalculator calc1 = new PriceCalculator(new NoDiscount());
        PriceCalculator calc2 = new PriceCalculator(new PercentDiscount(50));

        assertEquals(100.0, calc1.calculate(100.0));
        assertEquals(50.0, calc2.calculate(100.0));
    }
}