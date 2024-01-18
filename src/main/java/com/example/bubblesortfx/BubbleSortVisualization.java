package com.example.bubblesortfx;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;

import java.io.Console;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

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
        // Create the dialog box to get the number of numbers to sort
        TextInputDialog dialog = new TextInputDialog();
        dialog.setGraphic(null);
        dialog.setTitle("Number of Numbers");
        dialog.setHeaderText("Enter the number of numbers to sort:");
        Optional<String> result = dialog.showAndWait();
        TextInputDialog dialog2 = new TextInputDialog();
        dialog2.setTitle("Animationspeed");
        dialog.setGraphic(null);
        dialog2.setHeaderText("Enter the speed of the animation for each step[ms]:");
        Optional<String> speed = dialog2.showAndWait();
        if (result.isPresent()) {
            int numNumbers = Integer.parseInt(result.get());
            // Initialize the array with random values
            array = new int[numNumbers];
            for (int i = 0; i < array.length; i++) {
                //array[i] = (int) (Math.random() * 1000);
                array[i] = i + 1;
            }
            //shuffle array
            for (int i = 0; i < array.length; i++) {
                int randomIndex = (int) (Math.random() * array.length);
                int temp = array[i];
                array[i] = array[randomIndex];
                array[randomIndex] = temp;
            }

            // Create the bar chart
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            barChart = new BarChart<>(xAxis, yAxis);
            barChart.setTitle("Bubble Sort Animation");
            barChart.setStyle("-fx-text: white;");
            barChart.setBarGap(0);
            barChart.setAnimated(false);
            barChart.setHorizontalGridLinesVisible(false);
            barChart.setVerticalGridLinesVisible(false);
            barChart.setLegendVisible(false);
            xAxis.setVisible(false);
            yAxis.setVisible(false);
            xAxis.setTickLabelsVisible(false);
            yAxis.setTickLabelsVisible(false);
            barChart.setStyle("-fx-background-color: black");


            // Create the series for the bar chart
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            for (int i = 0; i < array.length; i++) {
                series.getData().add(new XYChart.Data<>(String.valueOf(i), array[i]));
                //barChart.getData().get(i).getData().get(i).getNode().setStyle("-fx-bar-fill: white;");
                //barChart.get(i).getNode().setStyle("-fx-bar-fill: white;");
                System.out.println((i + ": " + array[i]));
            }
            barChart.getData().add(series);
            for (Node n : barChart.lookupAll(".default-color0.chart-bar")) {
                n.setStyle("-fx-bar-fill: black;");
                //change color of the diagram to black
            }

            // Create the scene and add the chart to it
            Scene scene = new Scene(barChart, 800, 600);
            //add the css file
            URL cssUrl = getClass().getResource("style.css");
            //System.out.println("CSS URL: " + cssUrl);
            scene.getStylesheets().add(String.valueOf(Objects.requireNonNull(cssUrl)));
            stage.setScene(scene);
            stage.show();

            // Start the bubble sort algorithm
            currentIndex = 0;
            endIndex = array.length - 1;
            sort(speed);
        }
    }


    private void sort(Optional<String> s) {
        long speed = Long.parseLong(s.get());
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(speed), event -> {
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