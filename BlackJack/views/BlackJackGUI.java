package views;

import javax.swing.*;
import model.Gambler;
import controllers.Game;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * @author dan
 *
 */
public class BlackJackGUI {

  public JFrame oFrame = new JFrame();

  private Game game;
  private boolean cardsDelt=false;
  private JComboBox player_count;

  private JLabel player = new JLabel("");
  private JLabel bankroll = new JLabel("");
  private JLabel count = new JLabel("");
  private JLabel cbet = new JLabel("");
  private JLabel shoeCnt = new JLabel("");
  private JLabel round = new JLabel("");

  private JLabel player_cards = new JLabel("");
  private JLabel dealer_cards = new JLabel("");
  private JLabel select_label = new JLabel("Player Count:");

  private JPanel north = new JPanel(new GridLayout(4,6));
  private JPanel center = new JPanel(new GridLayout(4,1));
  private JPanel south = new JPanel(new GridLayout(3,5));
  
  private JButton bet = new JButton();
  private JButton hit = new JButton();
  private JButton stay = new JButton();
  private JButton doub = new JButton();
  private JButton surrender = new JButton();
  private JButton start = new JButton();
  private JButton browseInput = new JButton();
  
  private JRadioButton radio1;
  private JRadioButton radio2;
  
  public BlackJackGUI(Game game){
    this.game = game;
    this.drawCanvas();
  }

  /**
   * reset frame for new betting round
   */
  public void nextRound(){
    setPlayerData();
    bet.setVisible(true);
    browseInput.setVisible(true);
    this.hidePlayerActions();
    this.round.setText(this.game.round+"");
    this.player_cards.setText("");
    this.dealer_cards.setText("");
    this.cardsDelt=false;
  }

  /**
   * draw original layout!
   */
  private void drawCanvas(){
    
    this.oFrame.setSize(new Dimension(600,500));
    this.oFrame.setForeground(Color.WHITE);
    this.oFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.oFrame.setTitle("Lets play blackjack!");
    this.oFrame.setBackground(Color.GREEN);
    this.oFrame.setLayout(new BorderLayout());
    this.round.setText(this.game.round+"");
    this.shoeCnt.setText(this.game.shoe.cardsAvailable()+"");
    
    north.add(new JLabel(""));
    this.drawBrowseInputButton();
    this.drawBrowseOutputButton();
    north.add(new JLabel("Write to File:"));
    this.drawRadios();
    
    north.add(new JLabel(""));
    north.add(new JLabel("Current Player:"));
    north.add(this.player);
    north.add(new JLabel("Bankroll:"));
    north.add(this.bankroll);
    north.add(new JLabel(""));
    
    north.add(new JLabel(""));
    north.add(new JLabel("Count:"));
    north.add(this.count);
    north.add(new JLabel("Bet:"));
    north.add(this.cbet);
    north.add(new JLabel(""));
    
    north.add(new JLabel(""));
    north.add(new JLabel("Round:"));
    north.add(this.round);
    north.add(new JLabel("Shoe Count:"));
    north.add(this.shoeCnt);
    north.add(new JLabel(""));
    
    this.oFrame.add(north, BorderLayout.NORTH);
    
    center.add(new JLabel(" Player Cards:"));
    center.add(this.player_cards);
    
    center.add(new JLabel(" Dealer Cards:"));
    center.add(this.dealer_cards);
    
    this.oFrame.add(center, BorderLayout.CENTER);
    
    String[] select = {"1","2","3","4","5","6"};
    this.player_count = new JComboBox(select);
    
    south.add(new JLabel(""));
    south.add(this.select_label);
    south.add(this.player_count);
    this.drawStartButton();
    south.add(new JLabel(""));
    
    south.add(new JLabel(""));
    south.add(new JLabel(""));
    this.drawBetButton();
    south.add(new JLabel(""));
    south.add(new JLabel(""));
    
    south.add(new JLabel(""));
    this.drawHitButton();
    this.drawStayButton();
    this.drawDoubleButton();
    this.drawSurrenderButton();

    this.oFrame.add(south, BorderLayout.SOUTH);
    
    this.oFrame.setVisible(true);
  }

  /**
   * initialize next player
   */
  public void nextPlayer(){
    this.drawPlayerCards();
    this.setPlayerData();
  }

  /**
   * show buttons for playing 
   */
  private void showPlayerActions(){
    hit.setVisible(true);
    stay.setVisible(true);
    doub.setVisible(true);
    surrender.setVisible(true);
  }
  
  /**
   * hide action buttons
   */
  private void hidePlayerActions(){
    hit.setVisible(false);
    stay.setVisible(false);
    doub.setVisible(false);
    surrender.setVisible(false);
  }

  /**
   * update frame with current player data
   */
  private void setPlayerData(){
    this.player.setText(this.game.getCurrentPlayer().getName());
    this.bankroll.setText(this.game.getCurrentPlayer().getBankroll());
    this.count.setText(this.game.getCurrentPlayer().getCount()+"");
    this.cbet.setText(this.game.getCurrentPlayer().getBet()+"");
  }
  
  /**
   * draw current player cards
   */
  public void drawPlayerCards(){
    this.shoeCnt.setText(this.game.shoe.cardsAvailable()+"");
    this.player_cards.setText(" "+game.getCurrentPlayer().getHand().toString());
  }
  
  /**
   * draw dealer cards
   */
  public void drawDealerCards(){
    this.shoeCnt.setText(this.game.shoe.cardsAvailable()+"");
    this.dealer_cards.setText(" "+game.getDealer().getHand().toString());
  }

