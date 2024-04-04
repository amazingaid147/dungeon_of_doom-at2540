public class Space {       //contains all possible things a space on the map could 
  
  private Player Player;  // class contains a player object
  private Bot Bot;     // class contains a bot objects
  private boolean exit;
  private boolean gold;
  private boolean wall;
  private boolean empty;

  public Space()
  {
    Bot = null;
    Player = null;
    exit = false;
    gold = false;
    wall = false;
    empty = false;
      
  }

  public char GetSpace()  // returns the character to be displayed to represent the space on the map
  {
    char contents =' ';
    
    if (exit == true)
    {
      contents = 'E';
   
    }
    
    if (gold == true)
    {
      contents = 'G';
   
    }
    
    if (wall == true)
    {
      contents = '#';
     
    }
    
    if (empty == true)
    {
      contents = '.';
     
    }
    
    if (Player != null)
    {
      contents = 'P';
      
    }
    
     if (Bot != null)
    {
      contents = 'B';
      
    }

    return contents;
  }

  public void setEmpty(boolean b)
  {
    empty = b;
  }

  public void setWall(boolean b)
  {
    wall = b;
  }
  public void setGold(boolean b)
  {
    gold = b;
  }
  public void setExit(boolean b)
  {
    exit = b;
  }
  public void setBot(Bot bot)
  {
    Bot = bot;
  }
  public void setBot()
  {
    Bot = null;
  }
  public void setPlayer(Player player)
  {
    Player = player;
  }
  public void setPlayer()
  {
    Player = null;
  }

  public boolean GetEmpty()
  {
    return empty;
  }
  public boolean GetWall()
  {
    return wall;
  }
  public boolean GetGold()
  {
    return gold;
  }
  public boolean GetExit()
  {
    return exit;
  }
  public Bot GetBot()
  {
    return Bot;
  }
  public Player GetPlayer()
  {
    return Player;
  }
  public String GetPlayersGold()
  {
    return Player.GetGold();
  }
  public void IncremeantGold()
  {
    Player.IncremeantGold();
  }

  public String DecideBotAction(Space[][] map, int GoldNeeded) // gets the bot to decide and action and returns what that action is
  {
    String BotAction = " ";

    BotAction = Bot.DecideBotAction(map, GoldNeeded);


    return BotAction;
  }

  public boolean BotHasAction() // returns a boolean value to represent if the bot has decided an action
  {
    boolean BotAction = true;

    BotAction = Bot.GetHasAction();


    return BotAction;
  }

  
 


}