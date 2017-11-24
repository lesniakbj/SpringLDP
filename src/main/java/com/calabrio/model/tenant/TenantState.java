package com.calabrio.model.tenant;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name = "TenantState")
public class TenantState {
    @Id
    @Column(name = "tenantStateId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Integer tenantStateId;

    @Column(name = "name", length =  50, nullable = false)
    @Expose
    private String name;

    public Integer getTenantStateId() {
        return tenantStateId;
    }

    public void setTenantStateId(Integer tenantStateId) {
        this.tenantStateId = tenantStateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
