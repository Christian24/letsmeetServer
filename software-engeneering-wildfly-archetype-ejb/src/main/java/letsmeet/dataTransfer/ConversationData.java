package letsmeet.dataTransfer;

import java.util.ArrayList;

import letsmeet.meet.Conversation;
import letsmeet.meet.Reply;

/**
 * DTO for Conversation
 * Created by Christian on 12.06.2016.
 */
public class ConversationData extends UserContentData {

	private static final long serialVersionUID = -5043102158715961327L;
	
	protected int origin;
    protected ArrayList<ReplyData> replies;

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

        setOrigin(conversation.getOrigin().getId());
        replies = new ArrayList<ReplyData>();
        for(Reply reply : conversation.getReplies()){
        replies.add(new ReplyData(reply));
        }

    }

}
