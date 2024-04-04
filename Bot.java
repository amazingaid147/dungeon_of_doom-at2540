import java.util.Random;

public class Bot extends Agent{

  private boolean HasAction = false;
  private Space[][] BotView = new Space [5][5];   // creates an array of spaces that contains what the bot can see 
  
  
    public String GetGold()
  {
    String amountOfGold = String.valueOf(AmountOfGold);
    return amountOfGold;
  }
  


  public Bot()
  {

    for (int x = 0; x < 5; x++)
            {
                for (int y = 0; y < 5; y++)
                {
                    BotView [x][y] = new Space();  // fully instanciates BotView
                }
            }
  }

  public boolean GetHasAction() // swaps value of HasAction and return original value of HasAction
  {
    if (HasAction = true)
    {
      HasAction = false;
      return true;
    }
    else
    {
      HasAction = true;
      return false;
    }
    
  }

  public String RandomDirection()  // picks a random direction
  {
    Random Rnd = new Random();
    int x = Rnd.nextInt(4);
    if ( x == 0)
    {
      return "North";
    }
    else if (x == 1)
    {
      return "South";
    }
    else if (x == 2)
    {
      return "East";
    }
    else
    {
      return "West";
    }
            
  }

  


  public String DecideBotAction(Space[][] map, int GoldNeeded)
  {

    String BotAction = " ";
    
    int mapHieght = map[0].length;
    int mapWidth = map.length;
    

    for (int h = 0; h < mapHieght + 2; h++)
    {
                
      for (int w = 0; w < mapWidth + 2; w++)
      {
        if (map[h][w].GetBot() != null)
        {
          for(int h2 = 0; h2 < 5; h2++)
          {
            for(int w2 = 0; w2 < 5; w2++)
            {
              BotView[h2][w2] = map[h - 2 + h2][w - 2 + w2];  // fills in BotView With what the bot sees as if it had input look
            }
          }
        }
      }
    }
    if(BotView[2][2].GetGold() == true)
    {
      BotAction = "PICKUP";
    }
      
    else if (AmountOfGold >= GoldNeeded)
      if(BotView[2][2].GetExit() == true)
      {
        BotAction = "QUIT";
      }

    else
      {  //   checks what the bot can see
        String NearPlayer = GetNearPlayer(BotView);
        String FarPlayer = GetFarPlayer(BotView);
        String NearGold = GetNearGold(BotView);
        String FarGold = GetFarGold(BotView);
        String NearExit = GetNearExit(BotView);
        String FarExit = GetFarExit(BotView);
        
        // decides what bot will do based on what it can see with a priority on catching the enemy, then getting gold, the finding an exit if it has enough gold
        if(FarGold.contains("T"))
        {
          BotAction = FarGold;
        }
        else if(FarPlayer.contains("T"))
        {
          BotAction = FarPlayer;
        }
              
        else if(NearGold.contains("T"))
        {
          BotAction = NearGold;
        }
        else if(AmountOfGold >= GoldNeeded)
        {
          if(NearExit.contains("T"))
          {
            BotAction = NearExit;
          }
        }
        else if(AmountOfGold >= GoldNeeded)
        {
          if(NearExit.contains("T"))
          {
            BotAction = NearExit;
          }
        }
        else if(NearPlayer.contains("T"))
        {
          BotAction = NearPlayer;
        }
        else
        {
          BotAction = RandomDirection();
        }
      }
                      

    return BotAction;
  }

  public String GetNearPlayer(Space[][] view) // checks if player in adjacent square
  {
    String Direction = " ";
    if(view[1][2].GetPlayer() != null)
    {
      Direction = "NORTH";
    }
    if(view[3][2].GetPlayer() != null)
    {
      Direction = "SOUTH";
    }
    if(view[2][1].GetPlayer() != null)
    {
      Direction = "WEST";
    }
    if(view[2][3].GetPlayer() != null)
    {
      Direction = "EAST";
    }

    return Direction;
  }

  public String GetFarPlayer(Space[][] view)// checks if player is two adjacent squares away and there in no wall in the way
  {
    String Direction = " ";

    if(view[0][2].GetPlayer() != null)
    {
      if(view[1][2].GetWall() == false)
      {
        Direction = "NORTH";
      }
    }
    if(view[4][2].GetPlayer() != null)
    {
      if(view[3][2].GetWall() == false)
      {
        Direction = "SOUTH";
      }
    }
    if(view[2][0].GetPlayer() != null)
    {
      if(view[2][1].GetWall() == false)
      {
        Direction = "WEST";
      }
    }
    if(view[2][4].GetPlayer() != null)
    {
      if(view[2][3].GetWall() == false)
      {
        Direction = "EAST";
      }
    }

    return Direction;
  }

  public String GetNearGold(Space[][] view)// checks if gold in adjacent square
  {
    String Direction = " ";

    if(view[1][2].GetGold() == true)
    {
      Direction = "NORTH";
    }
    if(view[3][2].GetGold() == true)
    {
      Direction = "SOUTH";
    }
    if(view[2][1].GetGold() == true)
    {
      Direction = "WEST";
    }
    if(view[2][3].GetGold() == true)
    {
      Direction = "EAST";
    }

    return Direction;
  }

  public String GetFarGold(Space[][] view)// checks if gold is two adjacent squares away and there in no wall in the way
  {
    String Direction = " ";

    if(view[0][2].GetGold() == true)
    {
      if(view[1][2].GetWall() == false)
      {
        Direction = "NORTH";
      }
    }
    if(view[4][2].GetGold() == true)
    {
      if(view[3][2].GetWall() == false)
      {
        Direction = "SOUTH";
      }
    }
    if(view[2][0].GetGold() == true)
    {
      if(view[2][1].GetWall() == false)
      {
        Direction = "WEST";
      }
    }
    if(view[2][4].GetGold() == true)
    {
      if(view[2][3].GetWall() == false)
      {
        Direction = "EAST";
      }
    }

    return Direction;
  }

  public String GetNearExit(Space[][] view)// checks if exit in adjacent square
  {
    String Direction = " ";

    if(view[1][2].GetExit() == true)
    {
      Direction = "NORTH";
    }
    if(view[3][2].GetExit() == true)
    {
      Direction = "SOUTH";
    }
    if(view[2][1].GetExit() == true)
    {
      Direction = "WEST";
    }
    if(view[2][3].GetExit() == true)
    {
      Direction = "EAST";
    }

    return Direction;
  }

  public String GetFarExit(Space[][] view)// checks if exit is two adjacent squares away and there in no wall in the way
  {
    String Direction = " ";

    if(view[0][2].GetExit() == true)
    {
      if(view[1][2].GetWall() == false)
      {
        Direction = "NORTH";
      }
    }
    if(view[4][2].GetExit() == true)
    {
      if(view[3][2].GetWall() == false)
      {
        Direction = "SOUTH";
      }
    }
    if(view[2][0].GetExit() == true)
    {
      if(view[2][1].GetWall() == false)
      {
        Direction = "WEST";
      }
    }
    if(view[2][4].GetExit() == true)
    {
      if(view[2][3].GetWall() == false)
      {
        Direction = "EAST";
      }
    }

    return Direction;
  }


}