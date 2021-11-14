package ua.edu.sumdu.j2se.harkavenko.tasks;

public class ArrayTaskList {
    private int size = 0;
    private Task[] array = new Task[5];

    /**
     *  метод, що додає до списку вказану задачу
     */
    public void add(Task task){
        if( task == null){
            throw new NullPointerException();
        }
        if(size == array.length){
            Task[] massiv = new Task[size + 3];
            System.arraycopy(array, 0, massiv, 0, size);
            array = massiv;
        }
        array[size] = task;
        size++;
    }
    /**
     *     метод, що видаляє задачу зі списку і повертає істину, якщо
     *     така задача була у списку. Якщо у списку було декілька таких задач, необхідно видалити одну
     *     будь-яку.
     */
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
    /**
     *  метод, що повертає кількість задач у списку
     */
    public int size(){
        return size;
    }
    /**
     *      метод, що повертає задачу, яка знаходиться на вказаному місці у
     *     списку, перша задача має індекс 0.
     */
    public Task getTask(int index){
        if ( index >= size || index < 0){
            throw new IndexOutOfBoundsException();
        }
        return array[index];
    }
    /**
     * метод, що повертає підмножину задач, які заплановані на виконання хоча б раз
     * після часу from і не пізніше ніж to.
     */
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
