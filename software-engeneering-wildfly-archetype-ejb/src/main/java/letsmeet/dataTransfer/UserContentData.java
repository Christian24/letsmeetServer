package letsmeet.dataTransfer;

import letsmeet.helpers.ServerHelper;
import letsmeet.meet.UserContent;

import java.util.Date;


/**
 * Abstract dto for comments/conversations
 * Created by Christian on 12.06.2016.
 */
public abstract class UserContentData extends DataTransferObject {

	private static final long serialVersionUID = 8655337964823818122L;

	protected int id;
	protected String poster;
    protected String text;
    protected Date timestamp;
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public void setPoster(String userData) {
        poster = userData;
    }
    public String getPoster(){
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
        setId(original.getId());
        setText(original.getContent());
        setPoster( original.getPoster());
        setTimestamp(original.getPostedAt());
    }
    public int getId(){
        return id;
    }
    public void setId(int newId){
        id= newId;
    }
}
