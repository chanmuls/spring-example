package com.chanmul.domain.user.type;

import com.chanmul.core.type.EnumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountRole implements EnumType {
    ADMIN("대표"),
    STEP("직원"),
    MEMBER("회원"),
    GUEST("손님"),
    ;

    private final String description;

    @Override
    public String getValue() {
        return this.name();
    }
}
