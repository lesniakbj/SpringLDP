package com.calabrio.model.tenant;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Tenant")
public class Tenant {
    @Id
    @Column(name = "tenantId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Integer tenantId;

    @Column(name = "tenantName", length =  50, nullable = false)
    @Expose
    private String tenantName;

    @Column(name = "displayName", length =  50, nullable = false)
    @Expose
    private String displayName;

    @Column(name = "databaseName", length =  125, nullable = false)
    @Expose
    private String databaseName;

    @Column(name = "databaseUserName", length =  100)
    @Expose
    private String databaseUserName;

    @Column(name = "databasePassword", length =  200)
    @Expose
    private String databasePassword;

    @OneToOne
    @JoinColumn(name = "databaseInstanceId")
    private DatabaseInstance databaseInstance;

    @Column(name = "tenantStateId")
    @Enumerated(EnumType.STRING)
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

    @Override
    public String toString() {
        return "Tenant{" +
                "tenantId=" + tenantId +
                ", tenantName='" + tenantName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", databaseName='" + databaseName + '\'' +
                ", databaseUserName='" + databaseUserName + '\'' +
                ", databasePassword='" + databasePassword + '\'' +
                ", databaseInstance=" + databaseInstance +
                ", tenantState=" + tenantState +
                '}';
    }
}
