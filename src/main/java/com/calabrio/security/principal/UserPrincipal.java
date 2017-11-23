package com.calabrio.security.principal;

import com.calabrio.model.user.WFOPermission;
import com.calabrio.model.user.WFOPerson;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal {
    private WFOPerson person;

    public WFOPerson getPerson() {
        return person;
    }

    public void setPerson(WFOPerson person) {
        this.person = person;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(WFOPermission permission : person.getUserPermissions()) {
            authorities.add(new SimpleGrantedAuthority(permission.toString()));
        }
        return authorities;
    }
}
