package com.calabrio.service.auth;

import com.calabrio.model.user.WFOPerson;

public interface AuthenticationService {
    void applyAuthentication(WFOPerson person);
    void removeAuthentication();
}
