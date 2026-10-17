package edu.course.lab01;

/**
 * Небольшие методы для первой лабораторной работы.
 */
public final class CourseToolkit {

    private CourseToolkit() {
        // Утилитарный класс не должен иметь экземпляров.
    }

    public static boolean isEven(int number) {
        return number % 2 == 0;
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

    public static int min(int[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Массив не может быть null или пустым");
        }
        int min = values[0];
        for (int value : values) {
            if (value < min) min = value;
        }
        return min;
    }

    public static int max(int[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Массив не может быть null или пустым");
        }
        int max = values[0];
        for (int value : values) {
            if (value > max) max = value;
        }
        return max;
    }

}