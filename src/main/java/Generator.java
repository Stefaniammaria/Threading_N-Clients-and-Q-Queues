import java.util.*;

public class Generator {

    private ArrayList<Task> list;
    public int timeLimit = 100;
    public int maxProcessingTime = 10;
    public int minProcessingTime = 2;
    public int maxArrivalTime = 4;
    public int minArrivalTime = 2;
    public int numberOfServers = 4;

    public Generator() {
        list = new ArrayList<>();
    }

    public ArrayList<Task> getList() {
        return list;
    }

    public void setList(ArrayList<Task> list) {
        this.list = list;
    }

    public ArrayList<Task> generateTasks(int numberOfClients) {
        for (int i = 0; i < numberOfClients; i++) {
            Task task = new Task();
            Random random = new Random();
            int rand_int1 = random.nextInt(maxArrivalTime + 1);
            int rand_int2 = random.nextInt(maxProcessingTime + 1);
            if (rand_int1 >= minArrivalTime && rand_int2 >= minProcessingTime) {
                task.setId(i);
                task.setArrivalTime(rand_int1);
                task.setServiceTime(rand_int2);
                list.add(task);
            }
            else i--;
        }
        Collections.sort(list);
        return list;
    }
}
