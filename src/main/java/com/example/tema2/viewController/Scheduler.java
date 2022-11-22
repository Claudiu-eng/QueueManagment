package com.example.tema2.viewController;

import com.example.tema2.model.SelectionPolicy;
import com.example.tema2.model.Server;
import com.example.tema2.model.Task;

import java.net.PortUnreachableException;
import java.util.ArrayList;

public class Scheduler {
    private ArrayList<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers,int maxTasksPerServer){
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        servers=new ArrayList<Server>();
        for(int i=0;i<this.maxNoServers;i++)
        {
            Server t=new Server();
            servers.add(t);
            Thread a=new Thread(t);
            a.start();
        }
    }

    public void changeStrategy(SelectionPolicy selectionPolicy){
        if(selectionPolicy==SelectionPolicy.SHORTEST_TIME)
            strategy=new ShorthestTime();
        if(selectionPolicy==SelectionPolicy.SHORTHEST_QUEUE)
            strategy=new ShorthestQueue();
    }

    public synchronized int dispatchTask(Task t) throws InterruptedException {
            return strategy.addTask(servers,t,maxTasksPerServer);
    }

    public ArrayList<Server> getServers() {
        return servers;
    }

    public void setServers(ArrayList<Server> servers) {
        this.servers = servers;
    }

    public int getMaxNoServers() {
        return maxNoServers;
    }

    public void setMaxNoServers(int maxNoServers) {
        this.maxNoServers = maxNoServers;
    }

    public int getMaxTasksPerServer() {
        return maxTasksPerServer;
    }

    public void setMaxTasksPerServer(int maxTasksPerServer) {
        this.maxTasksPerServer = maxTasksPerServer;
    }
}
