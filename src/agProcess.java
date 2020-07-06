class agProcess extends Process {

    private int AgFactor ;
    public agProcess(String name, int arrivalTime, int burstTime, int priorityNumber, int quantum) {
        super(name, arrivalTime, burstTime, priorityNumber, quantum);
        AgFactor = arrivalTime + burstTime + priorityNumber ;
    }

    public int getAgFactor() {
        return AgFactor;
    }
}
