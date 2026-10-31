package edu.course.lab02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DataSampleTest {

    @Test
    void createsCorrectObject() {
        DataSample sample = new DataSample("id-1", "cat", SampleStatus.NEW, new double[]{1.0, 2.0, 3.0});
        assertEquals("id-1", sample.getId());
        assertEquals(SampleStatus.NEW, sample.getStatus());
        assertFalse(sample.isReady());
    }

    @Test
    void changeStatusWorks() {
        DataSample sample = new DataSample("id-1", "cat", SampleStatus.NEW, new double[]{1.0});
        sample.changeStatus(SampleStatus.READY);
        assertTrue(sample.isReady());
    }

    @Test
    void calculatesAverageFeature() {
        DataSample sample = new DataSample("id-1", "cat", SampleStatus.NEW, new double[]{2.0, 4.0, 6.0});
        assertEquals(4.0, sample.averageFeature());
    }

    @Test
    void constructorThrowsForEmptyId() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("", "cat", SampleStatus.NEW, new double[]{1.0}));
    }

    @Test
    void featuresArrayIsEncapsulated() {
        double[] originalArr = {1.0, 2.0};
        DataSample sample = new DataSample("id-1", "cat", SampleStatus.NEW, originalArr);

        originalArr[0] = 99.0;
        assertEquals(1.0, sample.getFeatures()[0]);

        double[] returnedArr = sample.getFeatures();
        returnedArr[0] = 99.0;
        assertEquals(1.0, sample.getFeatures()[0]);
    }
}