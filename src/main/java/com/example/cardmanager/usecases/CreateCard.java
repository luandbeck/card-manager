package com.example.cardmanager.usecases;

import com.example.cardmanager.controller.converters.UserConverter;
import com.example.cardmanager.dto.CreateCardsDTO;
import com.example.cardmanager.entities.User;
import com.example.cardmanager.exceptions.CardAlreadyCreateException;
import com.example.cardmanager.exceptions.UserNotFoundException;
import com.example.cardmanager.repository.CardsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateCard {

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private GetCard getCard;

    public void execute(final CreateCardsDTO request) {
        log.info("Buscando Usuário na base");
        final User user = UserConverter.toEntity(request);

        try {
            final User foundUser = this.getCard.execute(user.getEmail());
            this.addCard(foundUser, request.getCardNumber());
        } catch (final UserNotFoundException ex) {
            log.info("Usuário não existente, criando registro");
            this.cardsRepository.save(user);
        }
    }

    private void addCard(final User foundUser, final Long newCard) {
        log.info("Verificando se cartâo já foi cadastrado para usuário");
        final boolean cardExists = foundUser.getCards().stream().anyMatch(c -> c.getCardNumber().equals(newCard));

        if (cardExists) {
            log.error("Cartão já existe");
            throw new CardAlreadyCreateException();
        }

        log.info("Cartão não existente, criando registro");
        foundUser.addCardNumber(newCard);
        this.cardsRepository.update(foundUser);
    }


}
