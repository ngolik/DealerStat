package by.golik.dealerstat.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Nikita Golik
 */
public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_TRADER, ROLE_ANON;

    @Override
    public String getAuthority() {
        return name();
    }

}
