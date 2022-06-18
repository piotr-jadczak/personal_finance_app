package pl.simple.finance.apiserver.controller.helper;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.simple.finance.apiserver.security.UserDetailsImp;

public class SecurityContextHandler {

    public static long getUserId() {
        UserDetailsImp userDetails = (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if(userDetails == null) {
            throw new SecurityException("No user in security context");
        }
        return userDetails.getId();
    }
}
