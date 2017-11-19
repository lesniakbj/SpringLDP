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