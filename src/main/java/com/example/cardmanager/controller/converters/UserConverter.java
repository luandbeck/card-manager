package com.example.cardmanager.controller.converters;

import com.example.cardmanager.dto.CreateCardsDTO;
import com.example.cardmanager.entities.Card;
import com.example.cardmanager.entities.User;
import org.springframework.util.Assert;

import java.util.Collections;

public class UserConverter {

    private UserConverter() {
    }

    public static User toEntity(final CreateCardsDTO dto) {
        Assert.notNull(dto, "CreateCardsDTO cannot be null");

        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .phone(dto.getPhone())
                .cards(Collections.singletonList(Card.builder()
                        .cardNumber(dto.getCardNumber())
                        .build()))
                .build();
    }
}

