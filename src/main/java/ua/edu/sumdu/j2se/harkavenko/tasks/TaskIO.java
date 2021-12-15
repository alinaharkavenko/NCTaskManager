package ua.edu.sumdu.j2se.harkavenko.tasks;

import com.google.gson.Gson;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Iterator;

public class TaskIO {
    public static void write(AbstractTaskList tasks, OutputStream out){
        DataOutputStream output = new DataOutputStream(out);
        Iterator<Task> itr = tasks.iterator();
        Task tsk;
        try{
            output.writeInt(tasks.size());
        }
        catch (IOException ex){
            ex.printStackTrace();

        }
        while(itr.hasNext()){
            tsk = itr.next();
            try{
                output.writeInt(tsk.getTitle().length());
                output.writeUTF(tsk.getTitle());
                output.writeBoolean(tsk.isActive());
                output.writeInt(tsk.getRepeatInterval());
                if(tsk.isRepeated()){
                    output.writeLong(tsk.getStartTime().toEpochSecond(ZoneOffset.UTC));
                    output.writeLong(tsk.getEndTime().toEpochSecond(ZoneOffset.UTC));
                }else{
                    output.writeLong(tsk.getTime().toEpochSecond(ZoneOffset.UTC));
                }
            }
            catch (IOException ex){
                    ex.printStackTrace();
            }
        }


    }
    public static void read(AbstractTaskList tasks, InputStream in){
        DataInputStream input = new DataInputStream(in);

        try {
            int i = input.readInt();
            while (i>0){
                int l = input.readInt();
                String t = input.readUTF();
                boolean a = input.readBoolean();
                int inn = input.readInt();
                if(inn!=0){
                    LocalDateTime str = LocalDateTime.ofEpochSecond(input.readLong(),0, ZoneOffset.UTC);
                    LocalDateTime end = LocalDateTime.ofEpochSecond(input.readLong(),0, ZoneOffset.UTC);
                    Task tas = new Task(t, str, end, inn);
                    tas.setActive(a);
                    tasks.add(tas);
                }else{
                    LocalDateTime time = LocalDateTime.ofEpochSecond(input.readLong(),0, ZoneOffset.UTC);
                    Task tas = new Task(t, time);
                    tas.setActive(a);
                    tasks.add(tas);
                }

                i--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeBinary(AbstractTaskList tasks, File file){

        try( FileOutputStream out = new FileOutputStream(file)){
            write(tasks, out);
            out.flush();        //очистка
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public static void readBinary(AbstractTaskList tasks, File file){
        try( FileInputStream in = new FileInputStream(file)){
            read(tasks, in);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void write(AbstractTaskList tasks, Writer out){
        Gson g = new Gson();
        ArrayTaskList a = new ArrayTaskList();
        tasks.getStream().forEach(a::add);
        g.toJson(a, out);
        try {
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void read(AbstractTaskList tasks, Reader in){
        Gson g = new Gson();
        ArrayTaskList a = g.fromJson(in, ArrayTaskList.class);
        a.getStream().forEach(tasks::add);
    }
    public static void writeText(AbstractTaskList tasks, File file){
        try(FileWriter f = new FileWriter(file) ){
            write(tasks, f);
            f.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void readText(AbstractTaskList tasks, File file){
        try(FileReader f = new FileReader(file) ){
            read(tasks, f);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
