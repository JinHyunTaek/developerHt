package com.programming1.developerHt.service;

import com.programming1.developerHt.code.StatusCode;
import com.programming1.developerHt.dto.CreateDeveloper;
import com.programming1.developerHt.dto.DeveloperDetailDto;
import com.programming1.developerHt.dto.DeveloperDto;
import com.programming1.developerHt.entity.Developer;
import com.programming1.developerHt.repository.DeveloperRepository;
import com.programming1.developerHt.repository.RetiredDeveloperRepository;
import com.programming1.developerHt.type.DeveloperLevel;
import com.programming1.developerHt.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.programming1.developerHt.code.StatusCode.EMPLOYED;
import static com.programming1.developerHt.type.DeveloperLevel.JUNIOR;
import static com.programming1.developerHt.type.DeveloperLevel.SENIOR;
import static com.programming1.developerHt.type.DeveloperSkillType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeveloperServiceTest {
    //가상의 Mock으로 2개의 repository를 test에 등록
    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;

    //가짜(가상의 Mock)를 주입
    @InjectMocks
    private DeveloperService developerService;

    private final Developer defaultDeveloper = Developer.builder()
            .developerLevel(SENIOR)
            .developerSkillType(FRONT_END)
            .experienceYears(12)
            .statusCode(EMPLOYED)
            .name("aaa")
            .age(22)
            .build();

    private final CreateDeveloper.Request defaultCreateRequest =
            CreateDeveloper.Request.builder()
            .developerLevel(SENIOR)
                .developerSkillType(FRONT_END)
                .experienceYears(13)
                .name("bbb")
                .memberId("memberId3")
                .age(32)
                .build();

    @Test
    public void testSomething() {
        //given
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(defaultDeveloper));

        //when
        DeveloperDetailDto developerDetail = developerService.getDeveloperDetail("memberId");

        //then
        assertEquals(SENIOR,developerDetail.getDeveloperLevel());
        assertEquals(FRONT_END,developerDetail.getDeveloperSkillType());
        assertEquals(12,developerDetail.getExperienceYears());
    }

    @Test
    void createDeveloperTest_success(){
        //given(테스트 대상이 A 상태에서 출발)

        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());
        ArgumentCaptor<Developer> captor =
                ArgumentCaptor.forClass(Developer.class);

        //when(어떤 상태 변화를 가했을 때)
        CreateDeveloper.Response developer = developerService.createDeveloper(defaultCreateRequest);

        //then(기대하는 상태로 완료되어야 한다)
        verify(developerRepository, times(1))
                .save(captor.capture());
        Developer savedDeveloper = captor.getValue(); //캡쳐된 데이터(DB로 가는 값) 확인
        assertEquals(SENIOR,savedDeveloper.getDeveloperLevel());
        assertEquals(FRONT_END,savedDeveloper.getDeveloperSkillType());
        assertEquals(13, savedDeveloper.getExperienceYears());
    }
}
