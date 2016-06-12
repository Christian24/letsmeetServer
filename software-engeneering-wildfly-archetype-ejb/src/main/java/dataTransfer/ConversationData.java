package dataTransfer;

import meet.Conversation;

/**
 * Created by Christian on 12.06.2016.
 */
public class ConversationData extends UserContentData {
    protected int id;
    protected MeetData origin;
    public int getId(){
        return id;
    }
    public void setId(int newId){
        id= newId;
    }
    public ConversationData() {
        super();
    }
    public void setOrigin(MeetData newOrigin) {
        origin = newOrigin;
    }
    public MeetData getOrigin(){
        return origin;
    }
    public ConversationData(Conversation conversation){
        super(conversation);
        setId(conversation.getId());
        setOrigin(new MeetData(conversation.getOrigin()));

    }

}
