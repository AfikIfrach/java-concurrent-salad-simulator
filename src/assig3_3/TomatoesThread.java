package assig3_3;

public class TomatoesThread extends Thread {

    private SlicerMachine slicerMachine;

    public TomatoesThread(SlicerMachine slicerMachine){
        this.slicerMachine=slicerMachine;
    }

    @Override
    public void run(){
        while (!Thread.currentThread().isInterrupted()){
            slicerMachine.addOneTomato();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e){
                interrupt();
                break;
            }
        }
    }


}
