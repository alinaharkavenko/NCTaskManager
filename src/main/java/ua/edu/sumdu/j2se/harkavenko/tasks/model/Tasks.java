package ua.edu.sumdu.j2se.harkavenko.tasks.model;

import java.time.LocalDateTime;
import java.util.*;

public class Tasks {
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end){
        AbstractTaskList abstr = new LinkedTaskList();
        Iterator<Task> iter = tasks.iterator();
        while (iter.hasNext()) {
            Task iterTask = iter.next();
            LocalDateTime time = iterTask.nextTimeAfter(start);
            if(time != null && !time.isAfter(end)  && time.isAfter(start) ){
                abstr.add(iterTask) ;
            }
        }
        return abstr;
    }
    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end){
        SortedMap<LocalDateTime, Set<Task>> table = new TreeMap<>();
        Iterable<Task> iterable = incoming(tasks, start, end);
        Iterator<Task> iterator = iterable.iterator();
        while(iterator.hasNext()){
            Task task = iterator.next();
            LocalDateTime next = task.nextTimeAfter(start);
            if(task.isRepeated()){
                while(!next.isAfter(end) && !next.isAfter(task.getEndTime())){
                    if(!table.containsKey(next)){
                        table.put(next, new HashSet<>());
                    }
                    table.get(next).add(task);
                    next = next.plusSeconds(task.getRepeatInterval());
                }
            }else{
                if(!table.containsKey(next)){
                    table.put(next, new HashSet<>());
                }
                table.get(next).add(task.clone());
            }
        }
        return table;
    }
}
