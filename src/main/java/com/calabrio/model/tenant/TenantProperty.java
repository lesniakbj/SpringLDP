package com.calabrio.model.tenant;

import javax.persistence.*;

@Entity
@Table(name = "TenantProperties")
public class TenantProperty {
    @Column(name = "id", nullable =  false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private transient Integer id;

    @Column(name = "tenantId", nullable =  false)
    private transient Integer tenantId;

    @Id
    @Column(name = "keyName", length = 100, nullable = false)
    private String keyName;

    @Column(name = "value", length = Integer.MAX_VALUE, nullable = false)
    private String value;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
