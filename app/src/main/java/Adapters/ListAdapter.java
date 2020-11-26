package Adapters;

import java.util.ArrayList;

public class ListAdapter {

    public ArrayList<String> list;
    public String name;
    public String ownerId;
    public int id;
    public static int idCounter = 1;

    public ListAdapter(String name, ArrayList<String> list, String ownerId) {
        this.list = list;
        this.name = name;
        this.ownerId = ownerId;
        this.id = idCounter;
        idCounter++;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public ArrayList<String> getList() {
        return this.list;
    }

    public void setList(ArrayList<String> newList) {
        this.list = newList;
    }

}
