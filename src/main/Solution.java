package main;

import java.util.Random;

public class Solution {

    int[][] r;
    int fitness = Integer.MAX_VALUE; //makespan - minimize
    Schedule[] schedules;
    //Solution parent;
    int type = -1; //1-scout 2-localsearch 3-best

    Solution(Bean bean){
        r = new int[bean.jobs][bean.activities];
        Random random = new Random(System.nanoTime());
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                r[i][j] = i*bean.jobs+j;
            }
        }

        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                int index = random.nextInt(r.length);
                int index2 = random.nextInt(r[i].length);
                int a = r[i][j];
                r[i][j] = r[index][index2];
                r[index][index2] = a;
            }
        }
        type = 1;
        SGS.sgs(this,bean);
    }

    Solution(Solution s, Bean bean){
        r = new int[s.r.length][s.r[0].length];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[i].length; j++) {
                r[i][j] = s.r[i][j];
            }
        }
        //parent = s;
    }

    Solution localSearch(Bean bean){
        Solution s = new Solution(this,bean);
        Random random = new Random(System.nanoTime());
        int index = random.nextInt(r.length);
        int index2 = random.nextInt(r[0].length);
        int index3 = random.nextInt(r.length);
        int index4 = random.nextInt(r[0].length);
        int a = s.r[index3][index4];
        s.r[index3][index4] = s.r[index][index2];
        s.r[index][index2] = a;
        return s;
    }

}
