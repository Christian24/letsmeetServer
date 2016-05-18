package dataTransfer;

/**
 * Created by Christian on 18.05.2016.
 * For a list of return codes always check helpers.ReturnCodeHelper
 */
public class ReturnCodeResponse extends DataTransferObject {
    public ReturnCodeResponse()
    {
        super();
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
