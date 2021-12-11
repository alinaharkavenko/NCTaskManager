package ua.edu.sumdu.j2se.harkavenko.tasks;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Cloneable, Serializable {
    private String title;
    private LocalDateTime time;
    private LocalDateTime start;
    private LocalDateTime end;
    private int interval;
    private boolean active;
    private boolean repeated;

    /**
     * Конструктор, що конструює неактивну задачу, яка
     * виконується у заданий час без повторення із заданою назвою.
     */
    public Task(String title, LocalDateTime time) throws IllegalArgumentException {
        if( time == null ){
            throw new IllegalArgumentException();
        }
        this.title = title;
        this.time = LocalDateTime.of(time.toLocalDate(),time.toLocalTime());
        active = false;
        repeated = false;

    }

    /**
     * Конструктор, що конструює неактивну задачу, яка виконується у заданому
     * проміжку часу (і початок і кінець включно) із
     * заданим інтервалом і має задану назву.
     */
    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) throws IllegalArgumentException {
        if( start == null || end == null || interval < 0){
            throw new IllegalArgumentException();
        }
        this.title = title;
        this.start = LocalDateTime.of(start.toLocalDate(),start.toLocalTime());;
        this.end = LocalDateTime.of(end.toLocalDate(),end.toLocalTime());;
        this.interval = interval;
        active = false;
        repeated = true;
    }

    /**
     * Метод, що зчитує назву задачі
     */
    public String getTitle() {
        return title;
    }

    /**
     * Метод, що встановлює назву задачі
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Метод, що зчитує стан задачі
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Метод, що встановлює стан задачі
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Метод для зчитування та зміни часу виконання для задач, що не повторюються, у разі,
     * якщо задача повторюється метод має повертати час початку повторення;
     */
    public LocalDateTime getTime() {
        return isRepeated() ? start : time;

    }

    /**
     * Метод для зчитування та зміни часу виконання для задач, що не повторюються, у разі,
     * якщо задача повторювалась, вона має стати такою, що не повторюється
     */
    public void setTime(LocalDateTime time) {
        if (isRepeated()) {
            repeated = false;
        }
        this.time = LocalDateTime.of(time.toLocalDate(),time.toLocalTime());
    }

    /**
     * Метод для зчитування та зміни часу виконання для задач, що  повторюються, у разі,
     * якщо задача не повторюється метод має повертати час виконання задачі;
     */
    public LocalDateTime getStartTime() {
        return isRepeated() ? start : time;
    }

    /**
     * Метод для зчитування та зміни часу виконання для задач, що  повторюються, у разі,
     * якщо задача не повторюється метод має повертати час виконання задачі;
     */
    public LocalDateTime getEndTime() {
        return isRepeated() ? end : time;
    }

    /**
     * Метод для зчитування та зміни часу виконання для задач, що  повторюються, у разі,
     * якщо задача не повторюється метод має повертати 0;
     */
    public int getRepeatInterval() {
        return isRepeated() ? interval : 0;
    }

    /**
     * Метод для зчитування та зміни часу виконання для задач, що  повторюються, у разі,
     * якщо задача не повторювалася метод має стати таким, що повторюється.
     */
    public void setTime(LocalDateTime start, LocalDateTime end, int interval) {
        if (!isRepeated()) {
            repeated = true;
        }
        this.start = LocalDateTime.of(start.toLocalDate(),start.toLocalTime());
        this.end = LocalDateTime.of(end.toLocalDate(),end.toLocalTime());
        this.interval = interval;
    }

    /**
     * Метод для перевірки повторюваності задачі
     */
    public boolean isRepeated() {
        return repeated;
    }

    /**
     * метод , що повертає час наступного
     * виконання задачі після вказаного часу current, якщо після вказаного часу задача не виконується, то
     * метод має повертати -1.
     */

    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if (current == null){
            throw new IllegalArgumentException();
        }
        if (!isActive()) {
            return null;
        } else {
            if (!isRepeated()) {
                return (!current.isBefore(time) ) ? null : time;
            } else {
                LocalDateTime nextTime = null;
                for (LocalDateTime i = start; !i.isAfter(end) ; i = i.plusSeconds(interval)) {
                    if (current.isBefore(i)) {
                        nextTime = i;
                        return nextTime;
                    }
                }
                return nextTime;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return time == task.time &&
                start == task.start &&
                end == task.end &&
                interval == task.interval &&
                active == task.active &&
                repeated == task.repeated &&
                title.equals(task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, time, start, end, interval, active, repeated);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", start=" + start +
                ", end=" + end +
                ", interval=" + interval +
                ", active=" + active +
                ", repeated=" + repeated +
                '}';
    }

    @Override
    public Task clone(){
        Task clonTask = new Task(title, time);
        clonTask.start = this.start;
        clonTask.end = this.end;
        clonTask.interval = this.interval;
        clonTask.active = this.active;
        clonTask.repeated = this.repeated;
        return clonTask;

    }




}

