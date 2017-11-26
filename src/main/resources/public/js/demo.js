var DEMO = {};

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
}

DEMO.login = {
    api: '/auth',

    loadTenants: function() {
        var tenantSelect = $('#loginTenant');
    },

    registerFunctions: function() {
        this.loginUser();
        this.logoutUser();
    },

    loginUser: function() {
        $('#loginEmailButton').on('click', function() {
            var loginData = {
                email: $('#loginEmail').val(),
                password: $('#loginPassword').val(),
                tenantId: $('#loginTenant').val()
            }
            $.ajax({
                type: 'POST',
                url: DEMO.login.api,
                dataType: 'json',
                data: JSON.stringify(loginData),
                success: function(data) {
                    if(loginData.tenantId === null) {
                        DEMO.login.populateTenantDropDown(data, loginData);
                    } else {
                        console.log('Response from Login Request: ' + JSON.stringify(data));
                    }
                },
                error: function(error) { console.log('Error: ' + JSON.stringify(error)); }
            });
        });
    },

    populateTenantDropDown: function(data, loginData) {
        var tenantDropDown = $('#loginTenant');
        tenantDropDown.append($('<option>', { value: -1, text: 'Admin' }));
        $.each(data, function(i, tenant) {
            tenantDropDown.append($('<option>', { value: tenant.tenantId, text: tenant.displayName }));
        });
        tenantDropDown.multiSelect('refresh');
    },

    logoutUser: function() {
        $('#logoutUserButton').on('click', function() {
            $.ajax({
                 type: 'DELETE',
                 url: DEMO.login.api + '/logout',
                 dataType: 'json',
                 success: function(data) {
                     console.log('Successfully logged out user: ' + JSON.stringify(data));
                 },
                 error: function(error) {
                    console.log('Error: ' + JSON.stringify(error));
                 }
            });
        });
    }
};

$( document ).ready(function() {
    DEMO.init.initConsole();

    DEMO.login.loadTenants();
    DEMO.login.registerFunctions();
});