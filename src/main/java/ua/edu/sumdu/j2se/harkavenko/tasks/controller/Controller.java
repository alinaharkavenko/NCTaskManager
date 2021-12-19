package ua.edu.sumdu.j2se.harkavenko.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.harkavenko.tasks.model.*;
import ua.edu.sumdu.j2se.harkavenko.tasks.view.ConsoleView;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Iterator;

public class Controller {
    private static Controller instance = null;
    private static ConsoleView view;
    private final File dataFile = new File("data.bin");
    private AbstractTaskList taskList;
    private NotificationController notificationController;

    private final static Logger logger = Logger.getRootLogger();

    private Controller() {
        logger.info("Controller created");
        taskList = new ArrayTaskList();
        TaskIO.readBinary(taskList, dataFile);
        view = ConsoleView.getInstance();
        notificationController = new NotificationController(taskList);
        notificationController.setTimers();
    }

    public static Controller getInstance() {
        logger.debug("Instance requested");
        if (instance == null) instance = new Controller();
        return instance;
    }

    public void saveData(AbstractTaskList model, File dataFile) {
        dataFile.delete();
        logger.debug(dataFile.getName() + " deleted");
        try {
            dataFile.createNewFile();
            logger.debug(dataFile.getName() + " re-created");
        }
        catch (IOException e) {
            logger.error("saveData IOException");
            System.exit(-1);
        }
        TaskIO.writeBinary(model, dataFile);
        logger.info("Data saved to the " + dataFile.getName());
    }


    public void mainMenu() {
        int intt = 0;
        while(intt != 4) {
            view.showMainMenu();
            intt = view.readInt();
            switch (intt){
                case 1:
                    logger.debug("Task add option");
                    String taskStr = view.readTask();
                    parseAndAddTask(taskStr);
                    notificationController.setTimers();
                    logger.info("Timers reset");
                    break;
                case 2:
                    logger.debug("Calendar option");
                    calendarMenu();
                    break;
                case 3:
                    logger.debug("Tasks option");
                    tasksMenu(taskList);
                    break;
                case 4:
                    saveData(taskList, dataFile);
                    logger.debug("Exit Task Manager");
                    System.exit(0);
                    break;
            }
        }
    }

    private void parseAndAddTask(String s) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if (s.startsWith("1")) {
                String parameters[] = s.split("\\|");

                String title = parameters[1];

                LocalDateTime start = LocalDateTime.parse(parameters[2], dtf);
                LocalDateTime end = LocalDateTime.parse(parameters[3], dtf);
                int interval = Integer.parseInt(parameters[4]);
                Task task = new Task(title, start, end, interval);

                if (s.endsWith("1")) task.setActive(true);

                if (end.isBefore(LocalDateTime.now())) view.showWrongTimeMessage();
                else taskList.add(task);
            }
            else {
                String parameters[] = s.split("\\|");

                String title = parameters[1];
                LocalDateTime time = LocalDateTime.parse(parameters[2], dtf);
                Task task = new Task(title, time);

                if (s.endsWith("1")) task.setActive(true);

                if(time.isBefore(LocalDateTime.now())) view.showWrongTimeMessage();
                else taskList.add(task);
            }
            logger.info("New task added");
        }
        catch (Exception e) {
            logger.warn("Wrong data: " + e.getMessage());
            System.out.println("Wrong data received, please try again");
        }
    }

    private LinkedTaskList filter(String periodStr) {
        try {
            String interval[] = periodStr.split("\\|", 2);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime from = LocalDateTime.parse(interval[0], dtf);
            LocalDateTime to = LocalDateTime.parse(interval[1], dtf);
            if (from.isAfter(to)) {
                logger.warn("Wrong time period");
            }
            else {
                Iterator<Task> it = Tasks.incoming(taskList, from, to).iterator();
                LinkedTaskList filteredTaskList = new LinkedTaskList();
                while (it.hasNext()) {
                    filteredTaskList.add(it.next());
                }
                logger.info("Tasks filtered");
                return filteredTaskList;
            }
        }
        catch (Exception e) {
            logger.warn("Wrong data: " + e.getMessage());
            System.out.println("Wrong data received, please try again");
        }
        return null;
    }

    private void editTask(Task task) {
        boolean repeated = task.isRepeated();
        int intt = 0;
        while(intt != 3){
            view.showEditMenu(repeated, task.toString());
            intt = view.readInt();
            switch (intt){
                case 1:
                    task.setTitle(view.readNewTitle());
                    logger.info("Title changed");
                    break;
                case 2:
                    String param[] = view.readNewTime(repeated).split("\\|", 3);
                    try {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        if (repeated) {
                            LocalDateTime start = LocalDateTime.parse(param[0], dtf);
                            LocalDateTime end = LocalDateTime.parse(param[1], dtf);
                            int interval = Integer.parseInt(param[2]);
                            task.setTime(start, end, interval);
                        } else {
                            System.out.println(param[0]);
                            LocalDateTime time = LocalDateTime.parse(param[0], dtf);
                            task.setTime(time);
                        }
                        logger.info("Time changed");
                    }
                    catch (DateTimeParseException e){
                        logger.error("String was not parsed to LocalDateTime: " + e.getMessage());
                        System.out.println("Wrong data received");
                    }
                    break;
            }
        }
    }


    private void tasksMenu(AbstractTaskList taskList) {
        int intt = 0;
        while (intt != 1) {
            view.showTaskMenuOptions();
            view.showTasks(taskList, 3);
            intt = view.readInt();
            if (intt == 2) {
                logger.info("Task filter option");
                String interval = view.readFilterPeriod();
                if (filter(interval) != null) {
                    tasksMenu(filter(interval));
                }
            }
            else if (intt > 2 && intt < taskList.size() + 3) {
                logger.info("Task #" + intt + " option");
                taskMenu(taskList.getTask(intt - 3));
            }
        }
    }

    private void taskMenu(Task task) {
        view.showTaskDetails(task);
        view.showTasksOption(task);
        int intt = view.readInt();
        switch (intt){
            case 1:
                logger.debug("Task edit option");
                editTask(task);
                notificationController.setTimers();
                logger.info("Timers reset");
                break;
            case 2:
                logger.debug("Task activate/deactivate option");
                if(task.isActive()){
                    task.setActive(false);
                    logger.info("Task deactivated");
                }
                else{
                    task.setActive(true);
                    logger.info("Task activated");
                }
                notificationController.setTimers();
                logger.info("Timers reset");
                break;
            case 3:
                taskList.remove(task);
                logger.info("Task deleted");
                notificationController.setTimers();
                logger.info("Timers reset");
                break;
        }
    }

    private void calendarMenu(){
        int input = 0;
        while(input != 1) {
            view.showCalendar(Tasks.calendar(taskList, LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
            Iterable<Task> iterable = Tasks.incoming(taskList,LocalDateTime.now(), LocalDateTime.now().plusDays(1));
            System.out.println("\n1 - Back");
            view.showTasks(iterable, 2);
            input = view.readInt();
            Iterator<Task> it = iterable.iterator();
            if(input > 1) {
                Task t;
                int intt = 0;
                while (it.hasNext()) {
                    t = it.next();
                    if(intt == input - 2) {
                        logger.info("Task #" + intt + " option" );
                        taskMenu(t);
                    }
                    intt++;
                }
            }
        }
    }
}
