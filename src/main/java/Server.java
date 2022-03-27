import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable, Comparable<Server> {

    private final BlockingQueue<Task> tasks;
    private final int ID;
    private final AtomicInteger waitingPeriod = new AtomicInteger(0);
    private boolean running = true;
    private static int lastID = 0;
    private Logger logger = Logger.getLogger(Server.class.getName());

    public Server() {
        ID = lastID + 1;
        lastID++;
        tasks = new LinkedBlockingQueue<>();
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public int getID() {
        return ID;
    }

    public synchronized void addTask(Task newTask) {
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());
    }

    private synchronized void waitingTime(Task t) throws InterruptedException {
        Thread.sleep(t.getServiceTime() * 1000L);
        waitingPeriod.decrementAndGet();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Logs logs = new Logs("LogEvents.txt");
                Task t = tasks.poll();
                if (t != null) {
                    logger.info("Se executa taskul " + t);
                    logs.logger.info("Se executa taskul " + t);
                    //System.out.println("Se executa taskul " + t);
                    waitingTime(t);
                    logger.info("S-a executat taskul " + t);
                    logs.logger.info("S-a executat taskul " + t);
                    //System.out.println("S-a executat taskul " + t);
                } else {
                    running = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int compareTo(Server o) {
        return waitingPeriod.get() - o.getWaitingPeriod().get();
    }

    @Override
    public String toString() {
        return "Server{" +
                "tasks=" + tasks +
                ", ID=" + ID +
                ", waitingPeriod=" + waitingPeriod +
                '}';
    }
}
