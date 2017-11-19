package com.calabrio.model.tenant;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class TenantState {
    @Id
    @Column(name = "tenantStateId", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tenantStateId;

    @Id
    @Column(name = "tenantStateId", length =  50, nullable = false)
    private String name;
}
