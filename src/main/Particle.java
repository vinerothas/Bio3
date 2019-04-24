package main;

import java.util.Random;

public class Particle {

    double[][] r;
    int fitness = Integer.MAX_VALUE; //makespan - minimize
    Schedule[] schedules;
    double[][] bestR; //pBest
    double[][] v;
    int[] neighbours;

    Particle(Bean bean){
        Random random = new Random(System.nanoTime());

        neighbours = new int[Start.neighbours];
        for (int i = 0; i < neighbours.length; i++) {
            neighbours[i] = random.nextInt(Start.particles);
        }

        v = new double[bean.jobs][bean.activities];
        r = new double[bean.jobs][bean.activities];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                //r[i][j] = i*bean.jobs+j;
                r[i][j] = random.nextDouble();
            }
        }

        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                int index = random.nextInt(r.length);
                int index2 = random.nextInt(r[i].length);
                double a = r[i][j];
                r[i][j] = r[index][index2];
                r[index][index2] = a;
            }
        }
        SGS.sgs(this,bean);
        setBestR();
    }

    void update(Bean bean, Particle[] particles){

        double[][] gBest = null;
        int bestNeighbour = Integer.MAX_VALUE;
        for (int i = 0; i < neighbours.length; i++) {
            if(particles[neighbours[i]].fitness < bestNeighbour){
                bestNeighbour = particles[neighbours[i]].fitness;
                gBest = particles[neighbours[i]].bestR;
            }
        }


        Random random = new Random(System.nanoTime());
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                v[i][j] =  v[i][j]*Start.inertia
                        +Start.pInfluence*random.nextDouble()*(bestR[i][j]-r[i][j])
                        +Start.gInfluence*random.nextDouble()*(gBest[i][j]-r[i][j]);
                if(v[i][j]>Start.maxV)v[i][j]=Start.maxV;
                else if(v[i][j]<Start.minV)v[i][j]=Start.minV;
                r[i][j] += v[i][j];
            }
        }
        int bestFitness = fitness;
        SGS.sgs(this,bean);
        if(fitness<bestFitness)setBestR();
    }

    private void setBestR(){
        bestR = new double[r.length][r[0].length];
        for (int i = 0; i < bestR.length; i++) {
            for (int j = 0; j < bestR[i].length; j++) {
                bestR[i][j] = r[i][j];
            }
        }
    }
}
