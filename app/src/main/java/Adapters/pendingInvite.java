package Adapters;

public class pendingInvite {

    private String listName;
    private String creatorName;
    private String listId;

    public pendingInvite(){}

    public pendingInvite(String newName, String newCreatorName, String newListId){
        this.listName = newName;
        this.creatorName = newCreatorName;
        this.listId = newListId;

    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }
}
