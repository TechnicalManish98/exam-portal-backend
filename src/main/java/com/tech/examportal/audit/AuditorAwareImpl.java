package com.tech.examportal.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;


public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Manish");
        // Use below commented code when will use Spring Security.
    }
}
//------------------ Use below code for spring security --------------------------

/*
 * class AuditorAwareImpl implements AuditorAware<String> {
 * 
 * 
 * public Optional<String> getCurrentAuditor() {
 * 
 * Authentication authentication =
 * SecurityContextHolder.getContext().getAuthentication();
 * 
 * if (authentication == null || !authentication.isAuthenticated()) { return
 * null; }
 * 
 * User principal = (User) authentication.getPrincipal(); String user =
 * principal.getFirstName(); return Optional.ofNullable(user.toString());
 * 
 * 
 * 
 * } }
 */