package main;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Reader {

    public void readToBean(int test, Bean bean) {
        String filename = "resources/"+test+".txt";

        File file = new File(getClass().getResource(filename).getFile());

        try (Scanner scanner = new Scanner(file)) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+",-1);

            int index = 0;
            if(parts[0].equals("")) index = 1;

            bean.jobs = Integer.parseInt(parts[index++]);
            bean.machines = Integer.parseInt(parts[index]);

            bean.machineOrder = new int[bean.jobs][];
            bean.durationOrder = new int[bean.jobs][];

            for (int i = 0; i < bean.jobs; i++) {
                line = scanner.nextLine();
                parts = line.split("\\s+",-1);
                index = 0;
                if(parts[0].equals("")) index = 1;
                bean.machineOrder[i] = new int[parts.length/2];
                bean.durationOrder[i] = new int[parts.length/2];
                for (int j = 0; j < parts.length/2; j++) {
                    bean.machineOrder[i][j] = Integer.parseInt(parts[index++]);
                    bean.durationOrder[i][j] = Integer.parseInt(parts[index++]);
                }
            }

            bean.activities = bean.machineOrder[0].length;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

