package dataTransfer;

import meet.Reply;

/**
 * Created by Christian on 12.06.2016.
 */
public class ReplyData extends UserContentData {
    protected int parent;
    protected int id;
 public int getParent() {
        return parent;
    }
    public void setParent(int newParent) {
        parent = newParent;
    }

    public ReplyData() {
    super();
    }
    public ReplyData(Reply original) {
        super(original);
        setId(original.getId());
        setParent((original.getParent().getId()));
    }
    public int getId(){
        return id;
    }
    public void setId(int newId){
        id= newId;
    }

}
