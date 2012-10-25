package model;

import java.util.*;
import controllers.Game;


/**
 * @author dan
 *
 */

public class Dealer {
  
  public Game game;
  
  private int count=0;
  
  private boolean busted=false;
  
  private boolean blackjack=false;

  private ArrayList<Card> hand = new ArrayList<Card>();

  public Dealer(Game game){
    this.game=game;
  }
  
  /**
   * return combined card values
   */
  public int getCount(){
    return this.count;
  }

  /**
   * next round
   */
  public void reset(){
    this.count=0;
    this.hand = new ArrayList<Card>();
    this.blackjack=false;
    this.setBusted(false);
  }

  /**
   * check boolean
   */
  public boolean isBusted(){
    return this.busted;
  }
  
  /**
   * Check boolean
   */
  public boolean isBlackJack(){
    return this.blackjack;
  }
  
  /**
   * draw until at least hard 17 or busted
   */
  public void execute(){
    while(this.count<17 || this.game.oModel.isSoft17(this.hand)){
      this.draw();
    }
  }

  /**
   * set as busted
   */
  public void setBusted(boolean b){
    this.busted=b;
  }
  
  /**
   * set as blackjack
   */
  public void setBlackJack(boolean b){
    this.blackjack=b;
  }

  /**
   * draw card, compile count, check for blackjack
   */
  public void draw(){
    this.hand.add(this.game.shoe.draw());
    this.count=this.game.oModel.compileCount(this.hand);
    this.game.oModel.checkDealerBust(this.count);
    
    if(this.game.oModel.isBlackJack(this.hand,this.count)){
      this.setBlackJack(true);
    }
  }
  
  /**
   * return dealer hand
   */
  public ArrayList<Card> getHand(){
    return this.hand;
  }
}