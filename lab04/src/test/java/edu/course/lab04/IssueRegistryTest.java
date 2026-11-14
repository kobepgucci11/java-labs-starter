package edu.course.lab04;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IssueRegistryTest {

    @Test
    void registryWorksWithDifferentInstances() {
        IssueRegistry registry = new IssueRegistry();
        IssueId id1 = new IssueId("PROJ", 1);
        ProjectTask task = new ProjectTask("Fix bug");

        registry.add(id1, task);

        IssueId id2 = new IssueId("PROJ", 1); // другой экземпляр но логически равный
        assertTrue(registry.contains(id2));
        assertEquals("Fix bug", registry.get(id2).getTitle());
    }

    @Test
    void addingSameKeyReplacesValueAndSizeDoesNotGrow() {
        IssueRegistry registry = new IssueRegistry();
        IssueId id1 = new IssueId("PROJ", 1);

        registry.add(id1, new ProjectTask("Task 1"));
        registry.add(new IssueId("PROJ", 1), new ProjectTask("Task 2")); // Тот же ключ

        assertEquals(1, registry.size());
        assertEquals("Task 2", registry.get(id1).getTitle());
    }

    @Test
    void differentKeysWithSameHashCodeDoNotConflict() {
        // Создаем ключи с одинаковым хешем (намеренно)
        IssueId id1 = new IssueId("A", 1) {
            @Override
            public int hashCode() { return 42; }
        };
        IssueId id2 = new IssueId("B", 1) {
            @Override
            public int hashCode() { return 42; }
        };

        IssueRegistry registry = new IssueRegistry();
        registry.add(id1, new ProjectTask("Task A"));
        registry.add(id2, new ProjectTask("Task B"));

        assertEquals(2, registry.size());
        assertEquals("Task A", registry.get(id1).getTitle());
        assertEquals("Task B", registry.get(id2).getTitle());
    }

    @Test
    void equalsReturnsFalseForNullOrDifferentType() {
        IssueId id = new IssueId("PROJ", 1);
        assertFalse(id.equals(null));
        assertFalse(id.equals("Строка"));
    }

    @Test
    void mutableKeyBreaksHashMap() {
        // Эксперимент с изменяемым ключом
        class MutableKey {
            String code;
            MutableKey(String code) { this.code = code; }
            @Override public boolean equals(Object o) {
                return o instanceof MutableKey && ((MutableKey)o).code.equals(code);
            }
            @Override public int hashCode() { return code.hashCode(); }
        }

        MutableKey key = new MutableKey("TEST");
        IssueRegistry registry = new IssueRegistry();
        // Используем обычный HashMap так как IssueRegistry ожидает IssueId
        java.util.Map<MutableKey, String> map = new java.util.HashMap<>();
        map.put(key, "Data");

        key.code = "CHANGED"; // меняю поле участвующее в hashCode

        assertNull(map.get(new MutableKey("TEST"))); // старый ключ больше не находится
        assertNull(map.get(new MutableKey("CHANGED"))); // и новый тоже так как хеш изменился
    }
}