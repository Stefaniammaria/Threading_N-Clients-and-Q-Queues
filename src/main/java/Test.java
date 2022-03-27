import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Test {
    private final static Logger logger = Logger.getLogger(Test.class.getName());

    public static void main(String[] args) throws IOException {
        Generator generator = new Generator();
        ArrayList<Task> tasks = generator.generateTasks(10);

        Logs logs = new Logs("LogEvents.txt");

        for (Task t : tasks) {

            logs.logger.info(t.getId() + " " + t.getArrivalTime() + " " + t.getServiceTime());
            logger.info(t.getId() + " " + t.getArrivalTime() + " " + t.getServiceTime());
            System.out.println(t.getId() + " " + t.getArrivalTime() + " " + t.getServiceTime());
        }

        Scheduler sch = new Scheduler(tasks, 4);
        try {
            sch.startScheduler();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

