package model;

import java.io.*;


/**
 * @author dan
 *
 */
public class Util {
  
  public Util(){}

  /**
   * export data to assigned file
   */
  public void writeToFile(Gambler player, File file) 
      throws FileNotFoundException{
    
    String gain_or_loss="";
    
    if(player.isWinner()){
      gain_or_loss+=player.getBet()+"";
    } else if (player.isPush()){
      gain_or_loss+="0";
    } else {
      gain_or_loss+="-"+player.getBet()+"";
    }
    
    PrintStream ps = new PrintStream(new FileOutputStream(file, true)); 

    ps.append(player.getPosition()+"");
    ps.append(",");
    ps.append(gain_or_loss);
    ps.append(",");
    ps.append(player.getBankroll());
    ps.append("\n");
    ps.close();
  }
}