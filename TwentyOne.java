import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.shape.Rectangle;


public class TwentyOne extends Application
{	
	public Stage gameStage = new Stage();
	public boolean userT;
	public boolean pcT;
	public boolean alreadyHit = false;
	public Text pStay = new Text(" Stayed");
	public Text pHit = new Text(" Hit");
	public int numOfRoundWins = 0;
	public int numOfTotalWins = 0;
	
	public void start(Stage pStage)
	{
	  gameStage = pStage;
      BorderPane pane = new BorderPane();
      pane.setTop(getTop());
      pane.setCenter(getMiddle());
      pane.setBottom(getBottom());
      pane.setStyle("-fx-background-color: radial-gradient(center 50% -40%, radius 200%, #355e3b 45%, #1d3420 50%)");
     
      
      Scene scene = new Scene(pane);
         
      pStage.setTitle("21");
      pStage.setScene(scene);
      pStage.setMaximized(true);
      pStage.show();
      
   }

   private GridPane getMiddle()
   {  
       GridPane gp = new GridPane();
       gp.setAlignment(Pos.CENTER);
       gp.setPadding(new Insets(11.5, 12.5, 11.5, 14.5));
       gp.setHgap(5.5);
       gp.setVgap(5.5);
      
       Button stay = new Button("Stay");
	   Button hit = new Button("Hit");
	   
	   stay.setPrefWidth(150);
	   hit.setPrefWidth(150);
	   
	   stay.setPrefHeight(30);
	   hit.setPrefHeight(30);
	  
	   gp.add(stay, 2, 2);
	   gp.add(hit, 3, 2);
	  
	   Deck myDeck = new Deck(true);
	   
	   Player user = new Player(TwentyOneTitle.getPlayerName());
	   Player computer = new Player("Computer");
	   
	   user.addCard(myDeck.dealCard());
	   computer.addCard(myDeck.dealCard());
	   user.addCard(myDeck.dealCard());
	   computer.addCard(myDeck.dealCard());
	   
	   gp.add(addCards("?"), 0, 0);
	   gp.add(addCards(computer.getHand(1)), 1, 0);
	    
	   Text pcSum = new Text("? + "+computer.getHandSum(1)+"/21");
	   pcSum.setFill(Color.WHITE);
	   pcSum.setFont(Font.font("Courier", FontWeight.BOLD, 25));
	   gp.add(pcSum, 10, 0);
	      
	   gp.add(addCards(user.getHand(0)), 0, 1);
	   gp.add(addCards(user.getHand(1)), 1, 1);

	   Text playerSum = new Text(user.getHandSum()+"/21");
	   playerSum.setFill(Color.WHITE);
	   playerSum.setFont(Font.font("Courier", FontWeight.BOLD, 25));
	   gp.add(playerSum, 10, 1);
	   
	   boolean userIsDone = false;
	   boolean computerIsDone = false;
	   
	   userT = userIsDone;
	   pcT = computerIsDone;
	   
	   if(!userT && user.getHandPos() < 6){
			   /**
			    * Action Event
			    * If the hit button is pressed,
			    * There is an if statement checking if the handPos + 1 is the 6 or the max
			    * draws a card then adds it to the grid pane
			    */
			  //HIT
			  hit.setOnAction(e -> {
			  if(user.getHandPos() + 1 < 6){
				  	user.addCard(myDeck.dealCard());
				  	gp.add(addCards(user.getHand(user.getHandPos())), user.getHandPos(), 1); 
				  	alreadyHit = true;
				  			  	
					if(!pcT && computer.getHandPos() < 6 && alreadyHit){
						if(computer.getHandSum() < 17){
							   computer.addCard(myDeck.dealCard());
							   gp.add(addCards(computer.getHand(computer.getHandPos())), computer.getHandPos(), 0);
							   alreadyHit = false;
							   
							   pStay = new Text("Computer Hit");
							   pStay.setFill(Color.WHITE);
							   pStay.setFont(Font.font("Courier", FontWeight.BOLD, 15));
							   gp.add(pStay, 11, 0);
							   
					 } else {
						   pcT = true;
						   pStay = new Text("Computer Stayed");
						   pStay.setFill(Color.WHITE);
						   pStay.setFont(Font.font("Courier", FontWeight.BOLD, 15));
						   gp.add(pStay, 11, 1);
						}		   
		   		   }		
			  }
		});
			//STAY
			stay.setOnAction(e -> {
				userT = true;
				
				if(!pcT && computer.getHandPos() < 6){
					if(computer.getHandSum() < 17){
						   computer.addCard(myDeck.dealCard());
						   gp.add(addCards(computer.getHand(computer.getHandPos())), computer.getHandPos(), 0);
						   
						   pStay = new Text("Computer Hit");
						   pStay.setFill(Color.WHITE);
						   pStay.setFont(Font.font("Courier", FontWeight.BOLD, 15));
						   gp.add(pStay, 11, 0);			   
					   }
				}
					else{
					   pcT = true;
					   pStay = new Text("Computer Stayed");
					   pStay.setFill(Color.WHITE);
					   pStay.setFont(Font.font("Courier", FontWeight.BOLD, 15));
					   gp.add(pStay, 11, 1);
				}
				if(pcT && userT){
					if((user.getHandSum() > computer.getHandSum() && user.getHandSum() < 21 )|| computer.getHandSum() > 21){
						System.out.println("You WIN");
						numOfRoundWins++;
						//if 5 round wins, then the game score is 1.
						if(numOfRoundWins == 5){
							numOfTotalWins++;
						}
					}
					if((user.getHandSum() > computer.getHandSum() && computer.getHandSum() < 21 )|| user.getHandSum() > 21){
						System.out.println("Computer WINS");
				}
			   }
			});
	   		}
	   return gp;
	  }   

