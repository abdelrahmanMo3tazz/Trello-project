package com.example.trello.controller;

import com.example.trello.model.Card;
import com.example.trello.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    /**
     * Endpoint to create a new card in a specific list.
     *
     * @param listId       the ID of the list where the card will be added.
     * @param title        the title of the new card.
     * @param description  the description of the new card.
     * @return the created card.
     */
    @PostMapping("/")
    public ResponseEntity<Card> createCard(@RequestParam Long listId,
                                           @RequestParam String title,
                                           @RequestParam String description) {
        Card newCard = cardService.createCard(listId, title, description);
        return ResponseEntity.ok(newCard);
    }

    /**
     * Endpoint to assign a card to a user.
     *
     * @param cardId the ID of the card to assign.
     * @param userId the ID of the user to whom the card will be assigned.
     * @return the updated card.
     */
    @PutMapping("/{cardId}/assign/{userId}")
    public ResponseEntity<Card> assignCard(@PathVariable Long cardId, @PathVariable Long userId) {
        Card updatedCard = cardService.assignCard(cardId, userId);
        return ResponseEntity.ok(updatedCard);
    }

    /**
     * Endpoint to move a card from one list to another.
     *
     * @param cardId    the ID of the card to move.
     * @param newListId the ID of the new list where the card will be moved.
     * @return the updated card.
     */
    @PutMapping("/{cardId}/move/{newListId}")
    public ResponseEntity<Card> moveCard(@PathVariable Long cardId, @PathVariable Long newListId) {
        Card movedCard = cardService.moveCard(cardId, newListId);
        return ResponseEntity.ok(movedCard);
    }

    /**
     * Endpoint to update the description of a card.
     *
     * @param cardId       the ID of the card.
     * @param newDescription the new description for the card.
     * @return the updated card.
     */
    @PutMapping("/{cardId}/description")
    public ResponseEntity<Card> updateCardDescription(@PathVariable Long cardId, 
                                                      @RequestBody String newDescription) {
        Card updatedCard = cardService.updateDescription(cardId, newDescription);
        return ResponseEntity.ok(updatedCard);
    }
}
