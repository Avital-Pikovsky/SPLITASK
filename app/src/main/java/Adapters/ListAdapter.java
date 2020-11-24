package Adapters;

import java.util.ArrayList;

public class ListAdapter {

    public ArrayList<String> list;
    public String name;

public ListAdapter(String name, ArrayList<String> list){
    this.list = list;
    this.name = name;
}

    public String getName(){
        return this.name;
    }
    public void setName(String newName){
        this.name = newName;
    }
    public ArrayList<String> getList(){
        return this.list;
    }
    public void setList(ArrayList<String> newList){
        this.list = newList;
    }

}
