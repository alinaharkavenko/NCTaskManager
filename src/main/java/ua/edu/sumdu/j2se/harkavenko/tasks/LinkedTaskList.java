package ua.edu.sumdu.j2se.harkavenko.tasks;

public class LinkedTaskList {
    private int size = 0;
    private Element element = null;

    public class Element {
        private Task task;
        private Element element1;

        public Element(Task task) {
            element1 = null;
            this.task = task;
        }

    }
    public void add(Task task) {
        Element el = new Element(task);
        if( size > 0 ) {
            el.element1 = element;
        }
        element = el;
        size++;
    }

    public boolean remove(Task task) {
        Element marker = element;
        if(marker.task == task) {
            element = marker.element1;
            size --;
            return  true;
        }

        while(marker.element1 != null){
            if( marker.element1.task == task ) {
                marker.element1 = marker.element1.element1;
                size --;
                return  true;
            }
            marker = marker.element1;

        }
        return false;
    }

    public int size(){
        return size;
    }

    public Task getTask(int index){
        if ( index >= size) {
            throw new IllegalArgumentException();
        }
        Element marker = element;
        int counter = 0;
        while(marker != null){
            if( counter == index) {
                return marker.task;
            }
            marker = marker.element1;
            counter ++;

        }
        return null;
    }

    public LinkedTaskList incoming(int from, int to){
        LinkedTaskList arr = new LinkedTaskList();
        Element marker = element;
        while(marker != null){
            int time = marker.task.nextTimeAfter(from);
            if( time <= to && time >from ) {
                arr.add(marker.task);
            }
            marker=marker.element1;
        }
        return arr;
    }
}


