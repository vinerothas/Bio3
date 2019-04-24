package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class BA {

    int evaluations;
    int evaluationsAtBest;

    Solution start(Bean bean){

        int totalFromScouts = 0;
        int totalFromBestSearch = 0;
        int totalFromBest = 0;
        int totalFromElite = 0;
        int totalFromEliteSearch = 0;
        evaluations = 0;

        Random random = new Random(System.nanoTime());
        Solution[] scouts = new Solution[Start.scoutBees];

        for (int i = 0; i < scouts.length; i++) {
            scouts[i] = new Solution(bean);
            evaluations++;
        }

        Comparator<Solution> byFitness = (Solution o1, Solution o2) -> (int) (o1.fitness - o2.fitness);
        ArrayList<Solution> list = new ArrayList<>(Arrays.asList(scouts));
        list.sort(byFitness);
        scouts = list.toArray(new Solution[0]);

        Solution best = scouts[0];
        Solution[] bestSites = new Solution[Start.totalBestSites];
        for (int i = 0; i < bestSites.length; i++) {
            bestSites[i] = scouts[i];
        }
        if(!Start.performance)System.out.println("Best at start: "+best.fitness);

        for (int g = 0; g < Start.beeterations; g++) {
            ArrayList<Solution> newSites = new ArrayList<>();
            for (int i = 0; i < Start.eliteSites; i++) {
                for (int j = 0; j < Start.eliteBees; j++) {
                    evaluations++;
                    Solution s = bestSites[i];
                    bestSites[i].type = 4;
                    int steps = random.nextInt(Start.neighbourhood)+1;
                    for (int k = 0; k < steps; k++) {
                        s = s.localSearch(bean);
                    }
                    SGS.sgs(s,bean);
                    s.type = 5;
                    if(s.fitness<bestSites[0].fitness)newSites.add(s);
                }
            }

            for (int i = Start.eliteSites; i < Start.totalBestSites; i++) {
                for (int j = 0; j < Start.bestBees; j++) {
                    evaluations++;
                    Solution s = bestSites[i];
                    bestSites[i].type = 3;
                    int steps = random.nextInt(Start.neighbourhood)+1;
                    for (int k = 0; k < steps; k++) {
                        s = s.localSearch(bean);
                    }
                    SGS.sgs(s,bean);
                    s.type = 2;
                    if(s.fitness<bestSites[0].fitness)newSites.add(s);
                }
            }

            ArrayList<Solution> newScouts = new ArrayList<Solution>();
            for (int i = 0; i < Start.scoutBees-Start.totalBestSites-newSites.size() ; i++) {
                newScouts.add(new Solution(bean));
            }

            list = new ArrayList<>(Arrays.asList(bestSites));
            list.addAll(newSites);
            list.addAll(newScouts);
            list.sort(byFitness);
            int fromScouts = 0;
            int fromBestSearch = 0;
            int fromBest = 0;
            int fromElite = 0;
            int fromEliteSearch = 0;
            for (int i = 0; i < bestSites.length; i++) {
                bestSites[i] = list.get(i);
                if(bestSites[i].type == 1)fromScouts++;
                else if(bestSites[i].type == 2)fromBestSearch++;
                else if(bestSites[i].type == 3)fromBest++;
                else if(bestSites[i].type == 4)fromElite++;
                else if(bestSites[i].type == 5)fromEliteSearch++;
            }
            totalFromScouts += fromScouts;
            totalFromBestSearch += fromBestSearch;
            totalFromBest += fromBest;
            totalFromElite += fromElite;
            totalFromEliteSearch += fromEliteSearch;
            if(best.fitness>bestSites[0].fitness) {
                best = bestSites[0];
                evaluationsAtBest = evaluations;
                if(!Start.performance)System.out.println("Best at iteration "+g+": "+best.fitness+"\t\tfromScouts: "+fromScouts+"\t\tfromBestSearch: "+fromBestSearch+"\t\tfromEliteSearch: "+fromEliteSearch+"\t\tfromBest: "+fromBest+"\t\tfromElite: "+fromElite);
            }
            //if(g%20==0)System.out.println("Best at iteration "+g+": "+best.fitness+"\t\tnew sites: "+newSites.size());
        }

        if(!Start.performance)System.out.println("totalFromScouts: "+totalFromScouts+"\t\ttotalFromBestSearch: "+totalFromBestSearch+"\t\ttotalFromEliteSearch: "+totalFromEliteSearch+"\t\ttotalFromBest: "+totalFromBest+"\t\ttotalFromElite: "+totalFromElite);

        return best;
    }


}
