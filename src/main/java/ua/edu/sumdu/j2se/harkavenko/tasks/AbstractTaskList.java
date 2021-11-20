package ua.edu.sumdu.j2se.harkavenko.tasks;

public abstract class AbstractTaskList {
    protected int size = 0;

    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public int size(){
        return  size;
    }
    public abstract Task getTask(int index);
    public abstract AbstractTaskList incoming(int from, int to);




}
