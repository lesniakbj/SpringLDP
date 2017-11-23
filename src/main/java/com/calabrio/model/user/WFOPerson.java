package com.calabrio.model.user;

import javax.persistence.*;
import java.util.List;

/**
 * (c) Copyright 2017 Calabrio, Inc.
 * All Rights Reserved. www.calabrio.com LICENSED MATERIALS
 * Property of Calabrio, Inc., Minnesota, USA
 * <p>
 * No part of this publication may be reproduced, stored or transmitted,
 * in any form or by any means (electronic, mechanical, photocopying,
 * recording or otherwise) without prior written permission from Calabrio Software.
 * <p>
 * <p>
 * Created by Brendan.Lesniak on 11/17/2017.
 */
@Entity
@Table(name = "WFOPerson")
public class WFOPerson {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tenantId")
    private Integer tenantId;

    @Column(name = "acdId", length = 256)
    private String acdId;

    @Column(name = "firstName", length = 60)
    private String firstName;

    @Column(name = "lastName", length = 60)
    private String lastName;

    @Column(name = "email", length = 254)
    private String email;

    @ElementCollection(targetClass = WFOPermission.class, fetch = FetchType.EAGER)
    @JoinTable(name = "PersonPermissions", joinColumns = @JoinColumn(name = "personId"))
    @Column(name = "permissionId", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private List<WFOPermission> userPermissions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getAcdId() {
        return acdId;
    }

    public void setAcdId(String acdId) {
        this.acdId = acdId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<WFOPermission> getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(List<WFOPermission> userPermissions) {
        this.userPermissions = userPermissions;
    }

    public boolean hasPermission(WFOPermission permission) {
        return userPermissions.contains(permission);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WFOPerson wfoPerson = (WFOPerson) o;

        if (id != null ? !id.equals(wfoPerson.id) : wfoPerson.id != null) return false;
        if (tenantId != null ? !tenantId.equals(wfoPerson.tenantId) : wfoPerson.tenantId != null) return false;
        return acdId != null ? acdId.equals(wfoPerson.acdId) : wfoPerson.acdId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tenantId != null ? tenantId.hashCode() : 0);
        result = 31 * result + (acdId != null ? acdId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WFOPerson{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", acdId='" + acdId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
