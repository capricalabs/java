package controllers;

import java.io.*;
import java.util.ArrayList;
import model.*;

/**
 * 
 */

/**
 * @author dan
 *
 */
/*
 * RULES  
 * 8 Deck shoe
 * Player can hit or stand until they bust
 * Dealer hits on soft 17
 * No splitting
 * No double down
 * No surrender
 * No insurance
 * Dealer pays 3:2 on normal win
 * Dealer pays 2:1 on blackjack
 * Official rules: http://en.wikipedia.org/wiki/Blackjack#Rules_of_play_at_casinos
 * GUI example: http://www.bccs210.com/pluginfile.php/270/mod_assignment/intro/Screen%20shot%202012-02-08%20at%2012.08.07%20AM.png
 */

/*
 *Extra Credit:
 *
 * Allow for more than one player (1-6) configurable - 5 points
 * Use images for the cards - 1 point
 * Draw the cards so they work when the screen is resized - 5 points
 * Double Down - 2 points
 * Surrender - 2 points
 */
public class Game {

  /**
   * Where we store our shoe of 8 decks
   */
  public Shoe shoe;
  
  public int round=1;

  public views.BlackJackGUI oView;
  
  public model.BlackJack oModel;
  
  public model.Util util;
  
  public ArrayList<Gambler> players = new ArrayList<Gambler>();
  
  private Dealer dealer;

  private int cPlayer;
  
  public boolean exportHands=false;
  
  public File exportFile;

  public Game(){
    this.oModel = new model.BlackJack(this);
    this.shoe = new Shoe(this);
    this.util = new model.Util();
    this.oView = new views.BlackJackGUI(this);
  }


  public void initGame(String playerCnt){
    this.initGamblers(Integer.parseInt(playerCnt));

  }
  
  /**
   * Load gamblers based on count
   */
  private void initGamblers(int count){
    
    for(int i=1; i<count+1;i++){
      this.addGambler("Sucker " + i, i);
    }

    this.dealer=new Dealer(this);
    this.cPlayer=0;
  }

  /**
   * Player requests additional draw
   */
  public boolean hit(){
    return this.getCurrentPlayer().hit();
  }

  /**
   * point to next player or dealer
   */
  private void nextPlayerOrDealer() 
      throws FileNotFoundException{
    if(this.cPlayer+1==this.players.size()){
      this.evalHands();
    } else {
      this.cPlayer++;
      this.oView.nextPlayer();
    }
  }

  /**
   * Have dealer draw out and compare user hands, set scores and modify bankrolls
   */
  private void evalHands() 
      throws FileNotFoundException {

    this.dealer.execute();
    
    for (Gambler gambler : this.players ) {
      gambler.evaluate();
    }
    
    this.oView.drawDealerCards();
    this.oView.displayRoundSummary(this.oModel.renderResults(this.players));
    this.nextRound();
  }

  /**
   * reset player objects and go to next round
   */
  public void nextRound(){
    for (Gambler gambler : this.players )
      gambler.reset();
    
    this.dealer.reset();
    this.cPlayer=0;
    this.round++;
    this.oView.nextRound();
  }

  /**
   * player is staying w/hand
   */
  public void stay() 
      throws FileNotFoundException{
    this.nextPlayerOrDealer();
  }

  /**
   * go to next player
   */
  public void next() 
      throws FileNotFoundException{

    this.nextPlayerOrDealer();
  }

  /**
   * player requests half of bet back, foregoes round
   */
  public void surrender() 
      throws Exception{

    this.getCurrentPlayer().surrender();
    this.next();
  }

  /**
   * retrieve current player/gambler object
   */
  public Gambler getCurrentPlayer(){
    return this.players.get(this.cPlayer);
  }

  /**
   * double current bet, draw one additional card
   */
  public void doubleDown() 
      throws FileNotFoundException {
    this.getCurrentPlayer().doubleDown();
    this.oView.drawPlayerCards();
    this.next();
  }

  /**
   * retrieve dealer object
   */
  public Dealer getDealer(){
    return this.dealer;
  }
  
  /**
   * initialize round with two cards to each player and dealer
   */
  public void deal() 
      throws Exception{
    java.util.Iterator<Gambler> it = this.players.iterator();
    
    //draw card for each player
    while(it.hasNext()){
      Gambler player = it.next();
      player.draw();
    }
    
    //draw card for dealer
    this.dealer.draw();
    
    if(this.dealer.isBlackJack()){
      this.evalHands();
      this.nextRound();
    }
  }

  /**
   * Create new gambler
   */
  public void addGambler(String name, int position){
    this.players.add(new Gambler(name,position, this));
  }
  
  /**
   * Load shoe with csv file object
   */
  public void loadDeck(File csv) 
      throws FileNotFoundException{

    this.shoe.loadDeck(csv);
  }
  
  /**
   * establish export stream file object
   */
  public void loadOutput(File csv){
    this.exportFile=csv;
  }

  /**
   * turn hand exporting on or off
   */
  public void toggleExport(String s){
    if(s=="active"){
      this.exportHands=true;
    } else {
      this.exportHands=false;
    }
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    Game game = new Game();
  }
}
