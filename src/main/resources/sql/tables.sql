-- COMMON TABLES
CREATE TABLE Tenant (
	tenantId INT IDENTITY(1,1) NOT NULL,
	tenantName NVARCHAR(50) NOT NULL,
	displayName NVARCHAR(50) NOT NULL,
	databaseName NVARCHAR(125) NOT NULL,
	databaseUserName NVARCHAR(100) NULL,
	databasePassword NVARCHAR(200) NULL,
	databaseInstanceId INT NULL,
	tenantStateId NVARCHAR(MAX) NOT NULL,
	PRIMARY KEY (tenantId),
	FOREIGN KEY (databaseInstanceId) REFERENCES [dbo].[DatabaseInstance]([databaseInstanceId])
);

CREATE TABLE DatabaseInstance (
	databaseInstanceId INT IDENTITY(1,1) NOT NULL,
	hostName NVARCHAR(200) NOT NULL,
	instanceName NVARCHAR(16) NULL,
	port INT NULL,
	master BIT NOT NULL,
	username NVARCHAR(100) NULL,
	password NVARCHAR(200) NULL,
	PRIMARY KEY (databaseInstanceId)
);

CREATE TABLE WFOPerson (
	id INT IDENTITY(1, 1) NOT NULL,
	tenantId INT NOT NULL,
	acdId NVARCHAR(256) NULL,
	firstName NVARCHAR(60) NULL,
	lastName NVARCHAR(60) NULL,
	email NVARCHAR(254) NULL,
	password NVARCHAR(200) NULL,
	isServiceUser BIT NOT NULL DEFAULT(0),
	PRIMARY KEY (id)
);

CREATE TABLE PersonPermissions (
	personId INT NOT NULL,
	permissionId NVARCHAR(MAX) NOT NULL,
	FOREIGN KEY (personId) REFERENCES WFOPerson(id)
);

CREATE SCHEMA admin;

