package ua.edu.sumdu.j2se.harkavenko.tasks.view;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.harkavenko.tasks.model.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConsoleView {
    private static ConsoleView instance = null;

    private final static Logger logger = Logger.getRootLogger();

    private ConsoleView() {

    }

    public static ConsoleView getInstance() {
        logger.debug("Console view instance required");
        if (instance == null) instance = new ConsoleView();
        return instance;
    }

    public static void nextStep() {
        System.out.print("------------------------------------------------------------" +
                "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        logger.debug("Clear console");
    }


    public void showMainMenu(){
        nextStep();
        System.out.println("1 - Add\n2 - Calendar\n3 - Tasks\n4 - Exit");
        logger.info("Main menu has been printed");
    }


    public void showTaskMenuOptions(){
        nextStep();
        System.out.println("1 - Back\n2 - Filter\nTasks:");
        logger.info("Menu options showed");
    }

    public void showTasks(Iterable<Task> taskList, int option) {
        if (taskList != null) {
            for (Task temp : taskList) {
                System.out.println(option + " - " + temp.toString());
                option++;
            }
            logger.info("Tasks showed");
        } else logger.warn("Task list is empty");
    }

    public void showTaskDetails(Task task) {
        nextStep();
        System.out.println(task.toString());
        logger.info("Task details showed");
    }

    public void showCalendar(SortedMap<LocalDateTime, Set<Task>> map) {
        nextStep();

        Set<LocalDateTime> k = map.keySet();
        for (LocalDateTime t : k) {
            Set<Task> tasks = map.get(t);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            System.out.println(t.format(dtf));
            for (Task tsk : tasks) {
                System.out.println(tsk.toString());
            }
            System.out.print("\n");
        }
        logger.info("Calendar showed");
    }

    public void showTasksOption(Task task) {
        System.out.println("\n1 - Edit");
        if (task.isActive()) System.out.println("2 - Deactivate");
        else System.out.println("2 - Activate");
        System.out.println("3 - Delete");
        System.out.println("4 - Back");
        logger.info("Options for the task " + task.getTitle() + " showed");
    }

    public int readInt() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()){
            scanner.next();
        }
        return scanner.nextInt();
    }

    public String readNewTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("New title");
        return scanner.nextLine();
    }

    public String readTask() {
        nextStep();

        StringBuilder sb = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        System.out.println("Title");
        String title = sc.nextLine();

        System.out.println("Task repeated? 1 if yes, 0 if no");
        int repeatedInt = sc.nextInt();
        sb.append(repeatedInt).append("|").append(title).append("|");
        sc.nextLine();

        String format = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
        if (repeatedInt == 1) {
            System.out.println("Start time (Format yyyy-MM-dd HH:mm:ss)");
            String dateStr = sc.nextLine();
            while (!dateStr.matches(format)) dateStr = sc.nextLine();
            sb.append(dateStr).append("|");

            System.out.println("End time (Format yyyy-MM-dd HH:mm:ss)");
            dateStr = sc.nextLine();
            while (!dateStr.matches(format)) dateStr = sc.nextLine();
            sb.append(dateStr).append("|");

            System.out.println("Interval (seconds)");
            while (!sc.hasNextInt()) sc.next();
            sb.append(sc.nextInt()).append("|");
            sc.nextLine();
        } else {
            System.out.println("Time (Format yyyy-MM-dd HH:mm:ss)");
            String dateStr = sc.nextLine();
            while (!dateStr.matches(format)) dateStr = sc.nextLine();
            sb.append(dateStr).append("|");
        }
        System.out.println("Set task active? 1 if yes, 0 if no");
        sb.append(sc.nextInt());
        return sb.toString();
    }

    public String readFilterPeriod() {
        nextStep();
        Scanner scanner = new Scanner(System.in);
        StringBuilder builder = new StringBuilder();
        String format = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
        System.out.println("Start time (Format yyyy-MM-dd HH:mm:ss)");
        String dateStr = scanner.nextLine();
        while(!dateStr.matches(format)) dateStr = scanner.nextLine();
        builder.append(dateStr).append("|");

        System.out.println("End time (Format yyyy-MM-dd HH:mm:ss)");
        dateStr = scanner.nextLine();
        while(!dateStr.matches(format)) dateStr = scanner.nextLine();
        builder.append(dateStr);
        return builder.toString();
    }

    public String readNewTime(boolean repeated) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder builder = new StringBuilder();
        String format = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
        if(repeated) {
            System.out.println("Start time (Format yyyy-MM-dd HH:mm:ss)");
            String dateStr = scanner.nextLine();
            while(!dateStr.matches(format)) dateStr = scanner.nextLine();
            builder.append(dateStr).append("|");

            System.out.println("End time (Format yyyy-MM-dd HH:mm:ss)");
            dateStr = scanner.nextLine();
            while(!dateStr.matches(format)) dateStr = scanner.nextLine();
            builder.append(dateStr).append("|");

            System.out.println("Interval (seconds)");
            while (!scanner.hasNextInt()) scanner.next();
            builder.append(scanner.nextInt());
            scanner.nextLine();
        }
        else {
            System.out.println("Time (Format yyyy-MM-dd HH:mm:ss)");
            String dateStr = scanner.nextLine();
            while(!dateStr.matches(format)) dateStr = scanner.nextLine();
            builder.append(dateStr).append("|");
        }
        return builder.toString();
    }

    public void showEditMenu(boolean repeated, String info) {
        nextStep();
        System.out.println(info);
        System.out.println("\nWhat do you want to change?\n1 - Title");
        if(repeated){
            System.out.println("2 - Task time, interval\n3 - Back");
        }
        else{
            System.out.println("2 - Time\n3 - Back");
        }
        logger.info("Edit showed");
    }

    public void showWrongTimeMessage() {
        System.out.println("It is impossible to add task in the past");
    }
}
