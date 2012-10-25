package model;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import model.Card.*;
import controllers.Game;

/**
 * 
 */

/**
 * @author dan
 *
 */
public class Shoe {
  
  int index;
  
  private Game game;
  
  private Map<String, Suit> suitInput = new HashMap<String, Suit>();
  private Map<String, Value> valueInput = new HashMap<String, Value>();

  /**
   * Where we store our 8 decks of cards
   */
  private ArrayList<Card> shoeDeck = new ArrayList<Card>();

  public Shoe(Game game){
    this.game=game;
    this.init();
  }
  
  /**
   * pull x number of decks into shoe
   */
  private void init(){
    this.index=0;
    
    for(int i=0; i<this.game.oModel.SHOE_DECKS; i++) {
      Deck d = new Deck();
      this.shoeDeck.addAll(d.getDeck());
    }
  }
  
  /**
   * shuffle shoe
   */
  public void shuffle(){
    this.init();
  }
  
  /**
   * load csv file into shoe
   */
  public void loadDeck(File csv) 
      throws FileNotFoundException{

    //connect import strings with enum values using hash map
    this.loadTestDeckMap();

    ArrayList<Card> shoe = new ArrayList<Card>();
    
    Scanner ss = new Scanner(csv);

        while(ss.hasNext()){
          Scanner line = new Scanner(ss.nextLine());
          line.useDelimiter(", *");
          //first token is value, second suit
          this.loadCard(line.next(),line.next(), shoe);
        }
        
        if(this.validateShoe(shoe)){
          this.shoeDeck = shoe;
          this.index=0;
          JOptionPane.showMessageDialog(null, this.cardsAvailable()+" test cards have been loaded into shoe!");
        }
  }
  
  /**
   * map text dump to card enum
   */
  private void loadTestDeckMap(){
    this.suitInput.put("Spade",Card.Suit.S);
    this.suitInput.put("Diamond",Card.Suit.D);
    this.suitInput.put("Heart",Card.Suit.H);
    this.suitInput.put("Club",Card.Suit.C);
    
    this.valueInput.put("Ace",Card.Value.ACE);
    this.valueInput.put("Two",Card.Value.TWO);
    this.valueInput.put("Three",Card.Value.THREE);
    this.valueInput.put("Four",Card.Value.FOUR);
    this.valueInput.put("Five",Card.Value.FIVE);
    this.valueInput.put("Six",Card.Value.SIX);
    this.valueInput.put("Seven",Card.Value.SEVEN);
    this.valueInput.put("Eight",Card.Value.EIGHT);
    this.valueInput.put("Nine",Card.Value.NINE);
    this.valueInput.put("Ten",Card.Value.TEN);
    this.valueInput.put("Jack",Card.Value.JACK);
    this.valueInput.put("Queen",Card.Value.QUEEN);
    this.valueInput.put("King",Card.Value.KING);
  }
  
  /**
   * confirm shoe has 416 cards, with correct numver suits and values
   */
  private boolean validateShoe(ArrayList<Card> shoe){
    
    Map<Suit, Integer> valsuit = new HashMap<Suit, Integer>();
    Map<Value, Integer> valvalue = new HashMap<Value, Integer>();
    
    valsuit.put(Card.Suit.S,0);
    valsuit.put(Card.Suit.D,0);
    valsuit.put(Card.Suit.H,0);
    valsuit.put(Card.Suit.C,0);
    
    valvalue.put(Card.Value.ACE,0);
    valvalue.put(Card.Value.TWO,0);
    valvalue.put(Card.Value.THREE,0);
    valvalue.put(Card.Value.FOUR,0);
    valvalue.put(Card.Value.FIVE,0);
    valvalue.put(Card.Value.SIX,0);
    valvalue.put(Card.Value.SEVEN,0);
    valvalue.put(Card.Value.EIGHT,0);
    valvalue.put(Card.Value.NINE,0);
    valvalue.put(Card.Value.TEN,0);
    valvalue.put(Card.Value.JACK,0);
    valvalue.put(Card.Value.QUEEN,0);
    valvalue.put(Card.Value.KING,0);
    
    for (Card c : shoe ) {
      valsuit.put(c.getSuit(),valsuit.get(c.getSuit())+1);
      valvalue.put(c.getValue(),valvalue.get(c.getValue())+1);
    }
    
    for(Suit suit : valsuit.keySet()){
      Integer count = valsuit.get(suit);
      if(count != 104){
        JOptionPane.showMessageDialog(null, "You are off on "+suit+" suit with "+count);
        return false;
      }
    }

    
    for(Value val : valvalue.keySet()){
      Integer count = valvalue.get(val);
      if(count !=32){
        JOptionPane.showMessageDialog(null, "You are off on "+val+" cards with "+count);
        return false;
      }
    }

    return true;
  }

  private void loadCard(String value, String suit, ArrayList<Card> shoe){
    shoe.add(new Card(this.suitInput.get(suit), this.valueInput.get(value)));
  }

  /**
   * Draws a single card
   * @return The card drawn out of the shoe or null 
   */
  public Card draw()
  {
    //if we've reached the final card in the shoe, return null
    if (this.index==this.shoeDeck.size()-1) {
      JOptionPane.showMessageDialog(null, "You are out of cards! Game over.");
      System.exit(0); 
    }

    Card c = this.shoeDeck.get(this.index);
    this.index++;
    return c;
  }

  
  /**
   * @return string for debugging purposes 
   */
  public String toString(){
    return this.shoeDeck.toString();
  }

  public int cardsAvailable(){
    return this.shoeDeck.size()-this.index;
  }
}
