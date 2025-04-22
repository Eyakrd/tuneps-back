package pfe.project.back.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static pfe.project.back.Entity.Permission.*;

@RequiredArgsConstructor

public enum Role {
    USER(Collections.emptySet()),
    ADMIN(Set.of(
            ADMIN_READ,
            ADMIN_UPDATE,
            ADMIN_DELETE,
            ADMIN_CREATE,
            EMPLOYEE_CREATE,
            EMPLOYEE_UPDATE,
            EMPLOYEE_DELETE,
            EMPLOYEE_READ

    )),
    EMPLOYEE(Set.of(
            EMPLOYEE_CREATE,
            EMPLOYEE_UPDATE,
            EMPLOYEE_DELETE,
            EMPLOYEE_READ
    ));

    @Getter
    private final Set<Permission> permissions;
    public List<SimpleGrantedAuthority> getAuthorities(){
       var authorities= getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
       authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
       return authorities;
    }

}
