package com.ssafy.userservice.db.postgre.entity

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "users", schema = "public")
class User(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        var userId: Int,

        @Column(name = "user_internal_id", unique = true, nullable = false)
        var userInternalId: UUID,

        @Column(name = "user_sns_id", length = 50, nullable = false)
        var userSnsId: String,

        @Column(name = "user_name", length = 50, nullable = false)
        var userName: String,

        @Column(name = "user_image", length = 200, nullable = false)
        var userImage: String,

        @Column(name = "user_exp", nullable = false)
        var userExp: Int = 0,

        @Column(name = "user_created_at", nullable = false)
        @Temporal(TemporalType.DATE)
        var createdAt: Date,

        @Column(name = "user_deleted_at", nullable = true)
        @Temporal(TemporalType.DATE)
        var deletedAt: Date? = null,

        @Column(name = "user_is_activate", nullable = false)
        var isActive: Boolean = true

) : UserDetails {

        override fun getUsername(): String {
                // 사용자 이름을 반환
                return userInternalId.toString()
        }

        override fun getPassword(): String {
                return ""
        }

        override fun getAuthorities(): Collection<GrantedAuthority> {
                return listOf(SimpleGrantedAuthority("ROLE_USER"))
        }

        override fun isAccountNonExpired(): Boolean {
                // 계정의 만료 여부를 반환
                return true
        }

        override fun isAccountNonLocked(): Boolean {
                // 계정의 잠김 여부를 반환
                return true
        }

        override fun isCredentialsNonExpired(): Boolean {
                // 자격 증명의 만료 여부를 반환
                return true
        }

        override fun isEnabled(): Boolean {
                // 계정의 활성화 여부를 반환
                return isActive
        }

        fun userExpUp() {
                userExp += 100
        }
}