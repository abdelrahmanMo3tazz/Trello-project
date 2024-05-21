package com.example.trello.service;

import com.example.trello.model.Card;
import com.example.trello.model.dbList;
import com.example.trello.model.User; 
import com.example.trello.repository.CardRepository;
import com.example.trello.repository.ListRepository;
import com.example.trello.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ListRepository listRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new card within a specified list.
     *
     * @param listId       the list identifier where the card will be added.
     * @param title        the title of the new card.
     * @param description  the description of the new card.
     * @return the saved card entity.
     */
    @Transactional
    public Card createCard(Long listId, String title, String description) {
        dbList list = listRepository.findById(listId)
                .orElseThrow(() -> new IllegalArgumentException("List not found with id: " + listId));

        Card card = new Card();
        card.setTitle(title);
        card.setDescription(description);
        card.setList(list);
        return cardRepository.save(card);
    }

    /**
     * Assigns a card to a user.
     *
     * @param cardId the card identifier.
     * @param userId the user identifier to whom the card will be assigned.
     * @return the updated card entity.
     */
    @Transactional
    public Card assignCard(Long cardId, Long userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found with id: " + cardId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        card.setAssignee(user);
        return cardRepository.save(card);
    }

    /**
     * Moves a card from one list to another.
     *
     * @param cardId the card identifier.
     * @param newListId the new list identifier.
     * @return the updated card entity.
     */
    @Transactional
    public Card moveCard(Long cardId, Long newListId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found with id: " + cardId));
        dbList newList = listRepository.findById(newListId)
                .orElseThrow(() -> new IllegalArgumentException("List not found with id: " + newListId));

        card.setList(newList);
        return cardRepository.save(card);
    }
    @Autowired
    private NotificationService notificationService;
    /**
     * Updates the description of a card.
     *
     * @param cardId the card identifier.
     * @param newDescription the new description to set.
     * @return the updated card entity.
     */
    @Transactional
    public Card updateDescription(Long cardId, String newDescription) {
        Card card = cardRepository.findById(cardId).orElseThrow();
        card.setDescription(newDescription);
        Card updatedCard = cardRepository.save(card);

        // Send notification
        notificationService.notifyCardUpdate("Description updated for card " + cardId, cardId);

        return updatedCard;
    }
}
