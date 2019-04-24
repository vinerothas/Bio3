package main;

public class Bean {

    int jobs;
    int machines;
    int activities;
    int[][] machineOrder;
    int[][] durationOrder;

    Bean(int test){
        new Reader().readToBean(test,this);
    }

}
