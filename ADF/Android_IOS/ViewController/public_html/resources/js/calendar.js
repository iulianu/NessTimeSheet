(function () {
    if (!window.application)
        window.application = {
        };

    // Custom JS that will add an iFrame into the DOM
    highlightWeek = function () {
        unhighlightWeek();
        
        var args = arguments;
      
        var rowToH = $('#tl1 table > tr')[args[1]];      
       $(rowToH).addClass('highlightRow');

        return "application.testFunction - passed";
    };
    
    unhighlightWeek = function () {
    
    $('#tl1 table > tr').removeClass('highlightRow'); 
    };
})();