CREATE PROCEDURE [admin].[createTenantDatabase] (@databaseName NVARCHAR(255), @username NVARCHAR(255), @password NVARCHAR(255))
AS
BEGIN
	EXEC('USE master CREATE DATABASE ' + @databaseName);
	EXEC('USE master CREATE LOGIN ' + @username + ' WITH PASSWORD=N''' +  @password + '''');
	EXEC('USE ' + @databaseName + ' CREATE USER ' + @username + ' FOR LOGIN ' + @username + ' WITH DEFAULT_SCHEMA=[dbo]');
	EXEC('USE ' + @databaseName + ' ALTER ROLE db_owner ADD MEMBER ' + @username);
END

CREATE PROCEDURE [admin].[createTenantTables] (@databaseName NVARCHAR(255))
AS
BEGIN
	DECLARE @query NVARCHAR(MAX);
	SET @query = '
        CREATE TABLE WFOPerson (
            id INT IDENTITY(1, 1) NOT NULL,
            tenantId INT NOT NULL,
            acdId NVARCHAR(256) NULL,
            firstName NVARCHAR(60) NULL,
            lastName NVARCHAR(60) NULL,
            email NVARCHAR(254) NULL,
            password NVARCHAR(200) NULL,
            isServiceUser BIT NOT NULL DEFAULT(0),
            PRIMARY KEY (id)
        );

        CREATE TABLE TenantProperties (
            id INT IDENTITY(1,1) NOT NULL,
            tenantId INT NOT NULL,
            keyName NVARCHAR(100) NOT NULL,
            value NVARCHAR(MAX) NOT NULL,
            PRIMARY KEY (tenantId, keyName)
        );

        CREATE TABLE PersonPermissions (
            personId INT NOT NULL,
            permissionId NVARCHAR(MAX) NOT NULL,
            FOREIGN KEY (personId) REFERENCES WFOPerson(id)
        );

        CREATE TABLE Server (
            id INT IDENTITY(1,1) NOT NULL,
            serverType NVARCHAR(MAX) NOT NULL,
            ipHostName NVARCHAR(1000) NOT NULL,
            PRIMARY KEY (id)
        );

        CREATE TABLE TelephonyGroupServer (
            telephonyGroupId INT NOT NULL,
            serverFk INT NOT NULL,
            FOREIGN KEY (serverFk) REFERENCES Server(id)
        );

        CREATE TABLE RecordingGroupServer (
            recordingGroupId INT NOT NULL,
            serverFk INT NOT NULL,
            priority INT NULL,
            FOREIGN KEY (serverFk) REFERENCES Server(id)
        );

        CREATE TABLE SignalingGroupServer (
            signalingGroupId INT NOT NULL,
            serverFk INT NOT NULL,
            signalingAssociation INT NULL,
            priority INT NULL,
            FOREIGN KEY (serverFk) REFERENCES Server(id)
        );

        CREATE TABLE TelephonyGroup (
            id INT IDENTITY(1,1) NOT NULL,
            name NVARCHAR(255) NOT NULL,
            telephonyGroupTypeId NVARCHAR(MAX) NOT NULL,
            inclusionList NVARCHAR(MAX) NULL,
            acdServerId INT NOT NULL,
            PRIMARY KEY (id),
            FOREIGN KEY (acdServerId) REFERENCES Server(id)
        );

        CREATE TABLE SignalingGroup (
            id INT IDENTITY(1,1) NOT NULL,
            name NVARCHAR(255) NOT NULL,
            telephonyGroupId INT NULL,
            primarySignalingId INT NULL,
            backupSignalingId INT NULL,
            PRIMARY KEY (id),
            FOREIGN KEY (telephonyGroupId) REFERENCES TelephonyGroup(id),
            FOREIGN KEY (primarySignalingId) REFERENCES Server(id),
            FOREIGN KEY (backupSignalingId) REFERENCES Server(id)
        );

        CREATE TABLE RecordingGroup (
            id INT IDENTITY(1,1) NOT NULL,
            name NVARCHAR(255) NOT NULL,
            signalingGroupId INT NOT NULL,
            PRIMARY KEY (id),
            FOREIGN KEY (signalingGroupId) REFERENCES SignalingGroup(id)
        );
    ';

	EXEC('USE ' + @databaseName + ' ' + @query);
END

CREATE PROCEDURE [admin].[createTenantUsers] (@databaseName NVARCHAR(255), @tenantId NVARCHAR(MAX))
AS
BEGIN
	DECLARE @query NVARCHAR(MAX);
	SET @query = '
		INSERT INTO WFOPerson VALUES
		(' + @tenantId + ', ''RandomACD'', ''Service'', ''User'', ''service@user.com'', ''12345'', 1),
		(' + @tenantId + ', ''RandomACD'', ''Tenant'', ''Admin'', ''tenant@admin.com'', ''12345'', 0)

		DECLARE @tenantAdmin INT;
		SELECT @tenantAdmin = id FROM WFOPerson WHERE email = ''tenant@admin.com'';

		INSERT INTO PersonPermissions VALUES
		(@tenantAdmin, ''ADMIN_TENANT''),
		(@tenantAdmin, ''ADMIN_TELEPHONY''),
		(@tenantAdmin, ''VIEW_TELEPHONY'')
	';

	EXEC('USE ' + @databaseName + ' ' + @query);
END

INSERT INTO Tenant VALUES ('Tenant1', 'Tenant1', 'Tenant1', 'dbTenant1', '', 0), ('Tenant2', 'Tenant2', 'Tenant2', 'dbTenant2', '', 0)

INSERT INTO DatabaseInstance (hostName, instanceName, port, master, username, password)
VALUES ('localhost', '', 1433, 1, 'sa', '')

INSERT INTO WFOPerson VALUES
(-1, null, 'Sys', 'Admin', 'sys@admin.com', '12345', 0)

INSERT INTO PersonPermissions VALUES
(1, 'ADMIN_SYSTEM')


-- TENANT TABLES
CREATE TABLE WFOPerson (
	id INT IDENTITY(1, 1) NOT NULL,
	tenantId INT NOT NULL,
	acdId NVARCHAR(256) NULL,
	firstName NVARCHAR(60) NULL,
	lastName NVARCHAR(60) NULL,
	email NVARCHAR(254) NULL,
	password NVARCHAR(200) NULL,
	isServiceUser BIT NOT NULL DEFAULT(0),
	PRIMARY KEY (id)
);

CREATE TABLE TenantProperties (
	id INT IDENTITY(1,1) NOT NULL,
	tenantId INT NOT NULL,
	keyName NVARCHAR(100) NOT NULL,
	value NVARCHAR(MAX) NOT NULL,
	PRIMARY KEY (tenantId, keyName)
);

CREATE TABLE PersonPermissions (
	personId INT NOT NULL,
	permissionId NVARCHAR(MAX) NOT NULL,
	FOREIGN KEY (personId) REFERENCES WFOPerson(id)
);

CREATE TABLE Server (
	id INT IDENTITY(1,1) NOT NULL,
	serverType NVARCHAR(MAX) NOT NULL,
	ipHostName NVARCHAR(1000) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE TelephonyGroupServer (
	telephonyGroupId INT NOT NULL,
	serverFk INT NOT NULL,
	FOREIGN KEY (serverFk) REFERENCES Server(id)
);

CREATE TABLE RecordingGroupServer (
	recordingGroupId INT NOT NULL,
	serverFk INT NOT NULL,
	priority INT NULL,
	FOREIGN KEY (serverFk) REFERENCES Server(id)
);

CREATE TABLE SignalingGroupServer (
	signalingGroupId INT NOT NULL,
	serverFk INT NOT NULL,
	signalingAssociation INT NULL,
	priority INT NULL,
	FOREIGN KEY (serverFk) REFERENCES Server(id)
);

CREATE TABLE TelephonyGroup (
	id INT IDENTITY(1,1) NOT NULL,
	name NVARCHAR(255) NOT NULL,
	telephonyGroupTypeId NVARCHAR(MAX) NOT NULL,
	inclusionList NVARCHAR(MAX) NULL,
	acdServerId INT NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY acdServerId REFERENCES Server(serverId)
);

CREATE TABLE SignalingGroup (
	id INT IDENTITY(1,1) NOT NULL,
	name NVARCHAR(255) NOT NULL,
	telephonyGroupId INT NULL,
	primarySignalingId INT NULL,
	backupSignalingId INT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (telephonyGroupId) REFERENCES TelephonyGroup(id),
	FOREIGN KEY (primarySignalingId) REFERENCES Server(id),
	FOREIGN KEY (backupSignalingId) REFERENCES Server(id)
);

CREATE TABLE RecordingGroup (
	id INT IDENTITY(1,1) NOT NULL,
	name NVARCHAR(255) NOT NULL,
	signalingGroupId INT NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (signalingGroupId) REFERENCES SignalingGroup(id)
);

-- First Tenant Persons
INSERT INTO WFOPerson VALUES
(1, 'RandomACD', 'Service', 'User', 'service@user.com', '12345', 1),
(1, 'RandomACD', 'Tenant', 'Admin', 'tenant@admin.com', '12345', 0),
(1, 'RandomACD', 'Super', 'Visor', 'super@visor.com', '12345', 0),
(1, 'RandomACD', 'Random', 'Agent', 'random@agent.com', '12345', 0)

INSERT INTO PersonPermissions VALUES
(4, 'ADMIN_TENANT'),
(4, 'ADMIN_TELEPHONY'),
(4, 'VIEW_TELEPHONY'),
(5, 'ADMIN_TELEPHONY'),
(5, 'VIEW_TELEPHONY'),
(6, 'VIEW_TELEPHONY')

INSERT INTO Server VALUES ('ACD_SERVER', 'localhost')
INSERT INTO TelephonyGroup VALUES ('TestTG1', 'UNIFIED_CM', NULL, 1)
INSERT INTO SignalingGroup VALUES ('TestSG1', 1, 1, null)
INSERT INTO SignalingGroupServer VALUES (1, 1, 0, 0)
INSERT INTO RecordingGroup VALUES ('TestRG1', 1)
INSERT INTO RecordingGroupServer VALUES (1, 4, 0)