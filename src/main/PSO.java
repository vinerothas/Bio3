package main;

public class PSO {

    int bestMakespan = Integer.MAX_VALUE;
    int evaluationsAtBest = 0;

    Schedule[] start(Bean bean){
        Schedule[] bestSchedule = null;
        int evaluations = 0;
        bestMakespan = Integer.MAX_VALUE;

        Particle[] particles = new Particle[Start.particles];
        for (int i = 0; i < particles.length; i++) {
            particles[i] = new Particle(bean);
            evaluations++;
            if(particles[i].fitness<bestMakespan){
                bestMakespan = particles[i].fitness;
                bestSchedule = particles[i].schedules;
                evaluationsAtBest =evaluations;
            }
        }

        for (int i = 0; i < Start.psoIterations; i++) {
            for (int j = 0; j < particles.length; j++) {
                evaluations++;
                particles[j].update(bean,particles);
                if(particles[j].fitness<bestMakespan){
                    evaluationsAtBest = evaluations;
                    bestMakespan = particles[j].fitness;
                    bestSchedule = particles[j].schedules;
                    if(!Start.performance)System.out.println("New best at iteration "+i+", particle "+j+": "+bestMakespan);
                }
            }
        }
        //System.out.println("\n Best makespan test "+Start.test + ": "+ bestMakespan);
        return bestSchedule;
    }
}
