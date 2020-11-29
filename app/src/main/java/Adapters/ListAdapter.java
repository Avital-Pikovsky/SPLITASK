package Adapters;

import java.util.ArrayList;

public class ListAdapter {

    public ArrayList<String> list;
    public String name;
    public String ownerId;
    public int id;

    public ListAdapter(String name, ArrayList<String> list, String ownerId, int id) {
        this.list = list;
        this.name = name;
        this.ownerId = ownerId;
        this.id = id;
    }

    public ListAdapter() {
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

    public String getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(String newOwnerID) {
        this.ownerId = newOwnerID;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int newID) {
        this.id = newID;
    }
}
