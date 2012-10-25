package model;

import java.util.*;


/**
 * Deck class for storing and emulating a deck of cards.
 */
public class Deck 
{ 
  private static int NUM_CARDS = 52;

  /**
   * Where we initialize cards and reload activeDeck from
   */
  private ArrayList<ArrayList<Card>> masterDeck = new ArrayList<ArrayList<Card>>();

  /**
   * What we use for public methods
   */
  private ArrayList<Card> activeDeck = new ArrayList<Card>();

  /**
   * index the deck pointer so we can increment through the deck and manipulate as needed
   */
  private int index;

  /**
   * Construct deck w/52 cards and shuffle w/ENUM class data which represents suits & values
   */
  public Deck(){

    for (Card.Suit suit : Card.Suit.values()) {
      ArrayList<Card> suitedCards = new ArrayList<Card>();
      for (Card.Value value : Card.Value.values()) {
        suitedCards.add(new Card(suit, value));
      }
      
      //swap the ace onto front so it's always sorted by default
      Collections.rotate(suitedCards, 1);
      //add set of cards to this ordinaled index of masterDeck
      this.masterDeck.add(suitedCards);
    }

    Collections.reverse(this.masterDeck);
    initDeck();
    this.shuffle();
  }
  
  /**
   * clear active deck, reload w/existing arrayLists, reset index pointer
   */
  private void initDeck(){
    this.activeDeck.clear();
    
    //reload active deck with available suited lists. 
    for (ArrayList<Card> list : this.masterDeck )
      this.activeDeck.addAll(list);

    //setting card drawing pointer @0 since we have a fresh deck
    this.index=0;
  }

  /**
   * Shuffles a deck of cards so that all 52 cards are randomly distributed.
   */
  public void shuffle()
  { 
    this.initDeck();
    Collections.shuffle(this.activeDeck);
  }


  /**
   * Draws a single card
   * @return The card drawn out of the deck or null 
   */
  public Card draw()
  {
    //if we've reached the final card in the deck, return null
    if (this.index==53) {
      return null;
    }

    Card c = this.activeDeck.get(this.index);
    this.index++;
    return c;
  }
  
  /**
   * Sort the cards in the order A-K Spades, A-K Hearts, A-K Diamonds, A-K Clubs
   */
  public void sort()
  {
    this.initDeck();
  }
  
  /**
   * return int - cards available 
   */
  public int cardsAvailable()
  {
    return NUM_CARDS-this.index;
  }

  /**
   * @return string for debugging purposes 
   */
  public String toString(){
    return this.activeDeck.toString();
  }
  
  public ArrayList<Card> getDeck(){
    return this.activeDeck;
  }
}