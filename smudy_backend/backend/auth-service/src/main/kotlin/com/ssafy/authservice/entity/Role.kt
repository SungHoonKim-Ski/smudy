package com.ssafy.authservice.entity

enum class Role(val key: String, val title: String) {
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "일반 사용자")
}