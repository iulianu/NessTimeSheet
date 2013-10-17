//This is an event that fires when the user presses the device back button
document.addEventListener("deviceready", onDeviceReady, false);

function onDeviceReady() {
    document.addEventListener("backbutton", backKeyDown, true);
}

function backKeyDown() {
    //Check the device back button action happened in Employee.amx
    if ($('#EditTimePanel').length) {
        //Call the java method in managed bean
        adf.mf.api.invokeMethod("ness.mobile.MainFlowManagedBean", "handleNavigation", onInvokeSuccess, onFail);
    }
}


function onInvokeSuccess(param) {
};

function onFail() {
};