   private StackPane addCards(String s)
   {
      StackPane sp = new StackPane();
      
      Rectangle r = new Rectangle(0, 0, 150, 200);
      r.setFill(Color.WHITE);
      Text text = new Text(5, 10, s);
      text.setFont(Font.font("Courier", FontWeight.BOLD, 35));
      sp.getChildren().add(r);
      sp.getChildren().add(text);
      
      return sp;
   }
   
   private HBox getComputerGameArea()
   {
      HBox hbox = new HBox();
      initGameArea(hbox);
      return hbox;   
   }
   
   private HBox getPlayerGameArea()
   {
      HBox hbox = new HBox();
      initGameArea(hbox);
      return hbox;
   }
   
   private void initGameArea(HBox hbox)
   {
      for(int i=0; i<7; i++)
      {
         hbox.getChildren().add(new Label(String.valueOf(i)));
      }
      
      hbox.getChildren().add(new Label(" /21"));
   }
   
   private FlowPane getTop()
   {
      FlowPane fp = new FlowPane();
      
      setInfoArea(fp, "Computer: 0 wins", Color.BLACK);
      
      return fp;
   }
   
   private FlowPane getBottom()
   {
      FlowPane fp = new FlowPane();

      
      setInfoArea(fp, TwentyOneTitle.getPlayerName() + ": 0 wins", Color.WHITE);
      return fp;
   }
   
   private void setInfoArea(FlowPane fp, String s, Color c)
   {
      Text text = new Text(0, 0, s);
      
      text.setFont(Font.font("Courier", FontWeight.BOLD, 40));
      text.setFill(c);
      fp.getChildren().add(text);
   }
   
   
   public static void main(String[] args){	  
	   Application.launch(args);	
		}
	}


//Creating a card object
class Card{
	private int cardNumber;
	
	public Card(){
		
	}
	
	public Card(int num){
		this.cardNumber = num;
	}
	
	public int getCardNumber(){
		return cardNumber;
	}
	
	@Override
	public String toString(){
		String number = "";
		
		switch(cardNumber){
			case 1:
				number = "1";
				break;
			case 2:
				number = "2";
				break;
			case 3:
				number = "3";
				break;
			case 4:
				number = "4";
				break;
			case 5:
				number = "5";
				break;
			case 6:
				number = "6";
				break;
			case 7:
				number = "7";
				break;
			case 8:
				number = "8";
				break;
			case 9:
				number = "9";
				break;
			case 10:
				number = "10";
				break;
			case 11:
				number = "11";
				break;
		}
		
		return number;
	}
	
}

//Creats the deck of cards
class Deck{
	private ArrayList<Card> myCards = new ArrayList<Card>();
	
	private final static int NUM_OF_CARDS = 11;
	
	public Deck(){
		Deck d = new Deck(false);
	}
	
	public Deck(boolean shuffle){
		
		for(int i = 1; i <= NUM_OF_CARDS; i++){
			myCards.add(new Card(i));
		}
		
		if(shuffle == true){
			Collections.shuffle(myCards);
		}
	}
	//returns a card
	public Card dealCard(){
		Card topCard = myCards.get(0);	
		
		myCards.remove(0);
		
		return topCard;
		
	}
	
	public void printdeck(){
		System.out.println(myCards.toString());
	}
	
	public static int getDeckSize(){
		return NUM_OF_CARDS;
	}
}

class Player extends TwentyOne{
	
	private String playerName;
	
	private ArrayList<Card> hand = new ArrayList<>();
	
	private int numCardsInHand;
	
	public Player(String name){
		this.playerName = name;
		
		//set a player with an empty hand
		this.emptyHand();
	}
	public void emptyHand(){
		
		hand.clear();
		numCardsInHand = 0;
		}
	public boolean addCard(Card card){
		hand.add(card);
		
		return (getHandSum() <= 21);
		}
	public int getHandSum(){
		int cardSum = 0;
		
		for(int i = 0; i < hand.size(); i++){
			cardSum += hand.get(i).getCardNumber();
		}
		return cardSum;
	}
		
	public int getHandSum(int index){
			int cardSum = 0;
			
			for(int i = index; i < hand.size(); i++){
				cardSum += hand.get(i).getCardNumber();
			}
		return cardSum;
		
		}
	public void printHand(){
		System.out.println(hand.toString());
	}
	public String getHand(int index){
		String s = hand.get(index).toString();
		return s;
	}
	public int getHandPos(){
		return hand.size() - 1;
	}
}
	


