package com.example.tema2.viewController;

import com.example.tema2.model.SelectionPolicy;
import com.example.tema2.model.Server;
import com.example.tema2.model.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class SimulationManager implements Runnable {
    private int timeLimit, max, prec;
    private int maxProcessingTime, minProcessingTime;
    private int minArrivalTime, maxArrivalTime;
    private int noOfClients, noOfServers, maxTasksPerServer;
    private SelectionPolicy selectionPolicy;
    private Scheduler scheduler;
    private ArrayList<Task> generatedTasks;
    private FirstPage firstPage;
    private String msg = "";
    private static Logger logger = LogManager.getLogger(SimulationManager.class);

    public SimulationManager(FirstPage firstPage) {
        this.firstPage = firstPage;
        noOfClients = firstPage.getNoOfClients();
        noOfServers = firstPage.getNoOfServers();
        maxTasksPerServer = firstPage.getMaxTasksPerServer();
        maxProcessingTime = firstPage.maxProcessingTime();
        minProcessingTime = firstPage.minProcessingTime();
        minArrivalTime = firstPage.getMinArrivalTime();
        maxArrivalTime = firstPage.getMaxArrivalTime();
        timeLimit = firstPage.getTimeLimit();
        selectionPolicy = firstPage.getStrategyApplyed();
        scheduler = new Scheduler(noOfServers, maxTasksPerServer);
        scheduler.changeStrategy(selectionPolicy);
        generateNRandomTasks();
    }

    private void generateNRandomTasks() {
        Random rand = new Random();
        generatedTasks = new ArrayList<>();
        for (int i = 0; i < noOfClients; i++) {
            int ser = Math.abs(rand.nextInt()) % (maxProcessingTime - minProcessingTime) + minProcessingTime;
            int arr = Math.abs(rand.nextInt()) % (maxArrivalTime - minArrivalTime) + minArrivalTime;
            Task t = new Task(i, arr, ser);
            generatedTasks.add(t);
        }

        Collections.sort(generatedTasks);
    }

    private void print(int c) {
        String t = "timp = " + c;
        t = t + "\n";
        t = t + " pe hol mai sunt " + generatedTasks.size() + " clienti in asteptare" + "\n";
        for (Task a : generatedTasks) {
            t = t + a.toString() + "\n";
        }
        for (int i = 0; i < noOfServers; ++i) {
            t = t + "chioscul " + (i + 1) + " =\n";
            for (Task a : scheduler.getServers().get(i).getTasks()) {
                t = t + a.toString() + "\n";
            }

        }
        t = t + "\n############\n";
        msg = msg + t;

    }

    private float waitingTotalTime() {
        float p = 0.0f;
        for (Server a : scheduler.getServers())
            p = p + a.getWaitingPeriod().get();
        return p;
    }

    private void updateValues() {
        String s = (String) firstPage.queueComboBox.getValue();
        if (s == null)
            return;
        int t = Integer.parseInt(s);
        if (scheduler.getServers() != null)
            if (scheduler.getServers().size() > t && t >= 0)
                if (scheduler.getServers().get(t).getTasks() != null) {
                    firstPage.noClientsInQueue.setText(String.valueOf(scheduler.getServers().get(t).getTasks().size()));
                    firstPage.waitingPeriodQueue.setText(String.valueOf(scheduler.getServers().get(t).getWaitingPeriod()));
                }
    }

    @Override
    public void run() {
        int currentTime = 0, m = 0, peakHour = 0;
        float suma = 0.0f, wait = 0.0f;
        Double p;
        while (currentTime < timeLimit) {
            try {
                updateValues();
                 p = Double.valueOf(String.valueOf(currentTime));
                    firstPage.progresBar.setProgress(p / timeLimit);
                for (int i = 0; i < generatedTasks.size(); ++i) {
                    if (generatedTasks.get(i).getArrivalTime() == currentTime) {

                        m = scheduler.dispatchTask(generatedTasks.get(i));
                        if (m != -1) {
                            suma += generatedTasks.get(i).getServiceTime();
                            generatedTasks.remove(i);
                            p = Double.valueOf(String.valueOf(currentTime));
                            firstPage.progresBar.setProgress(p / timeLimit);
                            if (i >= 0)
                                i--;
                        } else {
                            break;
                        }
                    } else break;
                }
                if (generatedTasks.size() > 0) {
                    if (generatedTasks.get(0).getArrivalTime() != currentTime) {
                        print(currentTime);
                        currentTime++;

                    } else print(currentTime);
                } else {
                    currentTime++;
                    print(currentTime - 1);
                }
                Thread.sleep(1000L);
                if (max < prec - generatedTasks.size()) {
                    max = prec - generatedTasks.size();
                    peakHour = currentTime;
                }
                wait = wait + waitingTotalTime();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        p = Double.valueOf(String.valueOf(currentTime));
        firstPage.progresBar.setProgress(p / timeLimit);
        msg = msg + "\n" + "average service time =" + suma / noOfClients;
        msg = msg + "\n" + "Peak Hour =" + peakHour;
        msg = msg + "\n" + "average waiting time = " + wait / currentTime;
        logger.info(msg);
        firstPage.finishLabel.setVisible(true);
    }
}
