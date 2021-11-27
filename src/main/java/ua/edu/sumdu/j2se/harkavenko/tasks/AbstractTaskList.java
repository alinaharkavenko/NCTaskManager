package ua.edu.sumdu.j2se.harkavenko.tasks;

import java.util.Iterator;

public abstract class AbstractTaskList implements Iterable, Cloneable{
    protected int size = 0;

    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public int size(){
        return  size;
    }
    public abstract Task getTask(int index);
    public AbstractTaskList incoming(int from, int to){
            AbstractTaskList abstr = new LinkedTaskList();
            Iterator<Task> iter = this.iterator();
            while (iter.hasNext()) {
                Task iterTask = iter.next();
                int time = iterTask.nextTimeAfter(from);
                if(time <= to && time > from){
                    abstr.add(iterTask) ;
                }
            }
            return abstr;
    }
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
