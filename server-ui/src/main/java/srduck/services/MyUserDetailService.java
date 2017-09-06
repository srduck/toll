package srduck.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import srduck.dao.Role;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        srduck.dao.User user = userService.findByLogin(login);
        List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }


    private List<GrantedAuthority> buildUserAuthority(Set<Role> userRoles) {

        Set<GrantedAuthority> setAuthorities = new HashSet<GrantedAuthority>();
        // Build user's authorities
        for (Role userRole : userRoles) {
            //Prifix "ROLE_" if in html ${#httpServletRequest.isUserInRole('SOMEROLE')}
            setAuthorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getName()));
        }
        return new ArrayList<GrantedAuthority>(setAuthorities);
    }


}
