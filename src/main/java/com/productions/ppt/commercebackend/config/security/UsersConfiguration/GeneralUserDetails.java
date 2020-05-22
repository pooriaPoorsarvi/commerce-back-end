package com.productions.ppt.commercebackend.config.security.UsersConfiguration;

import com.productions.ppt.commercebackend.app.user.models.Role;
import com.productions.ppt.commercebackend.app.user.models.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
class GeneralUserDetails implements UserDetails {

  private boolean isEnabled;
  private boolean isCredentialsNonExpired;
  private boolean isAccountNonLocked;
  private boolean isAccountExpired;
  private String username;
  private String password;
  private List<GrantedAuthority> authorities;

  GeneralUserDetails(UserEntity userEntity) {
    this.password = userEntity.getPassword();
    this.username = userEntity.getEmail();
    this.isAccountExpired = userEntity.isAccountExpired();
    this.isAccountExpired = userEntity.isAccountExpired();
    this.isAccountNonLocked = userEntity.isAccountNonLocked();
    this.isCredentialsNonExpired = userEntity.isCredentialsNonExpired();
    this.isEnabled = userEntity.isEnabled();
    this.authorities =
        userEntity.getRoles().stream()
            .map(Role::getRole)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return !isAccountExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return isAccountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return isCredentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return isEnabled;
  }
}
