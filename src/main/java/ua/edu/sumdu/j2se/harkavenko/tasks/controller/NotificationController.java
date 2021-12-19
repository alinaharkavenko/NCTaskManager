package ua.edu.sumdu.j2se.harkavenko.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.harkavenko.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.harkavenko.tasks.model.Notifier;
import ua.edu.sumdu.j2se.harkavenko.tasks.model.Task;
import ua.edu.sumdu.j2se.harkavenko.tasks.model.Tasks;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class NotificationController {

    private AbstractTaskList taskList;
    private List<Timer> timers = new LinkedList<>();

    private final static Logger logger = Logger.getRootLogger();

    public NotificationController(AbstractTaskList taskList) {
        logger.info("Notification controller created");
        this.taskList = taskList;
    }

    public void setTimers() {
        cancelTimers();
        SortedMap<LocalDateTime, Set<Task>> map = Tasks.calendar(taskList, LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        List<TimerTask> taskList = new LinkedList<>();
        int i = 0;
        for(Map.Entry<LocalDateTime, Set<Task>> entry : map.entrySet()) {
            taskList.add(new Notifier(entry.getValue()));
            Timer timer = new Timer();
            timer.schedule(taskList.get(i), Date.from(entry.getKey().atZone(ZoneId.systemDefault()).toInstant()));
            timers.add(timer);
            i++;
        }
        logger.info("Timers are ready");
    }

    private void cancelTimers() {
        for (Timer t : timers) {
            t.cancel();
        }
        logger.debug("Timers cancelled");
    }
}
