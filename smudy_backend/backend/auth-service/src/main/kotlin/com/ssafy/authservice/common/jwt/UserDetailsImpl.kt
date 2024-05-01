package com.ssafy.authservice.common.jwt

import com.ssafy.authservice.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(
    private val user: User
) : UserDetails{

    override fun getUsername(): String {
        return user.userInternalId
    }

    override fun getPassword(): String {
        return user.userSnsId
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(user.userRole.key))
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}