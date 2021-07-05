package com.example.cardmanager.exceptions.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PreconditionData implements Serializable {

    private static final long serialVersionUID = 5733138415551331080L;

    private Map<String, Serializable> fields;

}
