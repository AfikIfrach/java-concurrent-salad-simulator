package assig3_3;

public class CucumbersThread extends Thread {

    private SlicerMachine slicerMachine;

    public CucumbersThread(SlicerMachine slicerMachine){
        this.slicerMachine=slicerMachine;
    }

    @Override
    public void run(){
        while (!Thread.currentThread().isInterrupted()){
            slicerMachine.addOneCucumber();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e){
                interrupt();
                break;
            }
        }
    }

}
