package com.programming1.developerHt.service;

import com.programming1.developerHt.code.StatusCode;
import com.programming1.developerHt.dto.CreateDeveloper;
import com.programming1.developerHt.dto.DeveloperDetailDto;
import com.programming1.developerHt.dto.DeveloperDto;
import com.programming1.developerHt.dto.EditDeveloper;
import com.programming1.developerHt.entity.Developer;
import com.programming1.developerHt.entity.RetiredDeveloper;
import com.programming1.developerHt.exception.DeveloperErrorCode;
import com.programming1.developerHt.exception.DeveloperException;
import com.programming1.developerHt.repository.DeveloperRepository;
import com.programming1.developerHt.repository.RetiredDeveloperRepository;
import com.programming1.developerHt.type.DeveloperLevel;
import com.programming1.developerHt.type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .memberId(request.getMemberId())
                .ststusCode(StatusCode.EMPLOYED)
                .name(request.getName())
                .age(request.getAge())
                .build();

        developerRepository.save(developer); //DB에 저장
        return CreateDeveloper.Response.fromEntity(developer);
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request){
        //business validation
        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

        //request의 memberId가 있는 developer가 있을 때
        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer -> {
                    throw new DeveloperException(DeveloperErrorCode.DUPLICATED_MEMBER_ID);
                }));
    }

    private void validateDeveloperRequest(DeveloperLevel developerLevel, Integer experienceYears) {
        if(developerLevel ==DeveloperLevel.SENIOR
        && experienceYears <10){
            throw new RuntimeException(DeveloperErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATHCED.getMessage());
        }
        if(developerLevel ==DeveloperLevel.MIDDLE
        &&(experienceYears <4)|| experienceYears >10){
            throw new RuntimeException(DeveloperErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATHCED.getMessage());
        }
        if(developerLevel ==DeveloperLevel.JUNIOR&& experienceYears >10){
            throw new RuntimeException(DeveloperErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATHCED.getMessage());
        }
    }

    public List<DeveloperDto> getAllDevelopers() {
        //map: entity를 dto로 변경
        return developerRepository.findAll()
                .stream().map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    public DeveloperDetailDto getDeveloperDetail(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .map(DeveloperDetailDto::fromEntity)
                .orElseThrow(()->new DeveloperException(DeveloperErrorCode.NO_DEVELOPER));
        //DetailDto의 값을 가져오지 못할 경우 에러코드
    }

    @Transactional
    public DeveloperDetailDto editDeveloper(String memberId, EditDeveloper.Request request) {
        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

        Developer developer = developerRepository.findByMemberId(memberId).orElseThrow(
                () -> new DeveloperException(DeveloperErrorCode.NO_DEVELOPER)
        );

        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkillType(request.getDeveloperSkillType());
        developer.setExperienceYears(request.getExperienceYears());

        return DeveloperDetailDto.fromEntity(developer);
    }

    private void validateEditDeveloperRequest(EditDeveloper.Request request, String memberId) {
        validateDeveloperLevel(request.getDeveloperLevel(),request.getExperienceYears());
        developerRepository.findByMemberId(memberId).orElseThrow(
                () -> new DeveloperException(DeveloperErrorCode.NO_DEVELOPER)
        );
    }

    private void validateDeveloperLevel(DeveloperLevel developerLevel, Integer experienceYears) {
        if(developerLevel ==DeveloperLevel.SENIOR
                && experienceYears <10){
            throw new RuntimeException(DeveloperErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATHCED.getMessage());
        }
        if(developerLevel ==DeveloperLevel.MIDDLE
                &&(experienceYears <4)|| experienceYears >10){
            throw new RuntimeException(DeveloperErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATHCED.getMessage());
        }
        if(developerLevel ==DeveloperLevel.JUNIOR&& experienceYears >10){
            throw new RuntimeException(DeveloperErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATHCED.getMessage());
        }
    }

    @Transactional
    public DeveloperDetailDto deleteDeveloper(String memberId) {
        //1. EMPLOYED -> RETIRED
        //DB에 있는지부터 확인
        Developer developer = developerRepository.findByMemberId(memberId)
                .orElseThrow(() ->
                        new DeveloperException(DeveloperErrorCode.NO_DEVELOPER));
        developer.setStatusCode(StatusCode.RETIRED);
        //2. save into RetiredDeveloper
        RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
                .memberId(memberId)
                .name(developer.getName())
                .build();
        RetiredDeveloperRepository
    }
}
