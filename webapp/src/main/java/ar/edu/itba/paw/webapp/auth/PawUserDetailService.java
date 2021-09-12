package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class PawUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        final ar.edu.itba.paw.models.User user = userService.findByEmail(email)
                                                    .orElseThrow(()-> new UsernameNotFoundException("No user for" + email));

        final Collection<GrantedAuthority> authorities = new ArrayList<>();


        if (user.getType() == UserType.Owner)
            authorities.add(new SimpleGrantedAuthority("ROLE_OWNER"));

        if (user.getType() == UserType.Renter)
            authorities.add(new SimpleGrantedAuthority("ROLE_RENTER"));

        return new User(email,user.getPassword(),authorities);
    }
}
