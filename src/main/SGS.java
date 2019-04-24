package main;

import java.util.ArrayList;
import java.util.Iterator;

public class SGS {

    static Schedule[] sgs(Solution s, Bean bean) {

        //calculate eligible activities
        // ea[i] is an index into machineOrder[i], -1 if job i is fully scheduled
        int[] ea = new int[bean.jobs];

        int[][] startTimes = new int[bean.jobs][bean.activities];
        int[][] finishTimes = new int[bean.jobs][bean.activities];
        int makespan = 0;

        Schedule[] schedules = new Schedule[bean.machines];
        for (int i = 0; i < schedules.length; i++) {
            schedules[i] = new Schedule();
        }

        while (true) {
            double lowestR = Double.MAX_VALUE;
            int job = -1;
            for (int i = 0; i < ea.length; i++) {
                if (ea[i] != -1 && s.r[i][ea[i]] < lowestR) {
                    lowestR = s.r[i][ea[i]];
                    job = i;
                }
            }
            if (job == -1) break;
            int activity = ea[job];
            int machine = bean.machineOrder[job][activity];

            ArrayList<ScheduledActivity> schedule = schedules[machine].schedule;

            int startTime = 0;
            int duration = bean.durationOrder[job][activity];
            int precedenceFinishTime = 0;
            if (activity != 0) precedenceFinishTime = finishTimes[job][activity - 1];
            int insertIndex = 0;
            boolean added = false;

            if (!schedule.isEmpty()) {

                Iterator<ScheduledActivity> it = schedule.iterator();
                ScheduledActivity saNext = it.next();
                if (saNext.startTime >= duration) {
                    if (precedenceFinishTime == 0) {
                        //startTime = 0;
                        added = true;
                    } else if (saNext.startTime - precedenceFinishTime >= duration) {
                        startTime = precedenceFinishTime;
                        added = true;
                    }
                }

                while (it.hasNext() && !added) {
                    ScheduledActivity saPrev = saNext;
                    saNext = it.next();
                    insertIndex++;

                    if (saNext.startTime-saPrev.finishTime >= duration) {
                        if (precedenceFinishTime <= saPrev.finishTime) {
                            startTime = saPrev.finishTime;
                            added = true;
                        } else if (saNext.startTime - precedenceFinishTime >= duration) {
                            startTime = precedenceFinishTime;
                            added = true;
                        }
                    }
                }

                if(!added){
                    insertIndex++;
                    if (precedenceFinishTime <= saNext.finishTime) {
                        startTime = saNext.finishTime;
                    } else {
                        startTime = precedenceFinishTime;
                    }
                }
            }else{
                startTime = precedenceFinishTime;
            }

            schedule.add(insertIndex, new ScheduledActivity(job, activity, duration, startTime));
            startTimes[job][activity] = startTime;
            finishTimes[job][activity] = startTime + bean.durationOrder[job][activity];
            makespan = Integer.max(finishTimes[job][activity],makespan);
            ea[job]++;
            if (ea[job] == bean.machineOrder[job].length) ea[job] = -1;

        }

        s.fitness = makespan;
        s.schedules = schedules;

        return schedules;
    }

    static Schedule[] sgs(Particle p, Bean bean) {

        //calculate eligible activities
        // ea[i] is an index into machineOrder[i], -1 if job i is fully scheduled
        int[] ea = new int[bean.jobs];

        int[][] startTimes = new int[bean.jobs][bean.activities];
        int[][] finishTimes = new int[bean.jobs][bean.activities];
        int makespan = 0;

        Schedule[] schedules = new Schedule[bean.machines];
        for (int i = 0; i < schedules.length; i++) {
            schedules[i] = new Schedule();
        }

        while (true) {
            double lowestR = Double.MAX_VALUE;
            int job = -1;
            for (int i = 0; i < ea.length; i++) {
                if (ea[i] != -1 && p.r[i][ea[i]] < lowestR) {
                    lowestR = p.r[i][ea[i]];
                    job = i;
                }
            }
            if (job == -1) break;
            int activity = ea[job];
            int machine = bean.machineOrder[job][activity];

            ArrayList<ScheduledActivity> schedule = schedules[machine].schedule;

            int startTime = 0;
            int duration = bean.durationOrder[job][activity];
            int precedenceFinishTime = 0;
            if (activity != 0) precedenceFinishTime = finishTimes[job][activity - 1];
            int insertIndex = 0;
            boolean added = false;

            if (!schedule.isEmpty()) {

                Iterator<ScheduledActivity> it = schedule.iterator();
                ScheduledActivity saNext = it.next();
                if (saNext.startTime >= duration) {
                    if (precedenceFinishTime == 0) {
                        //startTime = 0;
                        added = true;
                    } else if (saNext.startTime - precedenceFinishTime >= duration) {
                        startTime = precedenceFinishTime;
                        added = true;
                    }
                }

                while (it.hasNext() && !added) {
                    ScheduledActivity saPrev = saNext;
                    saNext = it.next();
                    insertIndex++;

                    if (saNext.startTime-saPrev.finishTime >= duration) {
                        if (precedenceFinishTime <= saPrev.finishTime) {
                            startTime = saPrev.finishTime;
                            added = true;
                        } else if (saNext.startTime - precedenceFinishTime >= duration) {
                            startTime = precedenceFinishTime;
                            added = true;
                        }
                    }
                }

                if(!added){
                    insertIndex++;
                    if (precedenceFinishTime <= saNext.finishTime) {
                        startTime = saNext.finishTime;
                    } else {
                        startTime = precedenceFinishTime;
                    }
                }
            }else{
                startTime = precedenceFinishTime;
            }

            schedule.add(insertIndex, new ScheduledActivity(job, activity, duration, startTime));
            startTimes[job][activity] = startTime;
            finishTimes[job][activity] = startTime + bean.durationOrder[job][activity];
            makespan = Integer.max(finishTimes[job][activity],makespan);
            ea[job]++;
            if (ea[job] == bean.machineOrder[job].length) ea[job] = -1;

        }

        p.fitness = makespan;
        p.schedules = schedules;

        return schedules;
    }
}
