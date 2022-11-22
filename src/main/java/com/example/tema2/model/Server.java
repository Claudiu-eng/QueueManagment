package com.example.tema2.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;


    public Server() {
        tasks = new LinkedBlockingQueue<>();
        waitingPeriod = new AtomicInteger(0);
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public void setTasks(BlockingQueue<Task> tasks) {
        this.tasks = tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public synchronized void addTask(Task newTask) throws InterruptedException {
        tasks.put(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());
    }

    @Override
    public void run() {

        while (true) {
            try {
                if (tasks.size() > 0) {
                    Thread.sleep(1000L * tasks.element().getServiceTime());
                    Task t = tasks.take();
                    //System.out.println(Thread.currentThread().getId());
                    waitingPeriod.addAndGet(t.getServiceTime());
                    //System.out.println(t.toString());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
