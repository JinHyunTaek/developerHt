package com.programming1.developerHt.type;
import lombok.*;

import javax.persistence.Entity;

@AllArgsConstructor
@Getter
public enum DeveloperLevel {
    NEW( "신입 개발자"),
    JUNIOR("주니어 개발자"),
    MIDDLE("중급 개발자"),
    SENIOR("시니어 개발자");

    private final String description;
}
