package com.calabrio.service.auth;

import com.calabrio.model.user.WFOPerson;
import com.calabrio.security.principal.UserPrincipal;
import com.calabrio.service.AbstractService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl extends AbstractService implements AuthenticationService {
    @Override
    public void applyAuthentication(WFOPerson person) {
        UserPrincipal principal = new UserPrincipal();
        principal.setPerson(person);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void removeAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
