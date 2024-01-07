package com.example.login.model;

import com.example.login.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;


@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "admin")
public class Admin extends User {

}
