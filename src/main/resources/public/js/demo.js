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
                        console.log('Response from Login Request: ' + JSON.stringify(data, null, '\t'));
                    }
                },
                error: function(error) { console.log('Error: ' + JSON.stringify(error, null, '\t')); }
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
                     console.log('Successfully logged out user: ' + JSON.stringify(data, null, '\t'));
                 },
                 error: function(error) {
                    console.log('Error: ' + JSON.stringify(error, null, '\t'));
                 }
            });
        });
    }
};

DEMO.admin = {
    api: '/admin',

    registerFunctions: function() {
        this.viewEndpoints();
        this.viewAllTenants();
    },

    viewEndpoints: function() {
        $('#endpointsButton').on('click', function() {
            $.ajax({
                type: 'GET',
                url: DEMO.admin.api + '/endpoints',
                dataType: 'json',
                success: function(data) {
                    DEMO.admin.populateDemoArea(data, 'endpoints');
                },
                error: function(error) { console.log('Error: ' + JSON.stringify(error, null, '\t')); }
            });
        });
    },

    viewAllTenants: function() {
        $('#tenantsButton').on('click', function() {
            $.ajax({
                type: 'GET',
                url: DEMO.admin.api + '/tenant',
                dataType: 'json',
                success: function(data) {
                    DEMO.admin.populateDemoArea(data, 'tenants');
                },
                error: function(error) { console.log('Error: ' + JSON.stringify(error, null, '\t')); }
            });
        });
    },

    clearDemoArea: function() {
        $('#adminActionResponse').empty();
    },

    populateDemoArea: function(data, type) {
        console.log('Populating the demo area ' + JSON.stringify(data, null, '\t') + ' ' + type);
        DEMO.admin.clearDemoArea();

        switch(type) {
            case 'endpoints':
                $.each(data, function(i, endpoint) {
                    $('#adminActionResponse').append('<p>' + 'Path: ' + endpoint.pathPattern + ', Methods: ' + endpoint.method + '</p>');
                });
                break;
            case 'tenants':
                $.each(data, function(i, tenant) {
                    $('#adminActionResponse').append('<p>' + 'Name: ' + tenant.displayName + '</p>');
                });
                break;
        }
    }
}

$( document ).ready(function() {
    DEMO.init.initConsole();

    // Register any event handlers
    DEMO.login.registerFunctions();
    DEMO.admin.registerFunctions();
});