package ua.edu.sumdu.j2se.harkavenko.tasks;


public class Task {
    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean active;
    private boolean repeated;

    /**
     * Конструктор, що конструює неактивну задачу, яка
     * виконується у заданий час без повторення із заданою назвою.
     */
    public Task(String title, int time) {
        this.title = title;
        this.time = time;
        active = false;
        repeated = false;
    }


    /**
     * Конструктор, що конструює неактивну задачу, яка виконується у заданому
     * проміжку часу (і початок і кінець включно) із
     * заданим інтервалом і має задану назву.
     */
    public Task(String title, int start, int end, int interval) {
        this.title = title;
        this.start = start;
        this.end = end;
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
    public int getTime() {
        return isRepeated() ? start : time;

    }

    /**
     * Метод для зчитування та зміни часу виконання для задач, що не повторюються, у разі,
     * якщо задача повторювалась, вона має стати такою, що не повторюється
     */
    public void setTime(int time) {
        if (isRepeated()) {
            repeated = false;
        }
        this.time = time;
    }

    /**
     * Метод для зчитування та зміни часу виконання для задач, що  повторюються, у разі,
     * якщо задача не повторюється метод має повертати час виконання задачі;
     */
    public int getStartTime() {
        return isRepeated() ? start : time;
    }

    /**
     * Метод для зчитування та зміни часу виконання для задач, що  повторюються, у разі,
     * якщо задача не повторюється метод має повертати час виконання задачі;
     */
    public int getEndTime() {
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
    public void setTime(int start, int end, int interval) {
        if (!isRepeated()) {
            repeated = true;
        }
        this.start = start;
        this.end = end;
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

    public int nextTimeAfter(int current) {
        if (!isActive()) {
            return -1;
        } else {
            if (!isRepeated()) {
                return (current >= time) ? -1 : time;
            } else {
                int nextTime = -1;
                for (int i = start; i < end; i = i + interval) {
                    if (current < i) {
                        nextTime = i;
                        return nextTime;
                    }
                }
                return nextTime;
            }
        }
    }
}

