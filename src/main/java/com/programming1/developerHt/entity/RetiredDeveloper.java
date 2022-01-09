package com.programming1.developerHt.entity;

import com.programming1.developerHt.type.DeveloperLevel;
import com.programming1.developerHt.type.DeveloperSkillType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@Entity
@EntityListeners({AuditingEntityListener.class})
@NoArgsConstructor
@AllArgsConstructor
public class RetiredDeveloper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String memberId;
    private String name;

    @CreatedDate //생성 시점
    private LocalDateTime createdAt;

    @LastModifiedDate //최종 수정 시점
    private LocalDateTime updatedAt;

}
