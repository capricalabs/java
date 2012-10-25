package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import controllers.Game;

/**
 * 
 */

/**
 * @author dan
 *
 */
public class Gambler {
  
  public String name;
  
  private Game game;
  
  private int position;

  private double bankroll;
  
  private int bet;
  
  private boolean busted=false;
  
  private boolean win=false;
  
  private boolean surrender=false;

  private boolean push=false;
  
  private boolean blackjack=false;

  private ArrayList<Card> hand = new ArrayList<Card>();
  
  private int count=0;

  /**
   * player hand
   */
  public ArrayList<Card> getHand(){
    return this.hand;
  }
  
  /**
   * initialize gambler object
   */
  public Gambler(String name, int position, Game game){
    this.name=name;
    this.game=game;
    this.position=position;
    this.bankroll=this.game.oModel.STARTING_BANKROLL;
  }

  /**
   * draw card, compile count, check for/set bust
   */
  public boolean hit(){
    this.hand.add(this.game.shoe.draw());
    this.count=this.game.oModel.compileCount(this.hand);
    this.game.oModel.checkPlayerBust(this.count);

    return this.isBusted();
  }
  
  /**
   * is winner? 
   */
  public boolean isWinner(){
    return this.win;
  }
  
  /**
   * is push?
   */
  public boolean isPush(){
    return this.push;
  }
  
  /**
   * surrender?
   */
  public void surrender(){
    this.surrender=true;
  }

  /**
   * get player position
   */
  public int getPosition(){
    return this.position;
  }

  /**
   * draw card, compile count, check for blackjack
   */
  public void draw(){
    this.hand.add(this.game.shoe.draw());
    this.count=this.game.oModel.compileCount(this.hand);
    
    if(this.game.oModel.isBlackJack(this.hand,this.count)){
      this.setBlackJack(true);
    }
  }
  
  /**
   * double down, double bet
   */
  public void doubleDown(){
    Card card = this.game.shoe.draw();
    this.hand.add(card);
    this.count=this.game.oModel.compileCount(this.hand);
    this.bet=this.bet*2;
    this.game.oModel.checkPlayerBust(this.count);
    
    JOptionPane.showMessageDialog(null, "You just pulled a "+card.toString()+"!");
  }
  
  /**
   * clean slate for next round
   */
  public void reset(){
    this.bet=0;
    this.setBusted(false);
    this.count=0;
    this.surrender=false;
    this.setBlackJack(false);
    this.hand = new ArrayList<Card>();
    this.setPush(false);
  }
  
  /**
   * set boolean
   */
  public void setBlackJack(boolean b){
    this.blackjack=b;
  }

  /**
   * pass bet for rnd
   */
  public void setBet(int amount){
    this.bet=amount;
  }
  
  /**
   * check boolean
   */
  public boolean isBlackJack(){
    return this.blackjack;
  }

  /**
   * retrieve bet
   */
  public int getBet(){
    return this.bet;
  }
  
  /**
   * busted?
   */
  public boolean isBusted(){
    return this.busted;
  }
  
  /**
   * set busted
   */
  public void setBusted(boolean b){
    this.busted=b;
  }
  
  /**
   * set push
   */
  public void setPush(boolean b){
    this.push=b;
  }
  
  /**
   * set bankroll amount
   */
  public void setBankroll(int amount){
    this.bankroll=amount;
  }
  
  /**
   * get player name
   */
  public String getName(){
    return this.name;
  }
  
  /**
   * surrendered
   */
  public boolean surrendered(){
    return this.surrender;
  }

  /**
   * retrieve bankroll
   */
  public String getBankroll(){
    return this.bankroll+"";
  }
  
  /**
   * get card count
   */
  public int getCount(){
    return this.count;
  }

  /**
   * compile result, net bankroll result
   */
  public void evaluate() 
      throws FileNotFoundException{

    if(! this.isBusted()) {
      //you got a blackjack!
      if(this.isBlackJack()){
        this.win=true;
        this.bankroll+=this.bet*1.5;
      //you don't bust, dealer busts, you win
      } else if(this.surrender){
        this.win=true;
        this.bankroll+=this.bet*.5;
      } else if(game.getDealer().isBusted()){
        this.win=true;
        this.bankroll+=this.bet;
      //neither bust, you have higher hand, you win
      } else if(this.getCount() > game.getDealer().getCount()) {
        this.win=true;
        this.bankroll+=this.bet;
      //neither bust, lower hand, you lose
      } else if(this.getCount() < game.getDealer().getCount()){
        this.win=false;
        this.bankroll-=this.bet;
      //all else push
      } else {
        this.push=true;
      }
    } else {
      this.bankroll-=this.bet;
    }
    
    if(game.exportHands)
      game.util.writeToFile(this, game.exportFile);
  } 
}