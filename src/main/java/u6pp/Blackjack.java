package u6pp;
import java.util.Scanner;
/**
Simulates a game of blackjack
*/
public class Blackjack
{
  private Deck blackJackDeck = new Deck();
  public Blackjack()
  {
  }
  /**
  Runs games of black jack between the user and a dealer using a scanner and the methods listed below. Repeats games until user decides to stop.
  calcPoints, determineResult, isBust, isBlackjack, dealerKeepHitting, hit, printHand
  */
  public void play()
  {
    Scanner scan = new Scanner(System.in);
    String userName;
    String playerInput;
    boolean gameOver;
    boolean algorithmOver = false;
    boolean validInput;
    boolean userHit;
    boolean busted;
    Card[] userHand;
    Card[] dealerHand;
    System.out.println("Welcome to Surfer Blackjack, dude! What's your name bro?");
    userName = scan.nextLine();
    System.out.println("Surfs up " + userName + "! I'm Beach Bum Bob, let's ride some waves.");
    while (!(algorithmOver))
    {
      gameOver = false;
      validInput = false;
      userHit = true;
      busted = false;
      userHand = new Card[2];
      dealerHand = new Card[2];
      for (int i = 0; i<2; i++)
      {
        dealerHand[i] = blackJackDeck.deal();
        userHand[i] = blackJackDeck.deal();
      }
      printHands(userHand, dealerHand, 0);
      if (isBlackjack(dealerHand))
      {
        System.out.println("Hang ten, I got a blackjack.");
        gameOver = true;
      }
      if (!(gameOver))
      {
        while (userHit)
        {
          System.out.println("\nWanna (H)it some waves or wanna (S)tay cool?");
          playerInput = scan.nextLine();
          if (playerInput.toUpperCase().equals("H") ||    playerInput.toLowerCase().equals("hit"))
          {
            userHand = hit(userHand);
            if (isBust(userHand))
            {
              System.out.println("Wipeout! you busted.");
              userHit = false;
              busted = true;
            }
            else
              printHands(userHand, dealerHand, 0);
          }
          else if (playerInput.toUpperCase().equals("S") || playerInput.toLowerCase().equals("stay"))
          {
            userHit = false;
          }
          else
          {
            System.out.println("Woah, narly input dude. Try again!");
            userHit = true;
          }

        }

        while (!(busted) && dealerKeepHitting(dealerHand))
        {
          dealerHand = hit(dealerHand);
          if (isBust(dealerHand))
          {
            System.out.println("Wipeout! I busted");
          }
        }
      }
      printHands(userHand, dealerHand, 1);
        System.out.println("Result: " + determineResult(userHand, dealerHand));
        while (!(validInput))
        {
          System.out.println("Would you like to play again? (T)otally/(N)ah");
          playerInput = scan.nextLine();
          if (playerInput.toUpperCase().equals("N") || playerInput.toLowerCase().equals("nah"))
          {
            algorithmOver = true;
            validInput = true;
            System.out.print("See you on another wave" + userName);
          }
          else if (playerInput.toUpperCase().equals("T") || playerInput.toLowerCase().equals("totally"))
          {
            validInput = true;
            System.out.println("Totally, that's totally rad!");
          }
          else
            System.out.println("Woah, narly input dude. Try again!");
        }
    }
  }
  /**
  @param Card array that's indexes will be totaled
  @return total points of Card object values where numbers are ints, face cards (Jack, Queen, King) are 10, and the ace is either 1 or 11
  */
  public static int calcPoints(Card[] hand)
  {
    int total = 0;
    for (int i = 0; i<hand.length; i++)
    {
      if (hand[i].getValue().equals("Jack") || hand[i].getValue().equals("Queen") || hand[i].getValue().equals("King"))
      {
        total += 10;
      }
      else if (!(hand[i].getValue().equals("Ace")))
      {
        total += Integer.parseInt(hand[i].getValue());
      }
    }
    for (int i = 0; i < hand.length; i++)
    {
      if (hand[i].getValue().equals("Ace"))
      {
        if (total + 11 > 21)
          total += 1;
        else
          total += 11;
      }
    }
    return total;
  }
  /**
  @param Card array representing users hand and card array representing dealers hand
  @return "User Loses" if user ?has busted? or user total points are less than dealer total points.
  @return "User Wins" is user total points are greater than dealer total points
  @return "User Pushes" if user total points equal dealer total points
  */
  public static String determineResult(Card [] userHand, Card[] dealerHand)
  {
    if (calcPoints(userHand) == 21 || calcPoints(userHand) > calcPoints(dealerHand) || isBust(dealerHand))
      return "User Wins. Totally rad!";
    else if (isBust(userHand) || (calcPoints(userHand) < calcPoints(dealerHand)))
      return "User Loses. I out surfed you bro!";
    else
      return "User Pushes. Nice waves bro!";
  }

  /**
  @param Card array that is being tested for a bust
  @return true if the total points of hand are above 21
  */
  public static boolean isBust(Card[] hand)
  {
    if(calcPoints(hand) > 21)
      return true;
    else
      return false;
  }
  /**
  @param Card array that is being tested for a Blackjack
  @return true if total points of hand equal 21
  */
  public static boolean isBlackjack(Card[] hand)
  {
    if(calcPoints(hand) == 21)
      return true;
    else
      return false;
  }
  /**
  @param Card array that represents the dealers hand
  @return true if total points of hand is below 17, causing the dealer to dealer to dealer to hit
  */
  public static boolean dealerKeepHitting(Card[] dealerHand)
  {
    if (calcPoints(dealerHand) > 16)
      return false;
    else
      return true;
  }

  /**
  @param Card array that is being added to
  @return returns a new Card array with the Card objects from the paramater plus a new random object, via the deal function
  */
  private Card[] hit (Card[] hand)
  {
    if (blackJackDeck.numLeft() == 0)
      blackJackDeck.shuffle();
    Card[] hitHand = new Card[hand.length + 1];
    for (int i = 0; i < hand.length; i++)
    {
      hitHand[i] = hand[i];
    }
    hitHand[hitHand.length - 1] = blackJackDeck.deal();
    return hitHand;
  }
  /**
  @param Card arrays representing the users hand and the dealers hand
  @return prints all the Cards in the users hand and prints either all the Cards, or all the cards minus index 1, in the dealers hand.
  */
  private void printHands(Card[] userHand, Card[] dealerHand, int x)
  {
    System.out.print("Dude, you have:  ");
    for (int i = 0; i<userHand.length; i++)
    {
      System.out.print(userHand[i] + " ");
    }
    System.out.print("\nYou know I have: ");
    for (int i = 1 - x; i < dealerHand.length; i++)
    {
      System.out.print(dealerHand[i] + " ");
    }
    System.out.println("");
  }
}
