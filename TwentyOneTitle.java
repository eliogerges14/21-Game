import javafx.scene.control.TextField;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TwentyOneTitle extends TwentyOne {
	//override the start method
	//use @Override methods when programming in teams
    public static RandomAccessFile raf;
    public static String plName = "You";
    private ArrayList<String> playerNames = null;
    Stage titleStage, pUp;
    Scene popUp;
    BorderPane popUpPane;
    
    
	@Override
	public void start(Stage primaryStage){
		titleStage = primaryStage;
		
		BorderPane borderPane = new BorderPane();
		borderPane.setStyle("-fx-background-color: radial-gradient(center 50% -40%, radius 200%, #355e3b 45%, #1d3420 50%)");
		borderPane.setPadding(new Insets(120, 20, 20, 20));
		
		borderPane.setCenter(getGridCenter());
		borderPane.setBottom(getHBoxBottom());
		borderPane.setTop(getVBoxTop());

		
		Scene scene = new Scene(borderPane, 800, 600);
		primaryStage.setTitle("21: The Game");
		primaryStage.setScene(scene);
		primaryStage.show();	
	}
	public GridPane getGridCenter(){
		try {
			//writes all the game data to the file gamedata.dat
			raf = new RandomAccessFile("gamedata.dat", "rw");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		GridPane pane = new GridPane();
	    pane.setAlignment(Pos.CENTER);
	    pane.setHgap(5.5);
	    pane.setVgap(5.5);
	    
	    // Place nodes in the pane
	    Label user = new Label("Username:");
	    user.setFont(Font.font("Courier", FontWeight.BOLD,12));
	    user.setTextFill(Color.WHITE);
	    pane.add(user, 0, 15);
	    TextField playerTxt = new TextField();
	    playerTxt.setPrefWidth(200);
	    pane.add(playerTxt, 1, 15);
	    
	    try {
	    	playerNames = readFile(raf);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    
	    
	    ComboBox<String> cbo = new ComboBox<>();
	    cbo.setPrefWidth(200);
	    pane.add(cbo,1,17);
	    
	    //reads all existing user names into the file
	    try {
			readFile(raf, cbo);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	       
	    Button addBtn = new Button("+ Add");
	    addBtn.setPrefWidth(200);
	    pane.add(addBtn,1,16);
	    
	    addBtn.setOnAction(e -> {if(checkPlayerName(playerTxt.getText(), playerNames) == false){
		    							plName = playerTxt.getText();
										System.out.println(plName);
										cbo.getItems().add(plName);
										try {
											add2File(raf, plName);
										} catch (IOException e1) {
											e1.printStackTrace();
										}
	    							}
								  	else{
								  		popUp();
								    }
								});
	    
	    Button selectBtn = new Button("Select");
	    selectBtn.setOnAction(e -> {plName = cbo.getValue();
	    							System.out.println("PLAYER SELECTED");
	    							//add method to get player info (wins, rounds, etc.)
	    							});
	    pane.add(selectBtn,0,17);
	       
	    Label author = new Label("Created By: Elio Gerges");
		author.setFont(Font.font("Courier",12));
		author.setTextFill(Color.WHITE);
		pane.add(author, 1 , 40);
		
		return pane;		
	}
	
	private boolean checkPlayerName(String name, ArrayList<String> names) {
		ArrayList<String> playerNames = names;
		String playerName = name;
		for(int i = 0; i < playerNames.size(); i++){
			
			if(playerName.equals(playerNames.get(i).trim())){
				return true;
			}
		}
		return false;

	}
	
	private void popUp(){
		pUp = new Stage();
  		popUpPane = new BorderPane();
  		popUpPane.setStyle("-fx-background-color: radial-gradient(center 50% -40%, radius 200%, #355e3b 45%, #1d3420 50%)");
  		popUpPane.setPadding(new Insets(20, 20, 20, 20));
  		HBox btnPane = new HBox(10);
  		VBox text = new VBox(2);
  		btnPane.setAlignment(Pos.CENTER);
  		text.setAlignment(Pos.CENTER);

         
        Text t1 = new Text("The username already exists.");
        t1.setFill(Color.WHITE);
        t1.setFont(Font.font("Courier",12));
        
        Text t2 = new Text("Would you like to override player data? ");
        t2.setFill(Color.WHITE);
        t2.setFont(Font.font("Courier",12));
        
        text.getChildren().addAll(t1,t2);
        
        Button yesBtn = new Button("Yes");
        Button noBtn = new Button("No");
        
        noBtn.setOnAction(e -> pUp.close());
        //yesBtn.setOnAction(e -> );
        
        btnPane.getChildren().addAll(yesBtn, noBtn);
        
        popUpPane.setCenter(text);
        popUpPane.setBottom(btnPane);
        
        popUp = new Scene(popUpPane, 350, 250);
        pUp.setScene(popUp);
        pUp.initModality(Modality.APPLICATION_MODAL);
        pUp.setTitle("21: The Game");
        pUp.showAndWait();
    	System.out.println("SAME_NAME");	
	}
	
	private HBox getHBoxBottom(){
		Button strBtn = new Button("Start");
		Button exBtn = new Button("Exit");
		
		strBtn.setPrefWidth(120);
		exBtn.setPrefWidth(120);

		HBox hBox = new HBox();
		hBox.setSpacing(10);
		hBox.setAlignment(Pos.CENTER);
			    
	    hBox.getChildren().addAll(strBtn,exBtn);
	    
	    //when start is pressed, the title screen closes and the game appears
	    strBtn.setOnAction(e -> {super.start(gameStage); 
	    							titleStage.close();
	    							});
	  //exits the game if pressed
	    exBtn.setOnAction(e -> { //save player information
	    							System.exit(0);});
		return hBox;
	}

	private VBox getVBoxTop(){
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		Label label = new Label("21");
		label.setTextFill(Color.WHITE);
		label.setFont(Font.font("Courier", FontWeight.BOLD,55));
		
		vBox.getChildren().add(label);		
		return vBox;
	}
	
	public static String getPlayerName(){
		//removes the extra spaces
		return plName.trim();
		}
	
	 private static void add2File(RandomAccessFile raf, String name) throws IOException
	 {
		raf.writeUTF(format(name,32));	 
	 }
	 
	 private static void readFile(RandomAccessFile raf, ComboBox<String> c) throws IOException
	 {

	   //move pointer to beginning
	   try {
		   raf.seek(0);
	   } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   }
	   
	   //Below is a cleaner way of reading the data
	   try{
		   do {
			   c.getItems().add(raf.readUTF());
		   }
		   while(raf.getFilePointer() < raf.length());
	   }
	   catch(IOException e){
		   e.getMessage();
	   }
	 }
	 
	 private static ArrayList<String> readFile(RandomAccessFile raf) throws IOException
	 {
	   ArrayList<String> names = new ArrayList<>();
	   System.out.println("Reading file...\n");
	   //move pointer to beginning
	   try {
		   raf.seek(0);
	   } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   }
	   
	   //Below is a cleaner way of reading the data
	   try{
		   do {
			   names.add(raf.readUTF());
		   }
		   while(raf.getFilePointer() < raf.length());
	   }
	   catch(IOException e){
		   e.getMessage();
	   }
	   return names;
	 }
	 
	 
	 private static String format(String s, int length)
	 {
	   return String.format("%-" + length + "s" , s);
	 }
	 	
	public static void main(String[] args){
		Application.launch(args);
	}
}

