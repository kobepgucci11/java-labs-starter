package edu.course.lab02;

import java.util.Arrays;

public class DataSample {
    private final String id;
    private String label;
    private SampleStatus status;
    private final double[] features;

    public DataSample(String id, String label, SampleStatus status, double[] features) {
        // инварианты id и label не null и не пустые
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID не может быть пустым");
        }
        if (label == null || label.trim().isEmpty()) {
            throw new IllegalArgumentException("Label не может быть пустым");
        }
        if (status == null) {
            throw new IllegalArgumentException("Статус не может быть null");
        }
        if (features == null) {
            throw new IllegalArgumentException("Массив признаков не может быть null");
        }

        this.id = id;
        this.label = label;
        this.status = status;
        // Копируем массив, чтобы внешний код не мог его изменить
        this.features = Arrays.copyOf(features, features.length);
    }

    // поведенческий метод для смены статуса (без setStatus)
    public void changeStatus(SampleStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Новый статус не может быть null");
        }
        this.status = newStatus;
    }

    // проверка готовности
    public boolean isReady() {
        return this.status == SampleStatus.READY;
    }

    // вычисление среднего значения признаков
    public double averageFeature() {
        if (features.length == 0) {
            return 0.0;
        }
        double sum = 0;
        for (double feature : features) {
            sum += feature;
        }
        return sum / features.length;
    }

    //  геттеры (без сеттеров)
    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public SampleStatus getStatus() {
        return status;
    }

    // возвращаем копию массива чтобы никто не мог изменить внутреннее состояние
    public double[] getFeatures() {
        return Arrays.copyOf(features, features.length);
    }
}