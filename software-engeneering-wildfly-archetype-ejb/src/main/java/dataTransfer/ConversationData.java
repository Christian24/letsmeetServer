package dataTransfer;

import meet.Conversation;
import meet.Reply;

import java.util.ArrayList;

/**
 * Created by Christian on 12.06.2016.
 */
public class ConversationData extends UserContentData {
    protected int id;
    protected int origin;
    protected ArrayList<ReplyData> replies;
    public int getId(){
        return id;
    }
    public void setId(int newId){
        id= newId;
    }
    public ConversationData() {
        super();
        replies = new ArrayList<ReplyData>();
    }
    public void setOrigin(int newOrigin) {
        origin = newOrigin;

    }
    public int getOrigin(){
        return origin;
    }
    public ConversationData(Conversation conversation){
        super(conversation);
        setId(conversation.getId());
        setOrigin(conversation.getOrigin().getId());
        replies = new ArrayList<ReplyData>();
        for(Reply reply : conversation.getReplies()){
        replies.add(new ReplyData(reply));
        }

    }

}
