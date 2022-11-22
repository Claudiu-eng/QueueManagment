package com.example.tema2.model;

public class Task implements Comparable{
    private final int id,arrivalTime,serviceTime;

    public Task(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", arrivalTime=" + arrivalTime +
                ", serviceTime=" + serviceTime +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Task t = (Task) o;
        if (arrivalTime < t.arrivalTime)
            return -1;
        else if (arrivalTime > t.arrivalTime) return 1;
        else {
            if(serviceTime < t.serviceTime)
                return -1;
            else if(serviceTime > t.serviceTime)
                return 1;
            else return 0;
        }
    }
}
