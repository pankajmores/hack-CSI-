package com.hackathon.platform.POJOS;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Document("Intern")
public class Intern implements UserDetails{

    @Id
    private  String  internId;
    private   String  email;
    private String  password;
    private String  name;

    @Field("role")
    private  Set<Roles> roles;
    private  List<Course>  courses;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
         return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name())) // Convert Enum to GrantedAuthority
                .collect(Collectors.toList());
    }
    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return  email;
    }
    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return password;
    }


}
