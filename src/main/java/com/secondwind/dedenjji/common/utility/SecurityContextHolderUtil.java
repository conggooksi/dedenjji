package com.secondwind.dedenjji.common.utility;

import com.secondwind.dedenjji.common.enumerate.Authority;
import com.secondwind.dedenjji.common.exception.ApiException;
import com.secondwind.dedenjji.common.exception.CustomAuthException;
import com.secondwind.dedenjji.common.exception.code.AuthErrorCode;
import com.secondwind.dedenjji.common.result.JsonResultData;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextHolderUtil {

    public static Long getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null || authentication.getName().equals("anonymousUser")) {
            throw new CustomAuthException(JsonResultData
                    .failResultBuilder()
                    .errorMessage(AuthErrorCode.SECURITY_CONTEXT_NOT_FOUND.getMessage())
                    .errorCode(AuthErrorCode.SECURITY_CONTEXT_NOT_FOUND.getCode())
                    .build());
        }

        return Long.parseLong(authentication.getName());
    }

    public static Authority getCurrentAuthority() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return Authority.ROLE_ADMIN;
        } else {
            return Authority.ROLE_USER;
        }
    }

    public static void checkRoleAdmin() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Authority authority;
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            authority = Authority.ROLE_ADMIN;
        } else {
            authority =  Authority.ROLE_USER;
        }

        if (authority.equals(Authority.ROLE_USER)) {
            throw ApiException.builder()
                    .errorMessage(AuthErrorCode.NOT_PERMITTED.getMessage())
                    .errorCode(AuthErrorCode.NOT_PERMITTED.getCode())
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }

    }
}
