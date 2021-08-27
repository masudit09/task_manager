package com.ovalhr.taskmanager;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.Date;
/**
 * Created by rana on 8/27/21.
 */
public class Util {

    final static Integer STRENGTH = 8;
    final static Integer MAX_DATE_SL = 7;
    final static PasswordEncoder ENCODER = new BCryptPasswordEncoder(STRENGTH);
    
    public static Long getCurrentUnixTime() {
        return getCurrentTimeMillis() / 1000L;
    }

    public static Long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }


    public static String splitCamelCase(String s) {
        return s.replaceAll(String.format("%s|%s|%s",
                "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
                "(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
    }

    public static PasswordEncoder getEncoder(){
        return ENCODER;
    }

    private static Object getCurrentPrincipal() {
        return getAuthentication() == null ? null : getAuthentication().getPrincipal();
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    
    public static String getCurrentUsername() {
        String username = null;
        if(getCurrentPrincipal() !=null){
            Object principal = getCurrentPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
        }
        if(username == "anonymousUser") {
            throw new RuntimeException("Token Not Valid");
        }
        return username;
    }

    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

}
