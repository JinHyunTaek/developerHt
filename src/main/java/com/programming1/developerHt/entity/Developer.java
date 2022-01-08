package com.programming1.developerHt.entity;

import com.programming1.developerHt.type.DeveloperLevel;
import com.programming1.developerHt.type.DeveloperSkillType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

//대부분의 dto, dtn은 밑의 constructor과 같이 사용
@Getter
@Setter
@Builder
@Entity
@EntityListeners({AuditingEntityListener.class})
@NoArgsConstructor
@AllArgsConstructor
public class Developer {
    //DB 테이블 create 시에 나오는 테이블 칼럼들 정의
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Enumerated(EnumType.STRING)
    private DeveloperLevel developerLevel;

    @Enumerated(EnumType.STRING)
    private DeveloperSkillType developerSkillType;

    private Integer experienceYears;
    private String memberId;
    private String name;
    private Integer age;

    @CreatedDate //생성 시점
    private LocalDateTime createdAt;

    @LastModifiedDate //최종 수정 시점
    private LocalDateTime updatedAt;

}
