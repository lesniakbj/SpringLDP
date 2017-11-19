package com.calabrio.model.tenant;

import javax.persistence.*;

public class Tenant {
    @Id
    @Column(name = "tenantId", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tenantId;

    @Column(name = "tenantName", length =  50, nullable = false)
    private String tenantName;

    @Column(name = "displayName", length =  50, nullable = false)
    private String displayName;

    @Column(name = "databaseName", length =  125, nullable = false)
    private String databaseName;

    @Column(name = "databaseUserName", length =  100)
    private String databaseUserName;

    @Column(name = "databasePassword", length =  200)
    private String databasePassword;

    @OneToOne
    @JoinColumn(name = "databaseInstanceId")
    private DatabaseInstance databaseInstance;

    @OneToOne
    @JoinColumn(name = "tenantStateId")
    private TenantState tenantState;

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseUserName() {
        return databaseUserName;
    }

    public void setDatabaseUserName(String databaseUserName) {
        this.databaseUserName = databaseUserName;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public DatabaseInstance getDatabaseInstance() {
        return databaseInstance;
    }

    public void setDatabaseInstance(DatabaseInstance databaseInstance) {
        this.databaseInstance = databaseInstance;
    }

    public TenantState getTenantState() {
        return tenantState;
    }

    public void setTenantState(TenantState tenantState) {
        this.tenantState = tenantState;
    }
}
