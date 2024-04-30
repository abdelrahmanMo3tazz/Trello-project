package service;

import model.Card;
import java.util.List;

public interface CardService {
    Card createCard(Card card);
    Card getCardById(Long id);
    List<Card> getAllCards();
    // Add other methods as needed
}
