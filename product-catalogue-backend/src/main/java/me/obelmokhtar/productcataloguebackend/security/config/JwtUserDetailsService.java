package me.obelmokhtar.productcataloguebackend.security.config;

import me.obelmokhtar.productcataloguebackend.security.entities.Users;
import me.obelmokhtar.productcataloguebackend.security.services.UsersAccountService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

public class JwtUserDetailsService implements UserDetailsService {
    private UsersAccountService usersAccountService;

    public JwtUserDetailsService(UsersAccountService usersAccountService) {
        this.usersAccountService = usersAccountService;
    }

   
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("JwtUserDetailsService.loadUserByUsername()");
       
        Users authenticatedUser = usersAccountService.loadUserByUsername(username);
        System.out.println("authenticationUser from DB: "+authenticatedUser);
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
       
        authenticatedUser.getRoles().forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));
       
        return new User(authenticatedUser.getUsername(), authenticatedUser.getPassword(), grantedAuthorities);
    }
}
