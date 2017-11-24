CREATE TABLE Tenant (
	tenantId INT IDENTITY(1,1) NOT NULL,
	tenantName NVARCHAR(50) NOT NULL,
	displayName NVARCHAR(50) NOT NULL,
	databaseName NVARCHAR(125) NOT NULL,
	databaseUserName NVARCHAR(100) NULL,
	databasePassword NVARCHAR(200) NULL,
	databaseInstanceId INT NULL,
	tenantStateId INT NOT NULL,
	PRIMARY KEY (tenantId),
	FOREIGN KEY (databaseInstanceId) REFERENCES [dbo].[DatabaseInstance]([databaseInstanceId]),
	FOREIGN KEY (tenantStateId) REFERENCES [dbo].[TenantState]([tenantStateId])
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

CREATE TABLE TenantState (
	tenantStateId INT IDENTITY(1,1) NOT NULL,
	name NVARCHAR(50) NOT NULL,
	PRIMARY KEY (tenantStateId)
);

SET IDENTITY_INSERT TenantState ON;
GO

INSERT INTO TenantState (tenantStateId, name) VALUES
(0, 'Activated'), (1, 'Suspended'), (2, 'Deactivated'),
(3, 'New'), (4, 'Failed Setup'), (5, 'Mainenance'),
(6, 'Deleted'), (7, 'Failed Deleltion');

SET IDENTITY_INSERT TenantState OFF;
GO

INSERT INTO Tenant (tenantName, displayName, databaseName, databaseUserName, databasePassword, tenantStateId)
VALUES ('Tenant1', 'Tenant1', 'Tenant1', 'dbTenant1', '', 0), ('Tenant2', 'Tenant2', 'Tenant2', 'dbTenant2', '', 0)

INSERT INTO DatabaseInstance (hostName, instanceName, port, master, username, password)
VALUES ('localhost', '', 1433, 1, 'sa', '')

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

INSERT INTO PersonPermissions VALUES (1, 0)
INSERT INTO PersonPermissions VALUES (2, 'VIEW_TELEPHONY')

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

INSERT INTO Server VALUES ('ACD_SERVER', 'localhost')
INSERT INTO TelephonyGroup VALUES ('TestTG1', 'UNIFIED_CM', NULL, 1)
INSERT INTO SignalingGroup VALUES ('TestSG1', 1, 1, null)