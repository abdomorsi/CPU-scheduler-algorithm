import java.awt.*;

public class Process {
    protected String name = "";
    protected int arrivalTime = 0;
    protected int burstTime = 0;
    protected int priorityNumber  = 0;
    protected int quantum = 0;
    protected int remainingTime = 0;
    //protected String color;

    public Process(String name , int arrivalTime , int burstTime , int priorityNumber) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityNumber = priorityNumber;
        this.remainingTime = burstTime;
    }

    public Process(String name , int arrivalTime , int burstTime , int priorityNumber , int quantum) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityNumber = priorityNumber;
        this.quantum = quantum;
        this.remainingTime = burstTime;
    }

    public  String getName() {
        return this.name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriorityNumber() {
        return priorityNumber;
    }

    public int getQuantum() {
        return quantum;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setPriorityNumber(int priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
}
