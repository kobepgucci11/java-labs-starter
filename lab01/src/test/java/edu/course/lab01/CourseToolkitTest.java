package edu.course.lab01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseToolkitTest {
    @Test
    void returnsTrueForNegativeEvenNumber() {
        int number = -8;
        boolean result = CourseToolkit.isEven(number);
        assertTrue(result);
    }

    @Test
    void minAndMaxReturnCorrectValues() {
        int[] arr = {4, 1, 8};
        assertEquals(1, CourseToolkit.min(arr));
        assertEquals(8, CourseToolkit.max(arr));
    }

    @Test
    void zeroIsEven() {
        assertTrue(CourseToolkit.isEven(0));
    }

    @Test
    void returnsTrueForEvenNumber() {
        boolean result = CourseToolkit.isEven(8);

        assertTrue(result);
    }

    @Test
    void returnsFalseForOddNumber() {
        boolean result = CourseToolkit.isEven(7);

        assertFalse(result);
    }

    public static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }
        for (int divisor = 2; divisor * divisor <= number; divisor++) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPalindrome(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Текст не может быть null");
        }
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static double average(int[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Массив не может быть null или пустым");
        }
        int sum = 0;
        for (int value : values) {
            sum += value;
        }
        return (double) sum / values.length;

    }

    // --- 4 теста для isPrime ---
    @Test
    void isPrimeReturnsFalseForLessThanTwo() {
        assertFalse(CourseToolkit.isPrime(-5));
        assertFalse(CourseToolkit.isPrime(0));
        assertFalse(CourseToolkit.isPrime(1));
    }

    @Test
    void isPrimeReturnsTrueForTwo() {
        assertTrue(CourseToolkit.isPrime(2));
    }

    @Test
    void isPrimeReturnsFalseForCompositeNumber() {
        assertFalse(CourseToolkit.isPrime(4));
        assertFalse(CourseToolkit.isPrime(100));
    }

    @Test
    void isPrimeReturnsFalseForSquareOfPrime() {
        assertFalse(CourseToolkit.isPrime(49)); // 7 * 7
    }

    // --- 3 теста для isPalindrome ---
    @Test
    void isPalindromeReturnsTrueForSimplePalindrome() {
        assertTrue(CourseToolkit.isPalindrome("шалаш"));
    }

    @Test
    void isPalindromeIsCaseAndSpaceSensitive() {
        assertFalse(CourseToolkit.isPalindrome("Шалаш")); // Регистр
        assertFalse(CourseToolkit.isPalindrome("ша лаш")); // Пробел
    }

    @Test
    void isPalindromeThrowsForNull() {
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.isPalindrome(null));
    }

    // --- Тесты для average (обычный, отрицательный, ошибочный) ---
    @Test
    void averageReturnsCorrectResultAndHandlesFractions() {
        assertEquals(4.0, CourseToolkit.average(new int[]{2, 4, 6})); // Обычный
        assertEquals(1.5, CourseToolkit.average(new int[]{1, 2}));    // Дробный
    }

    @Test
    void averageHandlesNegativeNumbers() {
        assertEquals(-4.0, CourseToolkit.average(new int[]{-2, -4, -6}));
    }

    @Test
    void averageThrowsForNullOrEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.average(null));
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.average(new int[]{}));
    }
}
