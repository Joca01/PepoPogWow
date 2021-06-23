package models;

import java.util.ArrayList;
import java.util.List;

public class MyQueue<E> {
    private List<E> list;
    public MyQueue(){
        this.list = new ArrayList<E>();
    }

    public void add(E elem){
        list.add(elem);
    }

    public List<E> getQueue(){
        return list;
    }

    public E pop(){
        E elem = null;
        try{
             elem = list.get(0);
            list.remove(0);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return elem;
    }
}
