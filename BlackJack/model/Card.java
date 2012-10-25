package model;
/**
 * Programming Assignment #1 
 * @author Dan Netzer
 */

/**
 * Represents a card constrained to two sets of enums, representing the 
 * suit and value. 
 */
public class Card implements Comparable<Card>
{
  public enum Suit 
  {
    C,
    D,
    H,
    S
  }
  
  public enum Value
  {
    ACE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK,
    QUEEN,
    KING
  }
  
  private Suit suit;
  private Value value;
  
  /**
   * construct a card given a suit and value
   */
    public Card(Suit suit, Value value) {
      this.suit=suit;
      this.value=value;
    }
 
  /**
     * @return the suit
     */
    public Suit getSuit()
    {
      return suit;
    }

  /**
     * @return the value
     */
    public Value getValue()
    {
      return value;
    }
    
  /**
   * create a combined value based on enum ordinal of suit + value, off-setting suit by 
   * a factor of 100 to maintain correct order
   */
    private int netValue(){
      return  this.suit.ordinal()*100 + this.value.ordinal();
    }


  @Override
    public int compareTo(Card card)
    {
      return this.netValue() - card.netValue();
    }
  
  /**
   * return string for testing purposes
   */
  public String toString(){
    return "["+this.value+":"+this.suit+"]";
  }
  
  public static void main(String[] args){
    Card c = new Card(Suit.C, Value.ACE);
    System.out.println(c.toString());
  }
}