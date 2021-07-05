package com.example.cardmanager.exceptions.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalErrorData {

    private String code;
    private String message;
    private Map<String, Object> arguments;

}
