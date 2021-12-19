package ua.edu.sumdu.j2se.harkavenko.tasks.model;

import org.apache.log4j.Logger;
import java.util.TimerTask;
import java.util.Set;

public class Notifier extends TimerTask {
    private Set<Task> taskSet;
    private final static Logger logger = Logger.getRootLogger();

    public Notifier(Set<Task> taskSet) {
        this.taskSet = taskSet;
    }

    @Override
    public void run() {
        taskNotify();
        logger.info("Notification showed");
    }

    private void taskNotify(){
        System.out.println("\n\n-----NOTIFICATION-----\nIt is time for the:\n");
        for (Task t: taskSet) {
            System.out.println(t.getTitle() + "\n");
        }
        System.out.println("\n");
    }
}
