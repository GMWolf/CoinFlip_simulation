import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Felix on 29-Mar-16.
 */
public class Simulation {

    public static final int CONSECUTIVE_WINS = 10;
    public static final int THREADS = 4;
    public static final int REPEATS = 8000000;
    public static final int REPEATS_PER_THREAD = REPEATS / THREADS;

    private static long total_tosses = 0;
    private static int repeats = 0;
    private static int min = Integer.MAX_VALUE;
    private static int max = 0;


    public static CountDownLatch latch;
    public static void main(String[] args) throws FileNotFoundException {

        latch = new CountDownLatch(THREADS);

        //measure performance
        long startTime = System.currentTimeMillis();

        //create and start threads
        Thread[] threads = new Thread[THREADS];
        for(int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(new CoinTossTester());
            threads[i].start();
        }

        //wait for threads
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long executionTime = System.currentTimeMillis() - startTime;

        System.out.println("Average tosses " + total_tosses / repeats);
        System.out.println("min, max " + min + ", " + max);
        System.out.println("repeats " + repeats);
        System.out.println("time " + executionTime);
    }

    public static synchronized void addRepeat(int count) {
        total_tosses += count;
        repeats++;
        if (count > max) {
            max = count;
        }
        if (count < min) {
            min = count;
        }
    }
}
