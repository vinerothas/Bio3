package main;

import javafx.stage.Stage;

public class Start {

    /*1  56
      2  1059
      3  1276
      4  1130
      5  1451 (1325)
      6  979 */

    static final int test = 6;
    static final boolean bees = false;
    static final boolean printSchedule = true;
    static final int colors = 20;
    static final boolean performance = false;
    static final int performanceTests = 50;

    static final int beeterations = 1000;
    static final int eliteSites = 3;
    static final int totalBestSites = 10;
    static final int bestSites = totalBestSites-eliteSites;
    static final int scoutBees = totalBestSites+10;
    static final int eliteBees = 20;
    static final int bestBees = 10;
    static final int neighbourhood = 5;

    static final int psoIterations = 1000;
    static final double pInfluence = 2;
    static final double gInfluence = 1.5;
    static final double inertia = 1;
    static final int particles = 100;
    static final int neighbours = 3;
    static final double maxV = 1;
    static final double minV = -maxV;


    public void start(Stage stage) {
        Bean bean = new Bean(test);

        if(!performance) {
            if (bees) {
                Solution s = new BA().start(bean);
                new PlotGanttChart().plot(stage, bean, s.schedules, s.fitness);
            } else {
                PSO pso = new PSO();
                Schedule[] s = pso.start(bean);
                new PlotGanttChart().plot(stage, bean, s, pso.bestMakespan);
            }
        }else{
            //time to best solution, score
            if (bees) {
                BA ba = new BA();
                double totalEvaluations = 0;
                double totalScore = 0;
                for (int i = 0; i < Start.performanceTests; i++) {
                    Solution s = ba.start(bean);
                    System.out.println(i+" "+s.fitness+" "+ba.evaluationsAtBest);
                    totalEvaluations += ba.evaluationsAtBest;
                    totalScore += s.fitness;
                }
                totalEvaluations/=(double)Start.performanceTests;
                totalScore/=(double)Start.performanceTests;
                System.out.println(totalEvaluations+" "+totalScore);
            } else {
                PSO pso = new PSO();
                double totalEvaluations = 0;
                double totalScore = 0;
                for (int i = 0; i < Start.performanceTests; i++) {
                    pso.start(bean);
                    System.out.println(i+" "+pso.bestMakespan+" "+pso.evaluationsAtBest);
                    totalEvaluations += pso.evaluationsAtBest;
                    totalScore += pso.bestMakespan;
                }
                totalEvaluations/=(double)Start.performanceTests;
                totalScore/=(double)Start.performanceTests;
                System.out.println(totalEvaluations+" "+totalScore);
            }
        }

        System.out.println();
    }

}
