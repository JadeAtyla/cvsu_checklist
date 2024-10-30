package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage1) {
		try {
			primaryStage1 = new Frame("202211802"); //default view
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
