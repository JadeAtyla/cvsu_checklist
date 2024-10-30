package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CoursePopInfo extends Stage {
	
	Stage subStage = new Stage();
	private String subjectName;
	private String courseCode;
	private String profName;
	private String grade;
	private String equivalent;
	
	private int year;
	private int semester;
	private int cuLabTxt;
	private int cuLecTxt;
	private int chLabTxt;
	private int chLecTxt;
	
	public CoursePopInfo(String subjectName, String courseCode, String profName, String grade, String equivalent,
            int year, int semester, int cuLab, int cuLec, int chLab, int chLec) {
		this.subjectName = subjectName;
		this.courseCode = courseCode;
		this.profName = (profName.isEmpty()||profName.isBlank()) ? "No Pofessor Yet":profName;
		this.grade = grade;
		this.equivalent = equivalent;
		
		this.year = year;
		this.semester = semester;
		this.cuLabTxt = cuLab;
		this.cuLecTxt = cuLec;
		this.chLabTxt = chLab;
		this.chLecTxt = chLec;
		
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
		
		Scene scene = new Scene(root, 579, 514);
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
		
		Label subjectLbl = new Label("\""+subjectName+"\"");
		Label subCodeLbl = new Label(courseCode);
		Label profNameLbl = new Label(profName);
		
		subjectLbl.setLayoutY(31);
		subCodeLbl.setLayoutY(56);
		profNameLbl.setLayoutY(113);
		
		subjectLbl.setPrefWidth(scene.getWidth());
		subCodeLbl.setPrefWidth(scene.getWidth());
		profNameLbl.setPrefWidth(scene.getWidth());
		
		subjectLbl.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 20; -fx-text-fill: #144C19; -fx-alignment: center;");
		subCodeLbl.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 48; -fx-text-fill: #144C19; -fx-alignment: center;");
		profNameLbl.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 20; -fx-text-fill: #144C19; -fx-alignment: center;");
		
		Label gradeLbl = new Label("Grade:");
		Label equiLbl = new Label("Equivalent:");
		Label yrAndSemLbl = new Label("Year and Semester");
		
		gradeLbl.setPrefSize(180, 30);
		equiLbl.setPrefSize(180, 30);
		yrAndSemLbl.setPrefSize(180, 30);
		
		gradeLbl.setLayoutX(85);
		equiLbl.setLayoutX(85);
		yrAndSemLbl.setLayoutX(85);
		
		gradeLbl.setLayoutY(186);
		equiLbl.setLayoutY(259);
		yrAndSemLbl.setLayoutY(333);
		
		gradeLbl.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 20; -fx-text-fill: #144C19;");
		equiLbl.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 20; -fx-text-fill: #144C19;");
		yrAndSemLbl.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 20; -fx-text-fill: #144C19;");
		
		PlaceholderTextField gradeField = new PlaceholderTextField("Not graded yet...");
		PlaceholderTextField equiField = new PlaceholderTextField("No equivalent...");
		PlaceholderTextField yrAndSemField = new PlaceholderTextField("Year and semester not found..");
		
		gradeField.setPrefSize(417, 30);
		equiField.setPrefSize(417, 30);
		yrAndSemField.setPrefSize(417, 30);
		
		gradeField.setLayoutX(81);
		equiField.setLayoutX(81);
		yrAndSemField.setLayoutX(81);
		
		gradeField.setLayoutY(217);
		equiField.setLayoutY(290);
		yrAndSemField.setLayoutY(370);
		
		gradeField.setStyle("-fx-background-radius: 20; -fx-font-family: 'Serif'; -fx-font-size: 14;");
		equiField.setStyle("-fx-background-radius: 20; -fx-font-family: 'Serif'; -fx-font-size: 14;");
		yrAndSemField.setStyle("-fx-background-radius: 20; -fx-font-family: 'Serif'; -fx-font-size: 14;");
		
		gradeField.setText(grade);
		equiField.setText(equivalent);
		yrAndSemField.setText(year+"-"+semester);
		
		gradeField.setEditable(false);
		equiField.setEditable(false);
		yrAndSemField.setEditable(false);
		
		Label cuLec	 = new Label("Credit Unit Lec: " + cuLecTxt);
		Label cuLab = new Label("Credit Unit Lab: " + cuLabTxt);
		Label chLec = new Label("Contact Hour Lec: " + chLecTxt);
		Label chLab = new Label("Contact Hour Lab: " + chLabTxt);
		
		cuLec.setPrefSize(196, 20);
		cuLab.setPrefSize(196, 20);
		chLec.setPrefSize(196, 20);
		chLab.setPrefSize(196, 20);
		
		cuLec.setLayoutX(91);
		cuLab.setLayoutX(91);
		chLec.setLayoutX(302);
		chLab.setLayoutX(302);
		
		cuLec.setLayoutY(416);
		cuLab.setLayoutY(437);
		chLec.setLayoutY(416);
		chLab.setLayoutY(437);
		
		cuLec.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 14; -fx-text-fill: #144C19;");
		cuLab.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 14; -fx-text-fill: #144C19;");
		chLec.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 14; -fx-text-fill: #144C19;");
		chLab.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 14; -fx-text-fill: #144C19;");
		
		root.getChildren().addAll(bg, exit, subjectLbl, subCodeLbl, profNameLbl, gradeLbl, equiLbl, yrAndSemLbl, gradeField, equiField, yrAndSemField, chLab, chLec, cuLab, cuLec);
		
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
