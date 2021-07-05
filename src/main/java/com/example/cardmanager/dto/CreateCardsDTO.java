package com.example.cardmanager.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel(value = "CreateCardsDTO", description = "Entidade utilizada no serviço de criação de registro")
public class CreateCardsDTO {

    @ApiModelProperty(value = "Nome", required = true)
    @NotNull(message = "{name.empty}")
    private String name;

    @ApiModelProperty(value = "Email", required = true)
    @NotNull(message = "{email.empty}")
    private String email;

    @ApiModelProperty(value = "Número de telefone - DDD e número", required = true)
    @NotNull(message = "{phone.empty}")
    private PhoneDTO phone;

    @ApiModelProperty(value = "Número do cartão", required = true)
    @NotNull(message = "{cardNumber.empty}")
    private Long cardNumber;
}
