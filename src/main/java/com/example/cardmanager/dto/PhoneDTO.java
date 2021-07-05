package com.example.cardmanager.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "PhoneDTO", description = "Entidade com dados de telefone")
public class PhoneDTO {

    @ApiModelProperty(value = "DDD", required = true)
    private Long areaCode;

    @ApiModelProperty(value = "Número de telefone", required = true)
    private Long number;
}
