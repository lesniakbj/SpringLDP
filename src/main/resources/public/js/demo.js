var DEMO = DEMO || {};

DEMO.init = {
    initConsole: function() {
        if (typeof console  != "undefined") {
            if (typeof console.log != 'undefined') {
                console.olog = console.log;
            } else {
                console.olog = function() {};
            }
        }

        console.log = function(message) {
            console.olog(message);
            $('#rawJSONArea').append('<p>' + message + '</p>');
        };
        console.error = console.debug = console.info =  console.log
    }
};

$( document ).ready(function() {
    // Raw JSON Logging to DIV
    DEMO.init.initConsole();

    // Register any event handlers
    DEMO.login.registerFunctions();
    DEMO.admin.registerFunctions();
});