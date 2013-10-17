package ness.mobile;

import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;

public class MainFlowManagedBean {
    public MainFlowManagedBean() {
        super();
    }
    
    public void handleNavigation() {
        //Code to naviagte within task flows programmatically
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction(AdfmfJavaUtilities.getFeatureName(),
                                                                  "adf.mf.api.amx.doNavigation",
                                                                  new Object[] { "backToCalendar" });
    }
}
