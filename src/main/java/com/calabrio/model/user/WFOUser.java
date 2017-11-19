package com.calabrio.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "WFOUser")
public class WFOUser {
    @Id
    @Column(name = "personId", nullable = false)
    private Integer personId;

    @Column(name = "tenantId", nullable = false)
    private Integer tenantId;

    @Column(name = "tenantDbName", nullable = false)
    private String tenantDbName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "skillId")
    private String skillId;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "timeZone")
    private Integer timeZone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WFOUser wfoUser = (WFOUser) o;

        if (!tenantId.equals(wfoUser.tenantId)) return false;
        if (!personId.equals(wfoUser.personId)) return false;
        return email.equals(wfoUser.email);
    }

    @Override
    public int hashCode() {
        int result = tenantId.hashCode();
        result = 31 * result + personId.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
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

    public Integer getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(Integer timeZone) {
        this.timeZone = timeZone;
    }

    public String getTenantDbName() {
        return tenantDbName;
    }

    public void setTenantDbName(String tenantDbName) {
        this.tenantDbName = tenantDbName;
    }
}
