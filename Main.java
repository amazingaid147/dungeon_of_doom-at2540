import java.util.Scanner;


class Main { 
  public static void main(String[] args) 
  {
    Scanner scanner = new Scanner(System.in);
    boolean GameOver = false;
    String PlayerAction = " ";
    String BotAction = " ";
    
    
    System.out.println("please enter the map file Location");
    String FileLocation = scanner.nextLine();
    

    Map map = new Map(FileLocation);   // creates a new map from the specified location
    map.PrintDungeon();



    GamePlay gamePlay = new GamePlay(map); // enacts player and bot actions on the map

    while(GameOver == false) // runs game while the game has not ended
      {
        System.out.println("What would you like to do?");
        PlayerAction = playerInput();
        GameOver = gamePlay.DoPlayerAction(PlayerAction);  // enacts player action on the map using the gamePlay object and checks if this will end the game
        if (map.BotHasAction() == false)  
        {
          BotAction = map.DecideBotAction();
        }
        else
        {
          GameOver = gamePlay.DoBotAction(BotAction); // enacts bot action on the map using the gamePlay object and checks if this will end the game
        }
        
        
        
      }

    

    
  }

  public static String playerInput()
  {
    Scanner scanner = new Scanner(System.in);
    boolean valid = false;
    String input = scanner.nextLine();
    input = input.toUpperCase();
    while (valid == false)
    {
      if (input.contains("LOOK") ||  input.contains("HELLO") || input.contains("QUIT" )|| input.contains("GOLD" )|| input.contains("PICKUP") || (input.contains("MOVE")))        // checks if player input is valid
      {
        valid = true;
      }
      else
      {
        System.out.println("Please enter one of: look, hello, quit, gold, pickup, move");
        input = scanner.nextLine();
        input = input.toUpperCase();

      }
        
    }
    return input;
  }  
}