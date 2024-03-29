package com.programming1.developerHt.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeveloperSkillType {
    BACK_END("백앤드 개발자"),
    FRONT_END("프론트앤드 개발자"),
    FULL_STACK("풀스택 개발자");

    private final String description;

}
