import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Scheduler {

    private final ArrayList<Server> servers = new ArrayList<>();
    private final ArrayList<Thread> threads = new ArrayList<>();
    private final ArrayList<Task> tasks;
    private final static Logger logger = Logger.getLogger(Scheduler.class.getName());

    public Scheduler(ArrayList<Task> tasks, int maxNoServers) {
        this.tasks = tasks;

        for (int i = 0; i < maxNoServers; i++) {
            servers.add(new Server());
        }

        servers.forEach(s -> threads.add(new Thread(s)));
    }

    public synchronized Server getServerWithMinimumWaitingPeriod() {
        Collections.sort(servers);
        //servers.forEach(System.out::println);
        return servers.get(0);
    }

    public void startScheduler() throws IOException {
        Logs logs = new Logs("LogEvents.txt");
        threads.forEach(Thread::start);

        tasks.forEach(t -> {
            logger.info("Task "+ t.getId() +" assigned to Server " + getServerWithMinimumWaitingPeriod().getID());
            logs.logger.info("Task "+ t.getId() +" assigned to Server " + getServerWithMinimumWaitingPeriod().getID());
            //System.out.println("Task assigned to Server " + getServerWithMinimumWaitingPeriod().getID());

            getServerWithMinimumWaitingPeriod().addTask(t);
        });
    }
}
