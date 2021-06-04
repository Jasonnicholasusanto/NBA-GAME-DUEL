import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class CountTime {
    static int interval;
    static java.util.Timer timer;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Input seconds => : ");
        String secs = sc.nextLine();
        int delay = 1000;
        int period = 1000;
        timer = new java.util.Timer();
        interval = Integer.parseInt(secs);
        System.out.println(secs);
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                System.out.println(setInterval());

            }
        }, delay, period);
    }

    private static int setInterval() {
        if (interval == 1)
            timer.cancel();
        return --interval;
    }
}
