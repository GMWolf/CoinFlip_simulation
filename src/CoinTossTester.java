import java.util.Random;

/**
 * Created by Felix on 29-Mar-16.
 */
public class CoinTossTester implements Runnable {
    @Override
    public void run() {
        Random random = new Random();
        for(int i = 0; i < Simulation.REPEATS_PER_THREAD; i++) {
            int total = 0;
            int consecutive = 0;
            while(consecutive != Simulation.CONSECUTIVE_WINS) {
                total++;
                if (random.nextBoolean()) { //if toss coin succesfull
                    consecutive++;
                } else { //if not succesfull
                    consecutive = 0;
                }
            }
            Simulation.addRepeat(total);
        }
        Simulation.latch.countDown();
    }
}
