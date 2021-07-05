package com.example.cardmanager.controller;

import com.example.cardmanager.dto.CreateCardsDTO;
import com.example.cardmanager.entities.User;
import com.example.cardmanager.usecases.CreateCard;
import com.example.cardmanager.usecases.DeleteCard;
import com.example.cardmanager.usecases.GetCard;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Slf4j
@RestController
@Validated
@RequestMapping(path = "/api/v1/cards")
@Api(value = "Gerenciamento de cartões", tags = {"Cards"})
public class CardsController {

    @Autowired
    private CreateCard createCard;

    @Autowired
    private GetCard getCard;

    @Autowired
    private DeleteCard deleteCard;

    @ApiOperation(
            value = "Consulta de usuário e cartões",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Registro encontrado"),
            @ApiResponse(code = 400, message = "Request inválida"),
            @ApiResponse(code = 404, message = "Registro não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno"),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@Valid @NotEmpty(message = "{email.empty}")
                                        @RequestParam(value = "email", required = false) final String email) {

        final User response = this.getCard.execute(email);

        log.info("Retornando usuário");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(
            value = "Criação de registro - Novo usuário ou inclusão de novos cartões para usuário existente",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cartão criado"),
            @ApiResponse(code = 400, message = "Request inválida"),
            @ApiResponse(code = 412, message = "Cartão já existe"),
            @ApiResponse(code = 500, message = "Erro interno"),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody final CreateCardsDTO createCardsDTO) {

        createCard.execute(createCardsDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(
            value = "Remoção de registros"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Para registros deletados com sucesso ou quando não é encontrado " +
                    "nenhum usuário"),
            @ApiResponse(code = 500, message = "Erro interno"),
    })
    @DeleteMapping()
    public ResponseEntity<Void> delete(@Valid @NotEmpty(message = "{email.empty}")
                                       @RequestParam(value = "email", required = false) final String email) {

        this.deleteCard.execute(email);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
