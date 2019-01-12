package com.code10.security.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;

public class SiemUserDetails extends User {

    private Date lastPasswordReset;

    public SiemUserDetails(String username, String password, Date lastPasswordReset, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.lastPasswordReset = lastPasswordReset;
    }

    public Date getLastPasswordReset() {
        return lastPasswordReset;
    }

    public void setLastPasswordReset(Date lastPasswordReset) {
        this.lastPasswordReset = lastPasswordReset;
    }
}
