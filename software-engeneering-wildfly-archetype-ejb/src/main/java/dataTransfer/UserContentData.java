package dataTransfer;

import helpers.ServerHelper;
import meet.UserContent;


/**
 * Created by Christian on 12.06.2016.
 */
public abstract class UserContentData extends DataTransferObject {
protected  UserPersistenceData poster;
    protected String text;
    protected long timestamp;
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setPoster(UserPersistenceData userData) {
        poster = userData;
    }
    public UserPersistenceData getPoster(){
        return poster;
    }
    public void setText(String content) {
        text = content;
    }
    public String getText() {
        return text;
    }
    public UserContentData() {

    }
    public UserContentData(UserContent original ){
        setText(original.getText());
        setPoster( new UserPersistenceData(original.getPostedBy()));
        setTimestamp(ServerHelper.getUnixTimestamp(original.getTimestamp()));
    }
}
