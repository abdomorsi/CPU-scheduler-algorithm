import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class AGScheduling {
    private static int currentTime = 0;
    private static int timeToAddprocess = 10000000;
    private static boolean recieve = false;
    private static Queue<agProcess> readyQ = new LinkedList<>();
    private static Queue<agProcess> arrivedQ = new LinkedList<>();
    private static ArrayList<agProcess> dieList = new ArrayList<>();
    private static ArrayList<agProcess> processes = new ArrayList<>() ;
    private static ArrayList<Integer> arrivalTimes = new ArrayList<>() ;
    /*
    public ArrayList<agProcess> readProcesses(int numberOfProcesses) {
        ArrayList<agProcess> mainProcesses = new ArrayList<>();
        for (int i = 0; i < numberOfProcesses; i++) {
            int AT

        }
        return mainProcesses;
    }
    */
    private static int meanQuantum (agProcess runningProcess , int diff ) {
        int mQ = 0 , c=0;
        if (diff == 0) {
            for (int i = 0; i < processes.size(); i++) {
                if (readyQ.contains(processes.get(i)) && processes.get(i) != runningProcess) {
                    mQ += processes.get(i).getQuantum();
                    c++;
                }
            }
            mQ = (int) (mQ/c * 0.1);
        } else {
            mQ = diff;
        }
        return mQ+runningProcess.getQuantum();
    }

    private static Queue<agProcess> addArrivedPreocess0(Queue<agProcess> arrivedProcesses,ArrayList<agProcess> processes ,int currentTime) {
        //Queue<agProcess> arrivedProcesses = new LinkedList<>();
        for (int i = 0 ; i<processes.size();i++) {
            agProcess p = processes.get(i);
            //System.out.println(p.getArrivalTime() + "|||||||||||||||||||||" +currentTime);
            if ( p.getArrivalTime() == currentTime && !dieList.contains(p) ) {
                arrivedProcesses.add(p);
                //processes.remove(p);
                break;
            }
        }
        getTimeToAddNextProcess(processes);
        return  arrivedProcesses;
    }

    private static void addArrivedPreocess() {
        //Queue<agProcess> arrivedProcesses = new LinkedList<>();
        System.out.println(currentTime+" --------");
        for (int i = 0 ; i<processes.size();i++) {
            agProcess p = processes.get(i);
            if ( p.getArrivalTime() == currentTime && !dieList.contains(p) ) {
                arrivedQ.add(p);
            }
        }
        //getTimeToAddNextProcess(processes);
    }

    private static void timesToAddP() {
        for (int i = 0; i < processes.size(); i++) {
            arrivalTimes.add(processes.get(i).getArrivalTime());
        }
    }

    private static void getTimeToAddNextProcess(ArrayList<agProcess> processes) {
        timeToAddprocess = processes.get(0).getArrivalTime();
        for (int i = 1; i < processes.size(); i++) {
            if (processes.get(i).getArrivalTime() < timeToAddprocess) {
                timeToAddprocess = processes.get(i).getArrivalTime();
            }
        }
    }

    private static void runProcess(Queue<agProcess> arrivedProcesses , ArrayList<agProcess> processes) {
        recieve = false;
        int time = currentTime;
        int runningTime = 0; ///time which the process is going to run
        double checkTime; /// at which second the process may be stopped to run another
        agProcess runningProcess = arrivedProcesses.poll();
        int stopTime = Math.min(runningProcess.getQuantum(), runningProcess.remainingTime);
        checkTime = Math.ceil(runningProcess.getQuantum() / 2) + currentTime;
        agProcess nextProcess;
        System.out.println("process : " + runningProcess.getName() + " start at :" + time );
        //System.out.println(runningProcess.getBurstTime() + " //  " + runningProcess.getRemainingTime());
        System.out.println(timeToAddprocess + " --- " + stopTime);
        while (runningTime <= stopTime) {
            //System.out.println(time + " t * ... " + timeToAddprocess);
            runningTime++;
            time++;
            if (time == timeToAddprocess) {
                //System.out.println("VV");
                //System.out.println(processes.get(0).getName() + " /*/////////////////////");
               ///////////// arrivedProcesses = addArrivedPreocess(arrivedProcesses, processes,time);
                recieve = true;
                //System.out.println(arrivedProcesses.peek().getName());
                //getTimeToAddNextProcess(processes);
            }
            /// time to check if the process reach %50 of its quantum time or greater than
            if (runningTime >= runningProcess.getQuantum()/2 ) {
                //System.out.println("%50");
                nextProcess = arrivedProcesses.peek();
                if (nextProcess != null) {
                    /// there are another processes reached and ready ro run
                    /// check that a process can run instead the running one
                    //System.out.println(nextProcess.getAgFactor()+ " +_+_+  " + runningProcess.getAgFactor() );
                    if (nextProcess.getAgFactor() < runningProcess.getAgFactor()) {
                        //System.out.println(nextProcess.getAgFactor()+ " +_+_+  " + runningProcess.getAgFactor() );
                        int newQuantom = runningProcess.getQuantum() - runningTime;
                        if (newQuantom == 0) {
                            ////////////newQuantom = meanQuantum(arrivedProcesses);
                        }
                        runningProcess.setQuantum(newQuantom+runningProcess.getQuantum());
                        runningProcess.setRemainingTime(runningProcess.getBurstTime()-(runningTime-currentTime));
                        currentTime = time;
                        if (runningProcess.remainingTime > 0) {
                            System.out.println("process : " + runningProcess.getName() + " stop at* :" + time);
                            arrivedProcesses.add(runningProcess);
                        } else {
                            System.out.println("process : " + runningProcess.getName() + " finish at :" + time );
                        }
                        runProcess(arrivedProcesses, processes);
                    } else {
                        System.out.println(nextProcess.getAgFactor()+ " +_+_+  " + runningProcess.getAgFactor()+ " >>" );
                        runningProcess.setRemainingTime(runningProcess.getBurstTime()-runningProcess.getQuantum());
                       /////////////// runningProcess.setQuantum(meanQuantum(arrivedProcesses)+runningProcess.getQuantum());
                        currentTime = time;
                        if (runningProcess.remainingTime > 0) {
                            arrivedProcesses.add(runningProcess);
                            System.out.println("process : " + runningProcess.getName() + " stop at// :" + time );
                        } else {
                            System.out.println(" finish : "+runningProcess.getName()+"  at "+currentTime );
                        }
                        runProcess(arrivedProcesses,processes);
                    }
                }
            }
            //System.out.println(time);

        }

    }

    private  static  void run() {
        if (arrivedQ.size() > 0) {
            runProcessR(arrivedQ);
        } else {
            runProcessR(readyQ);
        }
    }
    private static void runProcessR( Queue<agProcess> Q) {
        recieve = false;
        int time = currentTime;
        int runningTime = 0; ///time which the process is going to run
        double checkTime; /// at which second the process may be stopped to run another
        agProcess runningProcess = Q.poll();
        int stopTime = Math.min(runningProcess.getQuantum(), runningProcess.remainingTime);
        //checkTime = Math.ceil(runningProcess.getQuantum() / 2) + currentTime;
        agProcess nextProcess;
        System.out.println("process : " + runningProcess.getName() + " start at :" + time );
        //System.out.println(timeToAddprocess + " --- " + stopTime);
        while (runningTime <= stopTime) {
            runningTime++;
            time++;
            System.out.println("A");
            if (arrivalTimes.contains(time)) {
                System.out.println("B");
                currentTime = time;
                addArrivedPreocess();
                recieve = true;
            }
            /// time to check if the process reach %50 of its quantum time or greater than
            if (runningTime >= runningProcess.getQuantum()/2 ) {
                //System.out.println("%50");
                nextProcess = Q.peek();
                if (nextProcess != null) {
                    /// there are another processes reached and ready ro run
                    /// check that a process can run instead the running one
                    //System.out.println(nextProcess.getAgFactor()+ " +_+_+  " + runningProcess.getAgFactor() );
                    if (nextProcess.getAgFactor() < runningProcess.getAgFactor()) {
                        //System.out.println(nextProcess.getAgFactor()+ " +_+_+  " + runningProcess.getAgFactor() );
                        runningProcess.setQuantum(meanQuantum(runningProcess,runningProcess.getBurstTime()-runningTime));
                        runningProcess.setRemainingTime(runningProcess.getRemainingTime()-runningTime);
                        currentTime = time;
                        if (runningProcess.remainingTime > 0) {
                            System.out.println("process : " + runningProcess.getName() + " stop at* :" + time);
                            readyQ.add(runningProcess);
                        } else {
                            dieList.add(runningProcess);
                            System.out.println("process : " + runningProcess.getName() + " finish at :" + time );
                        }
                        run();
                    }

                }
            }
            //System.out.println(time);
        }
        time--;
        runningProcess.setQuantum(meanQuantum(runningProcess,runningProcess.getQuantum()-runningTime));
        runningProcess.setRemainingTime(runningProcess.getRemainingTime()-runningTime);
       // System.out.println(readyQ.size() + " <><><><><");
       // System.out.println(readyQ.peek().getName());
        currentTime = time;
        if (runningProcess.remainingTime > 0) {
            readyQ.add(runningProcess);
            System.out.println("process : " + runningProcess.getName() + " stop at// :" + time);
        } else {
            dieList.add(runningProcess);
        }
        run();
    }

    public static  void runAgSchedujling(ArrayList<agProcess> pp) {
        Queue<agProcess> arrivedProcesses = new LinkedList<>();
        for (int i = 0; i < pp.size(); i++) {
            processes.add(pp.get(i));
        }
        timesToAddP();
        addArrivedPreocess();
        System.out.println(arrivedQ.size());
        runProcessR(arrivedQ);

    }

    public static void main(String[] args) throws IOException {
        ArrayList<agProcess> pp = new ArrayList<agProcess>();
        agProcess p1 = new agProcess("1",0,17,4,4);
        agProcess p2 = new agProcess("2",3,6,9,4);
        agProcess p3 = new agProcess("3",4,10,3,4);
        agProcess p4 = new agProcess("4",29,4,8,4);
        System.out.println(p1.getAgFactor());
        System.out.println(p2.getAgFactor());
        System.out.println(p3.getAgFactor());
        System.out.println(p4.getAgFactor());
        pp.add(p1);
        pp.add(p2);
        pp.add(p3);
        pp.add(p4);
        runAgSchedujling(pp);
    }
}
