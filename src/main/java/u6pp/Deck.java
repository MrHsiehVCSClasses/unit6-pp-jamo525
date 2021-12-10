package u6pp;
/**
Creates and uses an array deck of Card objects
*/
public class Deck
{
  private Card[] deck;
  /**
  runs shuffle to create new array
  */
  public Deck()
  {
    shuffle();
  }
  
  /**
  @return: number of Card objects left in deck array
  */
  public int numLeft()
  {
    return deck.length;
  }

  /**
  @return: Card object at deck array index 0
  removes index 0 Card object from deck array
  */
  public Card deal()
  {
    Card output = deck[0];
    Card[] deckRevision = new Card[deck.length - 1];
    for (int i = 0; i<deckRevision.length; i++)
    {
      deckRevision[i] = deck[i+1];
    }
    deck = deckRevision;
    return output;
  }

  /**
  Creates array of 52 Card objects and sets deck to new array
  Randomly shuffles deck array of 52 Card objects so that array is no longer in order
  */
  public void shuffle()
  {
    int k = 0;
    int m = 0;
    Card temp = new Card();
    Card[] tempDeck = new Card[52];
    for(int i = 0; i<4; i++)
    {
      for (int j = 0; j<13; j++)
      {
      tempDeck[j + k] = new Card(Card.SUITS[i],Card.VALUES[j]);
      }
      k+= 13;
    }
    deck = tempDeck;
    for (int l = 51; l>0; l--)
    {
      m = (int) (Math.random() * l);
      temp = deck[l];
      deck[l] = deck[m];
      deck[m] = temp; 
    }
  }
}