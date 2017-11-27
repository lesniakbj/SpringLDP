var DEMO = DEMO || {};

DEMO.admin = {
    api: '/admin',

    registerFunctions: function() {
        this.viewEndpoints();
        this.viewAllTenants();
        this.viewTenantById();
        this.addNewTenant();
        this.removeTenant();
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

    viewTenantById: function() {
        $('#tenantById').on('click', function() {
            DEMO.admin.populateDemoArea(null, 'getTenantId');
        });
    },

    addNewTenant: function() {
        $('#newTenant').on('click', function() {
            DEMO.admin.populateDemoArea(null, 'getNewTenantDetails');
        });
    },

    removeTenant: function() {
        $('#removeTenant').on('click', function() {
            DEMO.admin.populateDemoArea(null, 'removeTenantDetails');
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
                var headerElm = '<div class="adminTenantHeader ">';
                headerElm += '<span class="adminDisplayName left underline">Display Name:</span>';
                headerElm += '<span class="adminDatabaseName underline">Database Name:</span>';
                headerElm += '<span class="adminDatabaseUsername right">Database Username:</span>';
                headerElm += '</div>';
                $('#adminActionResponse').append(headerElm);

                if(data.constructor === Array) {
                    $.each(data, function(i, tenant) {
                        var dataElm = '<div class="adminTenantDisplay">';
                        dataElm += '<span class="adminDisplayName left">' + tenant.displayName + '</span>';
                        dataElm += '<span class="adminDatabaseName">' + tenant.databaseName + '</span>';
                        dataElm += '<span class="adminDatabaseUsername right">' + tenant.databaseUserName + '</span>';
                        dataElm += '</div>';
                        $('#adminActionResponse').append(dataElm);
                    });
                } else {
                    var dataElm = '<div class="adminTenantDisplay">';
                    dataElm += '<span class="adminDisplayName left">' + data.displayName + '</span>';
                    dataElm += '<span class="adminDatabaseName">' + data.databaseName + '</span>';
                    dataElm += '<span class="adminDatabaseUsername right">' + data.databaseUserName + '</span>';
                    dataElm += '</div>';
                    $('#adminActionResponse').append(dataElm);
                }
                break;
            case 'getTenantId':
                DEMO.admin.getTenantId();
                break;
            case 'getNewTenantDetails':
                DEMO.admin.getNewTenantDetails();
                break;
            case 'removeTenantDetails':
                DEMO.admin.removeTenantDetails();
                break;
        }
    },

    getTenantId: function() {
        $('#adminActionResponse').append('<label>TenantId: </label><input type="text" id="tenantIdForSearch"><input type="button" value="Get Tenant" class="inputButton" id="getTenantById">');
        $('#getTenantById').on('click', function() {
            $.ajax({
                type: 'GET',
                url: DEMO.admin.api + '/tenant/' + $('#tenantIdForSearch').val(),
                dataType: 'json',
                success: function(data) {
                    DEMO.admin.populateDemoArea(data, 'tenants');
                },
                error: function(error) { console.log('Error: ' + JSON.stringify(error, null, '\t')); }
            });
        });
    },

    getNewTenantDetails: function() {
        $('#adminActionResponse').append('<div><label>Name: </label><input type="text" id="newTenantName"></div>');
        $('#adminActionResponse').append('<div><label>Display Name: </label><input type="text" id="newTenantDisplayName"></div>');
        $('#adminActionResponse').append('<div><label>Database Name: </label><input type="text" id="newTenantDatabaseName"></div>');
        $('#adminActionResponse').append('<div><label>Database Username: </label><input type="text" id="newTenantDatabaseUsername"></div>');
        $('#adminActionResponse').append('<div><label>Database Password: </label><input type="text" id="newTenantDatabasePassword"></div>');
        $('#adminActionResponse').append('<div><input type="button" value="Create Tenant" id="createNewTenantButton"></div>');
        $('#createNewTenantButton').on('click', function() {
            var newTenant = {
                tenantName: $('#newTenantName').val(),
                displayName: $('#newTenantDisplayName').val(),
                databaseName: $('#newTenantDatabaseName').val(),
                databaseUserName: $('#newTenantDatabaseUsername').val(),
                databasePassword: $('#newTenantDatabasePassword').val()
            };

            $.ajax({
                type: 'POST',
                url: DEMO.admin.api + '/tenant/add',
                dataType: 'json',
                data: JSON.stringify(newTenant),
                success: function(data) {
                    DEMO.admin.populateDemoArea(data, 'tenants');
                    $.ajax({
                        type: 'GET',
                        url: DEMO.admin.api + '/tenant',
                        dataType: 'json',
                        success: function(data) {
                            DEMO.login.populateTenantDropDown(data);
                        },
                        error: function(error) { console.log('Error: ' + JSON.stringify(error, null, '\t')); }
                    });
                },
                error: function(error) { console.log('Error: ' + JSON.stringify(error, null, '\t')); }
            });
        });
    },

    removeTenantDetails: function() {
        $.ajax({
            type: 'GET',
            url: DEMO.admin.api + '/tenant',
            dataType: 'json',
            success: function(data) {
                DEMO.admin.populateRemoveTenant(data);
            },
            error: function(error) { console.log('Error: ' + JSON.stringify(error, null, '\t')); }
        });
    },

    populateRemoveTenant: function(data) {
        $.each(data, function(i, tenant) {
            $('#adminActionResponse').append('<div><input type="button" class="deleteTenantButton" value="' + tenant.displayName + '" id="newTenantName' + tenant.tenantId + '" data-tenant="' + tenant.tenantId + '"></div>');
        });

        $('.deleteTenantButton').on('click', function(event) {
            var delTenant = {
                tenantId: $(event.target).data('tenant')
            };

            $.ajax({
                type: 'DELETE',
                url: DEMO.admin.api + '/tenant/remove',
                dataType: 'json',
                data: JSON.stringify(delTenant),
                success: function(data) {
                    DEMO.admin.populateDemoArea(data, 'tenants');
                },
                error: function(error) { console.log('Error: ' + JSON.stringify(error, null, '\t')); }
            });
        });
    }
};