package letsmeet.dataTransfer;

import letsmeet.helpers.ReturnCodeHelper;

/**
 * For a list of return codes always check helpers.ReturnCodeHelper
 * Created by Christian on 18.05.2016.
 *
 */
public class ReturnCodeResponse extends DataTransferObject {

	private static final long serialVersionUID = -7206933922166304817L;
	
	/**
     * Dummy constructor returning No_Acess
     */
    public ReturnCodeResponse()
    {
        super();
        this.returnCode = ReturnCodeHelper.NO_ACCESS;
    }
    public ReturnCodeResponse(int returnCode) {
        super();
        this.returnCode = returnCode;
    }
    protected int returnCode;
    public int getReturnCode() {return returnCode;}
    public void setReturnCode(int newReturnCode) {
        returnCode = newReturnCode;
    }
}