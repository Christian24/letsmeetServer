package letsmeet.dataTransfer;

import java.util.HashSet;
import java.util.Set;

import letsmeet.meet.Conversation;
import letsmeet.meet.Reply;

/**
 * DTO for Conversation
 * @author Christian
 */
public class ConversationData extends UserContentData {

	private static final long serialVersionUID = -5043102158715961327L;
	
	protected int origin;

    public Set<ReplyData> getReplies() {
        return replies;
    }

    public void setReplies(Set<ReplyData> replies) {
        this.replies = replies;
    }

    protected Set<ReplyData> replies;

    public ConversationData() {
        super();
        replies = new HashSet<ReplyData>();
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
        replies = new HashSet<>();
        for(Reply reply : conversation.getReplies()){
        replies.add(new ReplyData(reply));
        }

    }

}
