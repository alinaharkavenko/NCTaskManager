package ua.edu.sumdu.j2se.harkavenko.tasks;

public class ArrayTaskList {
    private int size = 0;
    private Task[] array = new Task[5];

    public void add(Task task){
        if(size == array.length){
            Task[] massiv = new Task[size + 3];
            System.arraycopy(array, 0, massiv, 0, size);
            array = massiv;
        }
        array[size] = task;
        size++;
    }
    public boolean remove(Task task){
        for(int i = 0; i<size; i++){
            if(task == array[i]){
                for(int j = i+1; j<size; j++){
                    array[i] = array[j];
                    i++;

                }
                size--;
                return true;
            }

        }
        return false;
    }

    public int size(){
        return size;
    }

    public Task getTask(int index){
        return array[index];
    }

    public ArrayTaskList incoming(int from, int to){
        ArrayTaskList arr = new ArrayTaskList();
        for( int i = 0; i < size; i++){
            int element;
            element = array[i].nextTimeAfter(from);
            if(element > from && element <= to){
                arr.add(array[i]);
            }
        }
        return arr;
    }
}
