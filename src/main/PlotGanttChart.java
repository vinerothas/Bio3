package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Iterator;


public class PlotGanttChart {


    public void plot(Stage stage, Bean bean, Schedule[] schedules, int fitness) {

        stage.setTitle("Gantt Chart");

        String[] machines = new String[bean.machines];
        for (int i = 0; i < machines.length; i++) {
            machines[i] = "M"+i;
        }


        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        final GanttChart<Number,String> chart = new GanttChart<Number,String>(xAxis,yAxis);
        xAxis.setLabel("Time");
        xAxis.setMinorTickCount(10);

        yAxis.setLabel("");
        yAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(machines)));

        if(Start.bees)chart.setTitle("Bees");
        else chart.setTitle("PSO");
        chart.setLegendVisible(false);
        chart.setBlockHeight( 40);

        for (int i = schedules.length-1; i > -1; i--) {
            String machine = machines[i];
            XYChart.Series series = new XYChart.Series();

            Iterator<ScheduledActivity> it = schedules[i].schedule.iterator();
            if(Start.printSchedule)System.out.println("\nMachine "+i);
            while(it.hasNext()){
                ScheduledActivity sa = it.next();
                if(Start.printSchedule)System.out.println(sa);
                series.getData().add(new XYChart.Data(sa.startTime, machine, new ExtraData(sa.duration, "status-color"+(sa.job%Start.colors),sa.job,sa.activity)));
            }
            chart.getData().addAll(series);
        }

        if(fitness!=-1)System.out.println("\n Best makespan test "+Start.test + ": "+ fitness);

        chart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());

        Scene scene  = new Scene(chart,620,350);
        stage.setScene(scene);
        stage.show();
    }

}
