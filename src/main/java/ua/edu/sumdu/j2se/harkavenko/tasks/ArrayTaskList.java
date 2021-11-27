package ua.edu.sumdu.j2se.harkavenko.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

public class ArrayTaskList extends AbstractTaskList{
    private Task[] array = new Task[5];

    /**
     *  метод, що додає до списку вказану задачу
     */
    @Override
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
    @Override
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
     *      метод, що повертає задачу, яка знаходиться на вказаному місці у
     *     списку, перша задача має індекс 0.
     */
    @Override
    public Task getTask(int index){
        if ( index >= size || index < 0){
            throw new IndexOutOfBoundsException();
        }
        return array[index];
    }

    @Override
    public Stream<Task> getStream() {
        ArrayList<Task> arrayList = new ArrayList<>();
        for(int i = 0; i < size; i++){
            arrayList.add(this.getTask(i));
        }
        return arrayList.stream();
    }

    /**
     * метод, що повертає підмножину задач, які заплановані на виконання хоча б раз
     * після часу from і не пізніше ніж to.
     */
   /* @Override
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
*/
    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public Task next() {
                index++;
                return array[index-1];
            }

            @Override
            public void remove(){
                if(index > 0){
                    index--;
                    ArrayTaskList.this.remove(array[index]);

                }else throw new IllegalStateException();

            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayTaskList that = (ArrayTaskList) o;
        return Arrays.equals(array, that.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    @Override
    public String toString() {
        return "ArrayTaskList{" +
                "array=" + Arrays.toString(array) +
                '}';
    }
}
