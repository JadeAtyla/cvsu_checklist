package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StudentPopInfo extends Stage {
	
	Stage subStage = new Stage();
	
    private String name;
    private String studentNumber;
    private String program;
    private String address;
    private String contact;
    
    public StudentPopInfo(String name, String studentNumber, String program, String address, String contact) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.program = program;
        this.address = address;
        this.contact = contact;
        
        initComponents();
        show();
        this.setAlwaysOnTop(true);
    }

	
	public Stage blurBg(int width, int height) {
		Pane root = new Pane();
		root.setStyle("-fx-background-color: black;");
		Scene scene = new Scene(root, width, height);
		subStage.setScene(scene);
		subStage.initStyle(StageStyle.UNDECORATED);
		subStage.setOpacity(0.3);
		subStage.show();
		return subStage;
	}
	
	private void initComponents() {
		Pane root = new Pane();
		
		Scene scene = new Scene(root, 579, 384);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		setScene(scene);
		initStyle(StageStyle.UNDECORATED);
		
		Button exit = new Button("x");
		exit.setStyle("-fx-background-radius: 50; -fx-font-family: 'Serif'; -fx-text-fill: #144C19;");
		exit.setOnAction(e->{
			close();
			subStage.close();
		});
		exit.setLayoutX(533);
		exit.setLayoutY(13);
		exit.setPrefSize(30, 30);
		
		Pane bg = new Pane();
		bg.setStyle("-fx-background-color: #ABDE77;");
		bg.setPrefSize(scene.getWidth(), 161);
		bg.setEffect(dropshadow(Color.GRAY, 0, 5, 10, 0));
		
		Label programLbl = new Label("\""+program+"\"");
		Label subCodeLbl = new Label(studentNumber);
		Label profNameLbl = new Label(name);
		
		programLbl.setLayoutY(31);
		subCodeLbl.setLayoutY(56);
		profNameLbl.setLayoutY(113);
		
		programLbl.setPrefWidth(scene.getWidth());
		subCodeLbl.setPrefWidth(scene.getWidth());
		profNameLbl.setPrefWidth(scene.getWidth());
		
		programLbl.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 20; -fx-text-fill: #144C19; -fx-alignment: center;");
		subCodeLbl.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 48; -fx-text-fill: #144C19; -fx-alignment: center;");
		profNameLbl.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 20; -fx-text-fill: #144C19; -fx-alignment: center;");
		
		Label addressLbl = new Label("Address:");
		Label contactLbl = new Label("Contact No.:");
		
		addressLbl.setPrefSize(180, 30);
		contactLbl.setPrefSize(180, 30);
		
		addressLbl.setLayoutX(85);
		contactLbl.setLayoutX(85);
		
		addressLbl.setLayoutY(186);
		contactLbl.setLayoutY(259);
		
		addressLbl.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 20; -fx-text-fill: #144C19;");
		contactLbl.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 20; -fx-text-fill: #144C19;");
		
		PlaceholderTextField addressField = new PlaceholderTextField("No address found...");
		PlaceholderTextField contactField = new PlaceholderTextField("No contact found...");
		
		addressField.setPrefSize(417, 30);
		contactField.setPrefSize(417, 30);
		
		addressField.setLayoutX(81);
		contactField.setLayoutX(81);
		
		addressField.setLayoutY(217);
		contactField.setLayoutY(290);
		
		addressField.setStyle("-fx-background-radius: 20; -fx-font-family: 'Serif'; -fx-font-size: 14;");
		contactField.setStyle("-fx-background-radius: 20; -fx-font-family: 'Serif'; -fx-font-size: 14;");
		
		addressField.setText(address);
		contactField.setText(contact);
		
		addressField.setEditable(false);
		contactField.setEditable(false);
		
		root.getChildren().addAll(bg, programLbl, subCodeLbl, profNameLbl, addressLbl, contactLbl, addressField, contactField, exit);
		
	}
	
	private DropShadow dropshadow(Color color, int x, int y, int blur, int spread) {
		DropShadow dropShadow = new DropShadow();
		dropShadow.setOffsetX(x);
		dropShadow.setOffsetY(y);
		dropShadow.setRadius(blur);
		dropShadow.setSpread(spread);
		dropShadow.setColor(color);
		return dropShadow;
	}
}
