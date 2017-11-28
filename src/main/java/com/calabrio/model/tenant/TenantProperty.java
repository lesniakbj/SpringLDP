package com.calabrio.model.tenant;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TenantProperties")
public class TenantProperty {
    @Column(name = "id", nullable =  false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose(serialize = false)
    private Integer id;

    @Column(name = "tenantId", nullable =  false)
    @Expose(serialize = false)
    private Integer tenantId;

    @Id
    @Column(name = "keyName", length = 100, nullable = false)
    @Expose
    private String keyName;

    @Column(name = "value", length = Integer.MAX_VALUE, nullable = false)
    @Expose
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
