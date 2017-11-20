package com.calabrio.model.tenant;

import javax.persistence.*;

@Entity
@Table(name = "TenantState")
public class TenantState {
    @Id
    @Column(name = "tenantStateId", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tenantStateId;

    @Column(name = "name", length =  50, nullable = false)
    private String name;
}
