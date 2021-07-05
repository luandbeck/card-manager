package com.example.cardmanager.usecases;

import com.example.cardmanager.repository.CardsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeleteCard {

    @Autowired
    private CardsRepository cardsRepository;

    public void execute(final String email) {

        log.info("Deletando usuário");
        this.cardsRepository.delete(email);
    }
}
