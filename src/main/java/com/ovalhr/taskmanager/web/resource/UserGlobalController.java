package com.ovalhr.taskmanager.web.resource;

import com.ovalhr.taskmanager.entity.Authority;
import com.ovalhr.taskmanager.entity.User;
import com.ovalhr.taskmanager.mapper.JwtResponse;
import com.ovalhr.taskmanager.mapper.LoginRequest;
import com.ovalhr.taskmanager.enumeration.Role;
import com.ovalhr.taskmanager.repositories.AuthorityRepository;
import com.ovalhr.taskmanager.repositories.UserRepository;
import com.ovalhr.taskmanager.security.JwtUtils;
import com.ovalhr.taskmanager.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rana on 8/27/21.
 */
@RestController
@RequestMapping("/api/global/user/")
public class UserGlobalController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse response;
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user != null) {
            response = authenticateUserCredential(loginRequest);
        } else {
            response =  new JwtResponse(false);
        }

        return ResponseEntity.ok(response);
    }

    private JwtResponse authenticateUserCredential(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = new ArrayList<String>();
        for (GrantedAuthority grantedAuthority: userDetails.getAuthorities()) {
            roles.add(grantedAuthority.getAuthority());
        }
        return new JwtResponse(jwt, userDetails.getUsername(), "");
    }
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createUser() {
        User user = userRepository.findByUsername("admin");
        if (user == null) {
            user = new User();
            user.setUsername("admin");
            user.setFullName("admin");
            user.setPlainPassword("admin");
            user.setEnabled(true);
            userRepository.save(user);

            Authority authority = new Authority();
            authority.setAuthority(Role.ROLE_ADMIN.toString());
            authority.setUsername("admin");
            authorityRepository.save(authority);
            return "Success";
        }else
        {
            return "User Admin Already Exist";
        }

    }
}
