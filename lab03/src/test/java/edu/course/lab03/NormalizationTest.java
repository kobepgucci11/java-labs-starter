package edu.course.lab03;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NormalizationTest {

    @Test
    void minMaxNormalizationWorks() {
        FeatureProcessor processor = new FeatureProcessor(new MinMaxNormalization());
        double[] result = processor.process(new double[]{10.0, 20.0, 30.0});
        assertArrayEquals(new double[]{0.0, 0.5, 1.0}, result);
    }

    @Test
    void meanCenteringWorks() {
        FeatureProcessor processor = new FeatureProcessor(new MeanCenteringNormalization());
        double[] result = processor.process(new double[]{2.0, 4.0, 6.0});
        assertArrayEquals(new double[]{-2.0, 0.0, 2.0}, result);
    }

    @Test
    void processorStrategyIsReplaceable() {
        double[] input = {2.0, 4.0, 6.0};

        FeatureProcessor minMax = new FeatureProcessor(new MinMaxNormalization());
        FeatureProcessor meanCent = new FeatureProcessor(new MeanCenteringNormalization());

        assertArrayEquals(new double[]{0.0, 0.5, 1.0}, minMax.process(input));
        assertArrayEquals(new double[]{-2.0, 0.0, 2.0}, meanCent.process(input));
    }
}