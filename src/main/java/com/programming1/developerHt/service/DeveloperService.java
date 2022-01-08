package com.programming1.developerHt.service;

import com.programming1.developerHt.dto.CreateDeveloper;
import com.programming1.developerHt.entity.Developer;
import com.programming1.developerHt.exception.DeveloperErrorCode;
import com.programming1.developerHt.exception.DeveloperException;
import com.programming1.developerHt.repository.DeveloperRepository;
import com.programming1.developerHt.type.DeveloperLevel;
import com.programming1.developerHt.type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeveloperService {
    private final DeveloperRepository developerRepository;

    //DB 테이블에 INSERT할 내용들
    //트랜잭션 특성(ACID) 1. Atomic 2. Consistency 3. Isolation 4. Durability(지속성)
    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request){
        Developer developer= Developer.builder() //Developer Class의 @Builder
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .age(request.getAge())
                .name(request.getName())
                .build();

        developerRepository.save(developer); //DB에 저장
        return CreateDeveloper.Response.fromEntity(developer);
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request){
        //business validation
        DeveloperLevel developerLevel = request.getDeveloperLevel();
        Integer experienceYears = request.getExperienceYears();
        if(developerLevel ==DeveloperLevel.SENIOR
        && experienceYears <10){
            throw new RuntimeException(DeveloperErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATHCED.getMessage());
        }
        if(developerLevel ==DeveloperLevel.MIDDLE
        &&(experienceYears <4)|| experienceYears >10){
            throw new RuntimeException(DeveloperErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATHCED.getMessage());
        }
        if(developerLevel==DeveloperLevel.JUNIOR&&experienceYears>10){
            throw new RuntimeException(DeveloperErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATHCED.getMessage());
        }

        //request의 memberId가 있는 developer가 있을 때
        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer -> {
                    throw new DeveloperException(DeveloperErrorCode.DUPLICATED_MEMBER_ID);
                }));
    }
}
