package letsmeet.dataTransfer;

import letsmeet.meet.Reply;

/**
 * DTO for Reply class
 * @author Christian
 *
 */
public class ReplyData extends UserContentData {

	private static final long serialVersionUID = -4222386759011868637L;

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
