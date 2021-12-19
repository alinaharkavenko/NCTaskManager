package ua.edu.sumdu.j2se.harkavenko.tasks.model;

import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable, Cloneable{
    protected int size = 0;

    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public int size(){
        return  size;
    }
    public abstract Task getTask(int index);

    public abstract Stream<Task> getStream();

    public AbstractTaskList clone(){
        AbstractTaskList absList;
        if(this instanceof ArrayTaskList){
            absList = new ArrayTaskList();
        }else {
            absList = new LinkedTaskList();
        }
        for(int i = 0; i < size; i++){
            absList.add(this.getTask(i).clone());
        }

        return absList ;
    }
}
