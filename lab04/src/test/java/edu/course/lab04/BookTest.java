package edu.course.lab04;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    void bookCanBeFoundByEqualInstance() {
        Book book1 = new Book("123", "Java");
        Book book2 = new Book("123", "Другое название");

        Map<Book, String> map = new HashMap<>();
        map.put(book1, "Значение");

        assertEquals("Значение", map.get(book2));
    }

    @Test
    void booksWithDifferentIsbnAreNotEqual() {
        Book book1 = new Book("123", "Java");
        Book book2 = new Book("456", "Java");
        assertNotEquals(book1, book2);
    }
}