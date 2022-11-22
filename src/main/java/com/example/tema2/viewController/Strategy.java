package com.example.tema2.viewController;

import com.example.tema2.model.Server;
import com.example.tema2.model.Task;

import java.util.ArrayList;

public interface Strategy {
    public int addTask(ArrayList<Server> servers, Task t,int maxTasks) throws InterruptedException;
}
