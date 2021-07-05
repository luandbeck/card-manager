package com.example.cardmanager.usecases;

import com.example.cardmanager.entities.User;
import com.example.cardmanager.exceptions.UserNotFoundException;
import com.example.cardmanager.repository.CardsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GetCard {

    @Autowired
    private CardsRepository cardsRepository;

    public User execute(final String email) {

        log.info("Buscando usuário");
        final User user = this.cardsRepository.get(email);

        if (user == null) {
            log.info("Usuário não encontrado");
            throw new UserNotFoundException();
        }

        log.info("Usuário encontrado");
        return user;
    }
}
