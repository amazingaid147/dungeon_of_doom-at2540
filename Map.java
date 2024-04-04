import java.io.File; 
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;


public class Map {

  private Space[][] SmallBorderMap;
  private Space[][] FullMap;
  private int GoldNeeded;
  private int mapHeight = 0;
  private int mapWidth = 0;

  public Map ()
  {
    
  }

  public int GetGoldNeeded()
  {
    return GoldNeeded;
  }

   public int GetMapHeight()
  {
    return mapHeight;
  }
   public int GetMapWidth()
  {
    return mapWidth;
  }

  public Space GetMapSpace(int x, int y)
  {
    return FullMap[y][x];
  }



  public Map(String FileLocation)  // takes a map from a given txt file and converts it to an instance of a Map 
  {

    try {
      File MapFile = new File(FileLocation);  // opens the file
      Scanner FileReader = new Scanner(MapFile); // creates a scanner to read the file
      String Line1 = FileReader.nextLine();  // stops frist line a map - maps name to be converted into the map
      String Line2 = FileReader.nextLine(); // stops that line for being converted into the map
      GoldNeeded = Integer.valueOf(Line2.substring(4));  // records the amount of gold needed to exit
      String data = FileReader.nextLine();
      mapWidth = data.length();  // records the width of the map by checking the length of the first line of the map
      mapHeight = 1;
      while (FileReader.hasNextLine()) {   // calculates the hieght of the map by checking how many lines the map has in the txt file
        mapHeight++;
        data = FileReader.nextLine();
      }
      FileReader.close();

      
      SmallBorderMap = new Space [mapHeight][mapWidth];  // creates a 2d array of spaces the exact dimension of the txt map
      for (int h = 0; h < mapHeight; h++)
            {
                for (int w = 0; w < mapWidth; w++)
                {
                    SmallBorderMap[h] [w] = new Space();
                }
            }

      Scanner FileReader2 = new Scanner(MapFile);  // gets the data from the txt file a second time
      Line1 = FileReader2.nextLine();
      Line2 = FileReader2.nextLine();
      int j = 0;
      while (FileReader2.hasNextLine()) {  // puts the data from the txt file into the 2d array 
        data = FileReader2.nextLine();
        for (int i = 0; i < mapWidth; i ++)
          {
            if (data.charAt(i) == '#')
            {
              SmallBorderMap[j][i].setWall(true);
            }
            if(data.charAt(i) == 'G'){
              SmallBorderMap[j][i].setGold(true);
            }

            if(data.charAt(i) == 'E'){
              SmallBorderMap[j][i].setExit(true);
            }

            if(data.charAt(i) == '.'){
              SmallBorderMap[j][i].setEmpty(true);
            }
          }
        j++;
        
        
      }     
      FileReader2.close();

      

    } 
    catch (FileNotFoundException e) {     // checks if there was an issue finding the specified txt file
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    FullMap = new Space [mapHeight+2][mapWidth+2];  // creates a new 2d array with dimensions 2 spaces wider and 2 spaces taller then the original map
    for (int h = 0; h < mapHeight+2; h++)
          {
              for (int w = 0; w < mapWidth+2; w++)
              {
                  FullMap[h] [w] = new Space();
              }
          }


    for (int h = 0; h < mapHeight+2; h++)  // adds a line of walls on top and bottom of the new array/map
      {
        FullMap[h][0].setWall(true);
        FullMap[h][mapWidth + 1].setWall(true);
      }

    for (int w = 0; w < mapWidth+2; w++)   // adds a line of walls on the sides of the new array/map
      {
        FullMap[0][w].setWall(true);
        FullMap[mapHeight + 1][w].setWall(true);
      }
    for (int h = 1; h < mapHeight+1; h++)   // adds the data from the original 2d array/map into the middle of the new 2d arra/map
          {
              for (int w = 1; w < mapWidth+1; w++)
              {
                  FullMap[h][w] = SmallBorderMap[h-1][w-1];
              }
          }
    CreatePlayer();  // adds a player in a random non wall location on the map
    CreateBot();  // adds a bot in a random non player and non wall location on the map
    
  }

  public void PrintDungeon()  // prints the entire map to the console
  {
    for (int h = 0; h < mapHeight + 2; h++)
              {
                  for (int w = 0; w < mapWidth + 2; w++)
                  {
                      System.out.print(FullMap[h][w].GetSpace());
                  }
                System.out.println("  ");
              }

  }

  public void PrintPlayerView() // prints the 5x5 grid around the player character to the console
  {
    for (int h = 0; h < mapHeight + 2; h++)
    {
      for (int w = 0; w < mapWidth + 2; w++)
      {
        if (FullMap[h][w].GetPlayer() != null)  // finds the player on the map
        {                        
          for(int h1 = 0; h1 < 5; h1++)
          {
            for(int w1 = 0; w1 < 5; w1++)
            {
              System.out.print(FullMap[h - 2 + h1][w - 2 + w1].GetSpace()); // prints the grid araound the player
            }
            System.out.println("  ");
            
          }
        }
                      
      }
    }
  }

  private void CreateBot()  // adds a new bot to a random non player and non wall location on the map
        {
            Random Rnd = new Random();  // imports function to generate random numbers
            int x = Rnd.nextInt(mapWidth + 2); // finds a random x co-ordinate on the map
            int y = Rnd.nextInt(mapHeight + 2);// finds a random y co-ordinate on the map


            while ((FullMap[y][x].GetPlayer() != null) || (FullMap[y][x].GetWall() == true)) // checks that location is non player and non wall location on the map
            {
              
                  x = Rnd.nextInt(mapWidth + 2);
                  y = Rnd.nextInt(mapHeight + 2);
                 // creates new co-ordinates
            }

            FullMap[y][x].setBot(new Bot()); // creates bot in the specified location
            
        }

  private void CreatePlayer()  // adds a new player to a random non player and non wall location on the map
        {
            Random Rnd = new Random();// imports function to generate random numbers
            int x = Rnd.nextInt(mapWidth + 2);// finds a random x co-ordinate on the map
            int y = Rnd.nextInt(mapHeight + 2);// finds a random y co-ordinate on the map


            while (FullMap[y][x].GetWall() == true)// checks that location is non player and non wall location on the map
            {
              
                x = Rnd.nextInt(mapWidth + 2);
                y = Rnd.nextInt(mapHeight + 2);
              // creates new co-ordinates
            }

            FullMap[y][x].setPlayer(new Player());// creates player in the specified location
            
        }

  public boolean CheckIfBotOnPlayer()  // checks if the bot and the player are in the same location
  {
    boolean botOnPlayer = false;
    for (int h = 0; h < mapHeight + 2; h++)
                {
                
                    for (int w = 0; w < mapWidth + 2; w++)
                    {
                      
                      
                      if (FullMap[h][w].GetBot() != null)
                      {
                        if (FullMap[h][w].GetPlayer() != null)
                        {
                          System.out.print("you loose as the bot caught you"); // displays message letting player know what happend
                          botOnPlayer = true;  
                        
                          
                        }
                        
                        
                      }
                      
                    }
                }

    return botOnPlayer; // returns boolean indication if bot and player are in the same location
  }

  public boolean BotHasAction()  // checks if the bot has got an action ready
  {
    boolean botHasAction = true;
    for (int h = 0; h < mapHeight + 2; h++)
                {
                
                    for (int w = 0; w < mapWidth + 2; w++)
                    {
                      
                      
                      if (FullMap[h][w].GetBot() != null) // finds bot in map
                      {
                        
                        botHasAction = FullMap[h][w].BotHasAction(); // checks if bot has an action ready
                      }
                      
                    }
                }

    return botHasAction;
  }

  public String DecideBotAction() // cause bot to ready an action
  {
    String BotAction = " ";

    for (int h = 0; h < mapHeight + 2; h++)
                {
                
                    for (int w = 0; w < mapWidth + 2; w++)
                    {
                      
                      
                      if (FullMap[h][w].GetBot() != null) // finds bot in map
                      {
                        
                        BotAction = FullMap[h][w].DecideBotAction(FullMap, GoldNeeded); // causes bot to ready and action
                      }
                      
                    }
                }



    return BotAction;
  }

  public void BotPickUpGold()  // allows bot to pick up gold it in on
  {
    
    for (int h = 0; h < mapHeight + 2; h++)
                {
                
                    for (int w = 0; w < mapWidth + 2; w++)
                    {
                      
                      
                      if (FullMap[h][w].GetBot() != null)  // finds bot in map
                      {
                        
                        FullMap[h][w].setGold(false);
                        FullMap[h][w].setEmpty(true);
                        FullMap[h][w].IncremeantGold();
                      }
                      
                    }
                }
    
  }
  
  public void PlayerPickUpGold()  // allows player to pick up gold it in on
  {
    
    for (int h = 0; h < mapHeight + 2; h++)
                {
                
                    for (int w = 0; w < mapWidth + 2; w++)
                    {
                      
                      
                      if (FullMap[h][w].GetPlayer() != null)// finds player in map
                      {
                        
                        FullMap[h][w].setGold(false);
                        FullMap[h][w].setEmpty(true);
                        FullMap[h][w].IncremeantGold();
                      }
                      
                    }
                }
    
  }

  public boolean PlayerOnGold()  // checks if player is on gold
  {
    boolean IsPlayerOnGold = false;;
    for (int h = 0; h < mapHeight + 2; h++)
                {
                
                    for (int w = 0; w < mapWidth + 2; w++)
                    {
                      
                      
                      if ((FullMap[h][w].GetPlayer()) != null) // finds Player in map
                      {
                        if(FullMap[h][w].GetGold() == true) // checks if gold is present in that location
                        {
                          IsPlayerOnGold = true;
                        }
                        
                        
                      }
                      
                    }
                }
    return IsPlayerOnGold; // returns boolean value indication if the player is on gold
  }

  public boolean PlayerOnExit()// checks if player is on an exit
  {
    boolean IsPlayerOnExit = false;;
    for (int h = 0; h < mapHeight + 2; h++)
                {
                
                    for (int w = 0; w < mapWidth + 2; w++)
                    {
                      
                      
                      if (FullMap[h][w].GetPlayer() != null)// finds Player in map
                      {
                        if(FullMap[h][w].GetExit() == true)// checks if an exit is present in that location
                        {
                          IsPlayerOnExit = true;
                        }
                      }
                      
                    }
                }
    return IsPlayerOnExit; // returns boolean value indication if the player is on an exit
  }
  public String GetPlayerGold() // finds out how much gold a player has
  {
    String PlayersGold = " ";
    for (int h = 0; h < mapHeight + 2; h++)
                {
                
                    for (int w = 0; w < mapWidth + 2; w++)
                    {
                      
                      
                      if (FullMap[h][w].GetPlayer() != null)// finds Player in map
                      {
                        PlayersGold = FullMap[h][w].GetPlayersGold();// checks how much gold the player has
                      }
                    }
                }
    return PlayersGold; // returns a string of how much gold the player has
  }
  public void MovePlayer(String Direction) // moves player in the map
              {
                
                boolean moved = false; // checks if player has already moved this turn to prevent loop from finding and moving player multiple times 
                int x = 0;
                int y = 0;
                  for (int h = 0; h < mapHeight + 2; h++)
                {
                
                    for (int w = 0; w < mapWidth + 2; w++)
                    {
                      
                      
                      if (FullMap[y][x].GetPlayer() != null  && moved == false) // finds player in map and checks if its already moved this turn
                      {
                        
                        if (Direction.contains("N")) // moves player north if possible
                        {
                          if(FullMap[y-1][x].GetWall()  == true) // checks if a wall is in the way
                          {
                            System.out.println("Fail"); // outputs message to let user know its movement failed
                          }

                          else if(FullMap[y-1][x].GetBot()  != null) // checks if a bot is in the way
                          {
                            System.out.println("Fail"); // outputs message to let user know its movement failed
                          }

                          else
                          {
                            FullMap[y-1][x].setPlayer(FullMap[y][x].GetPlayer()); // puts a coppy of the player in the space in the direction the intend to move
                            FullMap[y][x].setPlayer(); // sets player in old location to be null
                            System.out.println("Success"); // outputs message to let user know its movement succeded
                          }
                        }
                        
                        if (Direction.contains("S"))// moves player south if possible
                        {
                          if(FullMap[y+1][x].GetWall()  == true)// checks if a wall is in the way
                          {
                            System.out.println("Fail");// outputs message to let user know its movement failed
                          }

                          else if(FullMap[y+1][x].GetBot()  != null)// checks if a bot is in the way
                          {
                            System.out.println("Fail");// outputs message to let user know its movement failed
                          }

                          else
                          {
                            FullMap[y+1][x].setPlayer(FullMap[y][x].GetPlayer());// puts a coppy of the player in the space in the direction the intend to move
                            FullMap[y][x].setPlayer();// sets player in old location to be null
                            System.out.println("Success");// outputs message to let user know its movement succeded
                            
                          }
                          
                        }
                        if (Direction.contains("E")) // moves player east if possible
                        {
                          if(FullMap[y][x+1].GetWall()  == true)// checks if a wall is in the way
                          {
                            System.out.println("Fail");// outputs message to let user know its movement failed
                          }

                          else if(FullMap[y][x+1].GetBot()  != null)// checks if a bot is in the way
                          {
                            System.out.println("Fail");// outputs message to let user know its movement failed
                          }

                          else
                          {
                            FullMap[y][x+1].setPlayer(FullMap[y][x].GetPlayer());// puts a coppy of the player in the space in the direction the intend to move
                            FullMap[y][x].setPlayer();// sets player in old location to be null
                            System.out.println("Success");// outputs message to let user know its movement succeded
                          
                          }
                          
                        }
                        if (Direction.contains("W")) // moves player west if possible
                        {
                          if(FullMap[y][x-1].GetWall()  == true)// checks if a wall is in the way
                          {
                            System.out.println("Fail");// outputs message to let user know its movement failed
                          }

                          else if(FullMap[y][x-1].GetBot()  != null)// checks if a bot is in the way
                          {
                            System.out.println("Fail");// outputs message to let user know its movement failed
                          }

                          else
                          {
                            FullMap[y][x-1].setPlayer(FullMap[y][x].GetPlayer());// puts a coppy of the player in the space in the direction the intend to move
                            FullMap[y][x].setPlayer();// sets player in old location to be null
                            System.out.println("Success");// outputs message to let user know its movement succeded
                          }
                          
                        }
                        moved = true; // updates value to show that the player has tried to move
                      }
                      x = x + 1;
                    }
                  x = 0;
                  y = y +1;

                }
             
              }

  public void MoveBot(String Direction)
              {
                
                boolean moved = false;// checks if bot has already moved this turn to prevent loop from finding and moving bot multiple times 
                int x = 0;
                int y = 0;
                  for (int h = 0; h < mapHeight + 2; h++)
                {
                
                    for (int w = 0; w < mapWidth + 2; w++)
                    {
                      
                      
                      if (FullMap[y][x].GetBot() != null  && moved == false)// finds bot in map and checks if its already moved this turn
                      {
                        
                        if (Direction.contains("NORTH"))// moves bot north if possible
                        {
                     
                          FullMap[y-1][x].setBot(FullMap[y][x].GetBot());// puts a coppy of the bot in the space in the direction the intend to move
                          FullMap[y][x].setBot();// sets bot in old location to be null
                          
                          
                        }
                        
                        if (Direction.contains("S"))// moves bot south if possible
                        {
                          
                          FullMap[y+1][x].setBot(FullMap[y][x].GetBot());// puts a coppy of the bot in the space in the direction the intend to move
                          FullMap[y][x].setBot();// sets bot in old location to be null
                       
                            
                          
                          
                        }
                        if (Direction.contains("E"))// moves bot east if possible
                        {
                          
                          FullMap[y][x+1].setBot(FullMap[y][x].GetBot());// puts a coppy of the bot in the space in the direction the intend to move
                          FullMap[y][x].setBot();// sets bot in old location to be null
                            
                          
                        }
                        if (Direction.contains("W"))// moves bot west if possible
                        {
                          
                          FullMap[y][x-1].setBot(FullMap[y][x].GetBot());// puts a coppy of the bot in the space in the direction the intend to move
                          FullMap[y][x].setBot();// sets bot in old location to be null
                            
                          
                        }
                        moved = true;// updates value to show that the bot has tried to move
                      }
                      x = x + 1;
                    }
                  x = 0;
                  y = y +1;

                }
             
              }

}