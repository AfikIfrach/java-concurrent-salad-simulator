package assig3_3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            System.out.println("Please type how many salads to prepare:");
            if (!scan.hasNextInt()) {
                System.err.println("Please enter a non-negative integer.");
                return;
            }
            int count = scan.nextInt();
            if (count < 0) {
                System.err.println("The number of salads must be non-negative.");
                return;
            }
            if (count == 0) {
                System.out.println("Prepared 0 salads. Done.");
                return;
            }

            SlicerMachine machine = new SlicerMachine(count);
            Thread[] workers = {
                new CucumbersThread(machine), new OnionThread(machine),
                new TomatoesThread(machine), new SlicerThread(machine)
            };
            for (Thread worker : workers) worker.start();

            try {
                machine.awaitCompletion();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Preparation interrupted.");
            } finally {
                for (Thread worker : workers) worker.interrupt();
                for (Thread worker : workers) {
                    boolean joined = false;
                    while (!joined) {
                        try { worker.join(); joined = true; }
                        catch (InterruptedException e) { Thread.interrupted(); }
                    }
                }
            }
            System.out.println("Prepared " + machine.getNumOfPreparedSalads() + " salads. Done.");
        }
    }
}
