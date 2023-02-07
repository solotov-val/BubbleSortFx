package com.example.bubblesortfx;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BubbleSortVisualization extends Application {
    private int[] array;
    private int currentIndex;
    private int endIndex;
    private BarChart<String, Number> barChart;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Initialize the array with random values
        array = new int[10];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 100);
        }

        // Create the bar chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Bubble Sort Animation");
        barChart.setAnimated(false);

        // Create the series for the bar chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 0; i < array.length; i++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(i), array[i]));
        }
        barChart.getData().add(series);

        // Create the scene and add the chart to it
        Scene scene = new Scene(barChart, 800, 600);
        stage.setScene(scene);
        stage.show();

        // Start the bubble sort algorithm
        currentIndex = 0;
        endIndex = array.length - 1;
        sort();
    }

    private void sort() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(300), event -> {
            boolean isSorted = true;
            for (int i = 0; i < endIndex; i++) {
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    isSorted = false;
                }
            }
            if (isSorted) {
                timeline.stop();
                return;
            }
            endIndex--;
            updateChart();
        }));
        timeline.play();
    }

    private void updateChart() {
        for (int i = 0; i < array.length; i++) {
            XYChart.Data<String, Number> data = (XYChart.Data<String, Number>) barChart.getData().get(0).getData().get(i);
            data.setYValue(array[i]);
        }
    }
}