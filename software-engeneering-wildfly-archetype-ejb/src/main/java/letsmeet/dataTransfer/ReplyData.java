package letsmeet.dataTransfer;

import letsmeet.meet.Reply;

/**
 * DTO for Reply class
 * Created by Christian on 12.06.2016.
 */
public class ReplyData extends UserContentData {
    protected int parent;

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
        setParent((original.getParent().getId()));
    }


}