  /**
   * deal cards to players and dealer
   */
  private void dealCards() 
      throws Exception{

    if(! this.cardsDelt) {
      game.deal();
      game.deal();
      drawPlayerCards();
      drawDealerCards();
      this.cardsDelt=true;
    }
    
    setPlayerData();
  }
  
  /**
   * render radio for export data
   */
  private void drawRadios(){
    
    radio1 = new JRadioButton("Active");
    radio1.setActionCommand("active");

      radio2 = new JRadioButton("Inactive");
      radio2.setActionCommand("inactive");
      radio2.setSelected(true);
      
      ButtonGroup group = new ButtonGroup();
      group.add(radio1);
      group.add(radio2);
      
      this.north.add(radio1);
      this.north.add(radio2);
     
      radio1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if( game.exportFile != null) {
              game.toggleExport(e.getActionCommand());
            } else {
              JOptionPane.showMessageDialog(null, "Please select output file first.");
              radio2.setSelected(true);
            }
          }
      });

      radio2.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            game.toggleExport(e.getActionCommand());
          }
      });
  }

  /**
   * bet button + respective listener
   */
  public void drawBetButton(){

    bet.setText("Bet");
    bet.setBackground(Color.RED);
    bet.setVisible(false);
    
    bet.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent event){
        for (Gambler gambler : game.players ) {
          String amt=JOptionPane.showInputDialog(null, gambler.name+", place your bet!");

          try {
            gambler.setBet(Integer.parseInt(amt));
          } catch (NumberFormatException e){
            gambler.setBet(50);
            JOptionPane.showMessageDialog(null, "Please enter an integer as a bet. You've been penalized with a $50 bet this time.");
          }
        }
        
        try {
          dealCards();
        } catch (Exception e) {
          e.printStackTrace();
        }

        bet.setVisible(false);
        browseInput.setVisible(false);
        showPlayerActions();
      }
    });
    
    this.south.add(bet);
  }

  /**
   * hit button + respective listener 
   */
  public void drawHitButton(){
    
    hit.setText("Hit");
    hit.setBackground(Color.RED);
    hit.setVisible(false);

    hit.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent event){
        //true means bust
        if(game.hit()){
          drawPlayerCards();
          String message = "Hey " + game.getCurrentPlayer().name + ", you just busted!\n\n";
          message += "Your hand:\n";
          message += game.getCurrentPlayer().getHand().toString()+"\n\n That's a ";
          message += game.getCurrentPlayer().getCount();
          JOptionPane.showMessageDialog(null, message);
          try {
            game.next();
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          }
        } else {
          doub.setVisible(false);
          setPlayerData();
          drawPlayerCards();
        }
      }
    });
    
    this.south.add(hit);
  }

  /**
   * stay button + respective listener
   */
  public void drawStayButton(){

    stay.setText("Stay");
    stay.setBackground(Color.RED);
    stay.setVisible(false);
    
    stay.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent event){
        try {
          game.stay();
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }
    });
    
    this.south.add(stay);
  }

  /**
   * double down button + respective listener
   */
  public void drawDoubleButton(){
    
    doub.setText("Double Down");
    doub.setBackground(Color.RED);
    doub.setVisible(false);
    
    doub.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent event){
        try {
          game.doubleDown();
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }
    });
    
    this.south.add(doub);
  }
  
  /**
   * surrender button + respective listener
   */
  public void drawSurrenderButton(){
    
    surrender.setText("Surrender");
    surrender.setBackground(Color.RED);
    surrender.setVisible(false);
    
    surrender.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent event){
        try {
          game.surrender();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    
    this.south.add(surrender);
  }
  
  /**
   * popup round summary info
   */
  public void displayRoundSummary(String msg){
    JOptionPane.showMessageDialog(null, msg);
  }
  
  /**
   * load deck button + listener
   */
  public void drawBrowseInputButton(){
    browseInput.setText("Load Shoe...");
      browseInput.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
          JFileChooser fileChooser = new JFileChooser();
          int returnValue = fileChooser.showOpenDialog(null);
          if (returnValue == JFileChooser.APPROVE_OPTION) {
            File csv = fileChooser.getSelectedFile();
            
            try {
        game.loadDeck(csv);
        radio1.setSelected(true);
        shoeCnt.setText(game.shoe.cardsAvailable()+"");
            } catch (FileNotFoundException e) {
        e.printStackTrace();
            }
          }
        }
      });

      this.north.add(browseInput);
  }
  
  /**
   * export button + listener
   */
  public void drawBrowseOutputButton(){

      JButton browseOutput = new JButton("Write To...");

      browseOutput.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
          JFileChooser fileChooser = new JFileChooser();
          int returnValue = fileChooser.showOpenDialog(null);
          if (returnValue == JFileChooser.APPROVE_OPTION) {
            File csv = fileChooser.getSelectedFile();

            game.loadOutput(csv);
          }
        }
      });

      this.north.add(browseOutput);
  }
  
  /**
   * choose player count and start
   */
  public void drawStartButton(){
    
    start.setText("Start Game");
    start.setBackground(Color.RED);
  
    start.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent event){
        game.initGame((String) player_count.getSelectedItem());
        setPlayerData();
        hideConfig();
        bet.setVisible(true);
      }
    });
    
    this.south.add(start);
  }
  
  /**
   * hide config buttons
   */
  public void hideConfig(){
    start.setVisible(false);
    player_count.setVisible(false);
    select_label.setText("");
  }
}
