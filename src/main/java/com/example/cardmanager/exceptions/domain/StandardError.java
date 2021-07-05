package com.example.cardmanager.exceptions.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class StandardError {

    private final String path;
    private final Integer status;

    @JsonIgnore
    private final boolean printStack;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", locale = "pt-br", timezone =
            "Brazil/East")
    private final Timestamp timestamp;

    private final DefaultErrorResponse error;

}

