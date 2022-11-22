package com.example.tema2.viewController;

import com.example.tema2.model.Server;
import com.example.tema2.model.Task;

import java.util.ArrayList;

public class ShorthestQueue implements Strategy {
    @Override
    public int addTask(ArrayList<Server> servers, Task t, int maxTasks) throws InterruptedException {
        if (servers.isEmpty())
            return -1;
        Server min = null;
        boolean q = true;
        int p = -1;
        int l = -1;
        for (Server a : servers) {
            p++;
            if (q && a.getTasks().size() < maxTasks) {
                min = a;
                l = p;
                q = false;
            } else {
                if (!q && min.getTasks().size() > a.getTasks().size() && a.getTasks().size() < maxTasks) {
                    min = a;
                    l = p;
                }
            }
        }

        if (min != null) {
            min.addTask(t);
            return l;
        } else {
            return -1;
        }
    }

}
