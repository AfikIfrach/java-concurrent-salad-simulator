package assig3_3;

public class SlicerThread extends Thread {

    private SlicerMachine slicerMachine;

    public SlicerThread(SlicerMachine slicerMachine){
        this.slicerMachine=slicerMachine;
    }

    @Override
    public void run(){
        while (!Thread.currentThread().isInterrupted()){
            slicerMachine.sliceVegetables();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e){
                interrupt();
                break;
            }
        }
    }

}
