package dataTransfer;

import helpers.ReturnCodeHelper;

/**
 * Created by Christian on 18.05.2016.
 * For a list of return codes always check helpers.ReturnCodeHelper
 */
public class ReturnCodeResponse extends DataTransferObject {
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
