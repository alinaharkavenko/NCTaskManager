package ua.edu.sumdu.j2se.harkavenko.tasks;

import java.util.Iterator;
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

    /*public final AbstractTaskList incoming(int from, int to){
            AbstractTaskList abstr = new LinkedTaskList();
            Stream<Task> str = getStream();
            str.forEach( a-> {
                int time = a.nextTimeAfter(from);
                if( time <= to && time >from ) {
                    abstr.add(a);
                }
            });
            return abstr;
    }*/

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
