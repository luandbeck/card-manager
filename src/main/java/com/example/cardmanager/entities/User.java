package com.example.cardmanager.entities;

import com.example.cardmanager.dto.PhoneDTO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "User", description = "Entidade que possui os dados do usuário e seus cartões")
public class User {

    private String name;
    private String email;
    private PhoneDTO phone;
    private List<Card> cards;

    public void addCardNumber(final Long card) {
        this.cards.add(Card.builder()
                .cardNumber(card)
                .build());
    }
}
