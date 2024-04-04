import java.util.Scanner;

public class GamePlay {
  private Map map;
  Scanner scanner = new Scanner(System.in);


  public GamePlay()
  {
    
  }

  public GamePlay(Map map1)  // creates a map to exist to work within gameplay
  {
    map = map1;
    
  }

  public boolean DoBotAction(String Action) // enacts bots action on the map and returns if that would cause a gameover
  {
    boolean GameOver = false;
    if (Action.contains("NORTH"))
    {
      map.MoveBot("NORTH");
      
      GameOver = map.CheckIfBotOnPlayer();
    }

    if (Action.contains("EAST"))
    {
      map.MoveBot("EAST");
      GameOver = map.CheckIfBotOnPlayer();
    }

    if (Action.contains("WEST"))
    {
      map.MoveBot("WEST");
      GameOver = map.CheckIfBotOnPlayer();
    }
    
    if (Action.contains("SOUTH"))
    {
      map.MoveBot("SOUTH");
      GameOver = map.CheckIfBotOnPlayer();
    }
    
    
    if (Action.contains("QUIT"))
    {
      System.out.println("The Bot has collected enough gold and escaped");
      GameOver = true;
    }
    
    if (Action.contains("PICKUP"))
    {
      map.BotPickUpGold();
    }
    return GameOver;
  }


  public boolean DoPlayerAction(String Action) // enacts players action on the map and returns if that would cause a gameover
  {
    boolean GameOver = false;
    if (Action.contains("LOOK"))
    {
      map.PrintPlayerView();
    }
    
    if (Action.contains("MOVE"))
    {
      boolean valid = false;
      String Direction = " ";
      while (valid == false)   // froces player to choose a valid direction
      {
        System.out.println("(N)orth (S)outh (E)ast or (W)est");
        Direction = scanner.nextLine();
        if (Direction.contains("N") || Direction.contains("S") || Direction.contains("E") || Direction.contains("W"))
        {
          valid = true;
        }
      }
      map.MovePlayer(Direction);
    }
    
    if (Action.contains("GOLD"))
    {
      System.out.println("Gold owned: " + map.GetPlayerGold());
    }
    
    if (Action.contains("HELLO"))
    {
      System.out.println("Gold needed to win: " + map.GetGoldNeeded());
    }
    
    if (Action.contains("QUIT"))
    {
      String playerGold = map.GetPlayerGold();
      String goldNeeded = String.valueOf(map.GetGoldNeeded());
      if(playerGold.contains(goldNeeded))
      {
        if(goldNeeded.contains(playerGold))
        {
          if(map.PlayerOnExit() == true)
          {
            System.out.println("WIN");
          }
        }
 
      }
      else
      {
        System.out.println("LOOSE");
      }
      GameOver = true;
    }
    
    if (Action.contains("PICKUP"))
    {
      boolean IsPlayerOnGold = map.PlayerOnGold();
      if(IsPlayerOnGold == true )
      {
        map.PlayerPickUpGold();
        System.out.println("SUCCESS");
      }
      if(IsPlayerOnGold == false )
      {
        System.out.println("FAIL");
      }
    }
    return GameOver;
  }

}