package assig3_3;

public class SlicerMachine {

    private int numOfCucumbers = 0;
    private int numOfTomatoes = 0;
    private int numOfOnions = 0;
    private int numOfPreparedSalads = 0;
    private int saladsToPrepare;

    final int cucumbersNeededForOneSalad = 5;
    final int tomatoesNeededForOneSalad = 3;
    final int onionsNeededForOneSalad = 1;

    public SlicerMachine(int saladsToPrepare){
        if (saladsToPrepare < 0) throw new IllegalArgumentException("Number of salads cannot be negative");
        this.saladsToPrepare=saladsToPrepare;
    }

    // add one cucumber into the slicer chamber
    public synchronized void addOneCucumber() {
        while (numOfCucumbers >= cucumbersNeededForOneSalad) {
            try{
                wait();
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println("adding one cucumber to the machine");
        numOfCucumbers++;
        notifyAll();
    }

    // add one onion into the slicer chamber
    public synchronized void addOneOnion() {
        while (numOfOnions >= onionsNeededForOneSalad) {
            try {
                wait();
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println("adding one onion to the machine");
        numOfOnions++;
        notifyAll();
    }

    // add one tomato into the slicer chamber
    public synchronized void addOneTomato() {
        while (numOfTomatoes >= tomatoesNeededForOneSalad) {
            try {
                wait();
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println("adding one tomato to the machine");
        numOfTomatoes++;
        notifyAll();
    }

    // if there are enough vegetables in the slicer
    // chamber, make another salad
    public synchronized void sliceVegetables() {
        if (numOfPreparedSalads>=this.saladsToPrepare){
            notifyAll();
            return;
        }
        while ((numOfOnions < onionsNeededForOneSalad) || (numOfCucumbers < cucumbersNeededForOneSalad) || (numOfTomatoes < tomatoesNeededForOneSalad)) {
            try {
                wait();
                if (numOfPreparedSalads>=this.saladsToPrepare){
                    notifyAll();
                    return;
                }
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                return;
            }
        }
        makeNewSalad();
        notifyAll();
    }

    private void makeNewSalad() {
        System.out.println("== preparing one more salad ==");
        numOfPreparedSalads++;
        // update stock
        numOfTomatoes = numOfTomatoes - tomatoesNeededForOneSalad;
        numOfCucumbers = numOfCucumbers - cucumbersNeededForOneSalad;
        numOfOnions = numOfOnions - onionsNeededForOneSalad;
    }

    /** Wait without polling until all requested salads have been prepared. */
    public synchronized void awaitCompletion() throws InterruptedException {
        while (numOfPreparedSalads < saladsToPrepare) {
            wait();
        }
    }

    public synchronized int getNumOfPreparedSalads() {
        return numOfPreparedSalads;
    }

}
