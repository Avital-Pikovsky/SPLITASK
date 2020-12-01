package Adapters;

import java.util.ArrayList;

public class JoinedListAdapter {

    public String id;

    public JoinedListAdapter(String id) {
        this.id = id;
    }

    public JoinedListAdapter() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String newID) {
        this.id = newID;
    }
}
