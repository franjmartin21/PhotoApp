package com.photoapp.security;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.photoapp.dao.AccountDao;
import com.photoapp.domain.Account;
import com.photoapp.domain.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by fran on 22/08/14.
 */
@Service
public class AccountSecurityService implements UserDetailsService{

    @Autowired
    private AccountDao accountDao;

    public UserDetails loadUserByUsername(String username){
        // assuming that you have a User class that implements UserDetails
        /*final Account account = entityManager.createQuery("from Account where username = :username", Account.class)
                .setParameter("username", username)
                .getSingleResult();
*/
        final Account account = accountDao.findAccountByUsername(username);

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                 Collection<GrantedAuthority> authorityCollection= Collections2.transform(account.getAuthorities(),
                        new Function<Authority, GrantedAuthority>() {

                            @Override
                            public GrantedAuthority apply(final Authority authority) {
                                return new GrantedAuthority() {
                                    @Override
                                    public String getAuthority() {
                                        return authority.getAuthority().name();
                                    }
                                };
                            }
                        });
                return authorityCollection;
            }

            @Override
            public String getPassword() {
                return account.getPassword();
            }

            @Override
            public String getUsername() {
                return account.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return account.isEnabled();
            }
        };

    }
}
