package com.programming1.developerHt.dto;

import com.programming1.developerHt.exception.DeveloperErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperErrorResponse {
    private DeveloperErrorCode errorCode;
    private String errorMessage;
}
