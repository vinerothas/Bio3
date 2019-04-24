package main;

import java.util.Arrays;

public class ScheduledActivity {

    int job;
    int activity;
    int duration;
    int startTime;
    int finishTime;

    ScheduledActivity(int job, int activity, int duration, int startTime){
        this.job = job;
        this.activity = activity;
        this.duration = duration;
        this.startTime = startTime;
        this.finishTime = startTime+duration;
    }

    public String toString() {
        String s = job+"\t"+activity+"\t\t Duration:"+duration+"\tStart time:"+startTime+"\tFinish time:"+finishTime;
        return s;
    }
}
