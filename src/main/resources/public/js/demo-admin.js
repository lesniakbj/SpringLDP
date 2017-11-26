var DEMO = DEMO || {};

DEMO.admin = {
    api: '/admin',

    registerFunctions: function() {
        this.viewEndpoints();
        this.viewAllTenants();
        this.viewTenantById();
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
            case 'tenant':
                $('#adminActionResponse').append('<p>' + 'Name: ' + data.displayName + '</p>');
                break;
            case 'getTenantId':
                $('#adminActionResponse').append('<label>TenantId: </label><input type="text" id="tenantIdForSearch"><input type="button" value="Get Tenant" class="inputButton" id="getTenantById">');
                $('#getTenantById').on('click', function() {
                    $.ajax({
                        type: 'GET',
                        url: DEMO.admin.api + '/tenant/' + $('#tenantIdForSearch').val(),
                        dataType: 'json',
                        success: function(data) {
                            DEMO.admin.populateDemoArea(data, 'tenant');
                        },
                        error: function(error) { console.log('Error: ' + JSON.stringify(error, null, '\t')); }
                    });
                });
                break;
        }
    }
};