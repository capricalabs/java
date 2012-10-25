package model;

import java.util.*;
import controllers.Game;

/**
 * 
 */

/**
 * @author dan
 *
 */
public class BlackJack {
  
  private Game game;
  
  public final int SHOE_DECKS = 8;

  /**
   * Where we store our shoe of 8 decks
   */
  public final int STARTING_BANKROLL = 1000;
  
  private Map<Integer, Integer> cardVal = new HashMap<Integer, Integer>();
  /**
   * Where we store our shoe of 8 decks
   */
  public BlackJack(Game game){
    this.game=game;
    this.mapCardValues();
  }
  
  public int getCardValue(Integer ord){
    return cardVal.get(ord);
  }

  /**
   * map card enum ordinal with blackjack hand value
   */
  private void mapCardValues(){
    this.cardVal.put(0,11);
    this.cardVal.put(1,2);
    this.cardVal.put(2,3);
    this.cardVal.put(3,4);
    this.cardVal.put(4,5);
    this.cardVal.put(5,6);
    this.cardVal.put(6,7);
    this.cardVal.put(7,8);
    this.cardVal.put(8,9);
    this.cardVal.put(9,10);
    this.cardVal.put(10,10);
    this.cardVal.put(11,10);
    this.cardVal.put(12,10);
  }

  /**
   * has player exceeded 21?
   */
  public void checkPlayerBust(int count){
    if(count > 21){
      game.getCurrentPlayer().setBusted(true);
    }
  }
  
  /**
   * Check if dealer has busted and set unfortunate result
   */
  public void checkDealerBust(int count){
    if(count > 21){
      game.getDealer().setBusted(true);
    }
  }
  
  /**
   * determine count, the highest if soft
   */
  public int compileCount(ArrayList<Card> hand){
    int count=0;
    for (Card card : hand )
      count = count + this.getCardValue(card.getValue().ordinal());
    
    //addresses soft hands
    if(this.hasAce(hand) && count > 21){
      count-=10;
    }

    return count;
  }
  
  /**
   * probing for blackjack
   */
  public boolean isBlackJack(ArrayList<Card> hand, int count){
    if(this.hasAce(hand) && hand.size()==2 && count==21){
      return true;
    } else {
      return false;
    }
  }

  /**
   * ace present?
   */
  private boolean hasAce(ArrayList<Card> hand){
    
    for (Card card : hand ) {
      if(card.getValue()==Card.Value.ACE){
        return true;
      }
    }
      
    return false;
  }
  
  /**
   * Is soft 17?
   */
  public boolean isSoft17(ArrayList<Card> hand){
    int count=0;
    for (Card card : hand )
      count = count + this.getCardValue(card.getValue().ordinal());
    
    if(this.hasAce(hand) && count ==17){
      return true;
    } else {
      return false;
    }
  }

  /**
   * return summary of round
   */
  public String renderResults(ArrayList<Gambler> players){
    String msg="Round "+game.round+" Summary:\n\n";
    
    if(game.getDealer().isBusted()){
      msg+="The dealer busted with a "+game.getDealer().getCount()+".";
    } else if(game.getDealer().isBlackJack()) {
      msg+="Uh oh, the dealer got a blackjack!";
    } else {
      msg+="The dealer has a "+game.getDealer().getCount()+".";
    }
    
    msg+="\n\n";

    for (Gambler gambler : players ) {
      
      msg+=gambler.getName()+" ";
      if(gambler.surrendered()){
        msg+="surrendered to keep $"+gambler.getBet()*.5+"\n\n";
      } else if (gambler.isPush()){
        msg+="pushed with a "+gambler.getCount()+".\n\n";
      } else if (! gambler.isWinner()){
        if(gambler.isBusted()){
          msg+="busted by drawing to a "+gambler.getCount()+".\n";
        } else {
          msg+="lost to dealer with a "+gambler.getCount()+".\n";
        }

        msg+="Say goodbye to $"+gambler.getBet()+"\n\n";
      } else if(gambler.isBlackJack()) {
        msg+="got a Blackjack and won $"+gambler.getBet()*1.5+"\n\n";
      } else {
        msg+="won $"+gambler.getBet()+" with a "+gambler.getCount()+"\n\n";
      }
    }

    return msg;
  }
}