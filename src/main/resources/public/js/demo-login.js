var DEMO = DEMO || {};

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
                        DEMO.login.populateTenantDropDown(data);
                    } else {
                        $('#loggedInTenantName').text(loginData.tenantId);
                        $('#loggedInUserName').text(loginData.email);
                        console.log('Response from Login Request: ' + JSON.stringify(data, null, '\t'));
                    }
                },
                error: function(error) { console.log('Error: ' + JSON.stringify(error, null, '\t')); }
            });
        });
    },

    populateTenantDropDown: function(data) {
        var tenantDropDown = $('#loginTenant');
        tenantDropDown.empty();
        tenantDropDown.append($('<option>', { value: -1, text: 'Admin' }));
        $.each(data, function(i, tenant) {
            tenantDropDown.append($('<option>', { value: tenant.tenantId, text: tenant.displayName }));
        });
    },

    logoutUser: function() {
        $('#logoutUserButton').on('click', function() {
            $.ajax({
                 type: 'DELETE',
                 url: DEMO.login.api + '/logout',
                 dataType: 'json',
                 success: function(data) {
                    $('#loggedInTenantName').text('');
                    $('#loggedInUserName').text('');
                    console.log('Successfully logged out user: ' + JSON.stringify(data, null, '\t'));
                 },
                 error: function(error) {
                    console.log('Error: ' + JSON.stringify(error, null, '\t'));
                 }
            });
        });
    }
};