package application;

import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Optional;
import fetchsql.SetConnection;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Frame extends Stage{
	
	Measurement m = new Measurement();
	int WIDTH = m.WIDTH;
	int HEIGHT = m.HEIGHT;
	
	private ArrayList<String> name;
	private ArrayList<String> stdn;
	private ArrayList<String> prgrm;
	
	private SetConnection sql = new SetConnection();
	private PlaceholderTextField search;
	
	private Menu fileMenu;
	private Menu yearSemMenu;
	private MenuItem sb;
	private MenuItem scc;
	private MenuItem sba;
	private MenuItem sbd;
	private MenuItem sbhg;
	private MenuItem sblg;
    
	private MenuItem sby;
	private MenuItem firstSort;
	private MenuItem secondSort;
	private MenuItem thirdfirstSort;
	private MenuItem fourthSort;
    
	private String sortSelection = "";
	private String addon = "";
    
    private String studentNum = "202211802";
    
    private ArrayList<String> nameList = new ArrayList<>();
	private ArrayList<String> studentNumList = new ArrayList<>();
	private ArrayList<String> programList = new ArrayList<>();
	private ArrayList<String> addressList = new ArrayList<>();
	private ArrayList<String> contactList = new ArrayList<>();
	
	private ArrayList<String> crseCode = new ArrayList<>();
	private ArrayList<String> subjectName = new ArrayList<>();
	private ArrayList<String> prfName = new ArrayList<>();
	private ArrayList<String> grde = new ArrayList<>();
	private ArrayList<String> equi = new ArrayList<>();
	private ArrayList<Integer> yr = new ArrayList<>();
	private ArrayList<Integer> sm = new ArrayList<>();
	private ArrayList<Integer> cuLab = new ArrayList<>();
	private ArrayList<Integer> cuLec = new ArrayList<>();
	private ArrayList<Integer> chLab = new ArrayList<>();
	private ArrayList<Integer> chLec = new ArrayList<>();
	
	public Frame(String studentNum) {
		this.studentNum = studentNum;
		initComponents();
		retrieveData();
		show();
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
	
	public void initComponents() {
		Pane root = new Pane();
		Button b = new Button("x");
		b.setStyle("-fx-background-radius: 50; -fx-font-family: 'Serif'; -fx-text-fill: #144C19;");
		 b.setOnAction(e -> {
	            Alert alert = new Alert(AlertType.CONFIRMATION);
	            alert.setHeaderText(null);
	            alert.setContentText("Are you ready to exit?");
	            alert.showAndWait().ifPresent(response -> {
	                if (response == ButtonType.OK) {
	                    close();
	                }
	            });
	        });
		b.setLayoutX(1307);
		b.setLayoutY(25);
		b.setPrefSize(30, 30);
		
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		setScene(scene);
		initStyle(StageStyle.UNDECORATED);
		
		Pane upperPanel = new Pane();
		upperPanel.setPrefSize(WIDTH, 298);
		upperPanel.setStyle("-fx-background-color: #ABDE77;");
		upperPanel.setEffect(dropshadow(Color.GRAY, 5, 5, 10, 0));
		
		Image image = new Image(getClass().getResourceAsStream("/images/cvsu_logo.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100); 
        imageView.setFitHeight(100); 
		imageView.setPreserveRatio(true);
        
		Label cvsuLogo = new Label();
		cvsuLogo.setPrefSize(50, 50);
		cvsuLogo.setLayoutX(130);
		cvsuLogo.setLayoutY(30);
		cvsuLogo.setGraphic(imageView);
		
		Text chklstText = new Text("   CHECKLIST\nINFORMATION");
		chklstText.setTextAlignment(TextAlignment.CENTER);
		
		Label chklstLbl = new Label(chklstText.getText());
		chklstLbl.setLayoutX(70);
		chklstLbl.setLayoutY(120);
		chklstLbl.setPrefSize(470, 100);
		chklstLbl.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 32; -fx-text-fill: #144C19;");
		
		name = new ArrayList<String>();
		stdn = new ArrayList<String>();
		prgrm =  new ArrayList<String>();
		
		sql.openConnection();
		sql.getStudenProgramInfo(studentNum, name, stdn, prgrm);
		sql.closeConnection();
		
		Label nameLbl;
		try {
			nameLbl = new Label("Name: " + name.get(0));
		} catch(Exception e) {
			nameLbl = new Label("Name: Not Found");
		}
		nameLbl.setLayoutX(20);
		nameLbl.setPrefSize(361, 39);
		nameLbl.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 19; -fx-text-fill: white;");
		
		Label stdnLbl; 
		try{
			stdnLbl = new Label("Student Number: " + stdn.get(0));
		}catch(Exception e) {
			stdnLbl = new Label("Student Number: " + studentNum);
		}
		stdnLbl.setLayoutX(nameLbl.getText().length()*12);
		stdnLbl.setPrefSize(361, 39);
		stdnLbl.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 19; -fx-text-fill: white;");
		
		Label prgrmLbl;
		try{
			prgrmLbl = new Label("Program: " + prgrm.get(0));
		}catch(Exception e) {
			prgrmLbl = new Label("Program: Not Found");
		}
		prgrmLbl.setLayoutX((nameLbl.getText().length()*12)+(stdnLbl.getText().length()*12));
		prgrmLbl.setPrefSize(361, 39);
		prgrmLbl.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 19; -fx-text-fill: white;");
		
		Pane personalPeekInfo = new Pane();
		personalPeekInfo.setLayoutX(426);
		personalPeekInfo.setLayoutY(75);
		personalPeekInfo.setPrefSize(899, 40);
		personalPeekInfo.setStyle("-fx-background-radius: 15; -fx-background-color: #144C19;");
		personalPeekInfo.setOpacity(0.7);
		personalPeekInfo.getChildren().addAll(nameLbl, stdnLbl, prgrmLbl);
		
		Button showCmpltInfo = new Button("Show Info");
		Button changeStudent = new Button("Change Student");
		
		showCmpltInfo.setLayoutX(53);
		showCmpltInfo.setLayoutY(236);
		showCmpltInfo.setPrefSize(120, 30);
		showCmpltInfo.setStyle("-fx-background-color: #25742F; -fx-background-radius: 20; -fx-text-fill: white; -fx-font-family: 'Serif'; -fx-font-size: 14;");
		showCmpltInfo.setOpacity(0.9);
		showCmpltInfo.setOnAction(e->{
			for(int i = 0; i<studentNumList.size(); i++) {
				if(studentNumList.get(i).equalsIgnoreCase(studentNum)) {
					StudentPopInfo pop = new StudentPopInfo(nameList.get(i), studentNumList.get(i), programList.get(i), addressList.get(i), contactList.get(i));
					pop.blurBg(WIDTH, HEIGHT);
				} else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
			        alert.setHeaderText(null);
			        alert.setContentText("No student information to view.");
			        alert.show();
			        
				}
			}
		});
		
		changeStudent.setLayoutX(180);
		changeStudent.setLayoutY(236);
		changeStudent.setPrefSize(120, 30);
		changeStudent.setStyle("-fx-background-color: #25742F; -fx-background-radius: 20; -fx-text-fill: white; -fx-font-family: 'Serif'; -fx-font-size: 14;");
		changeStudent.setOpacity(0.9);
		changeStudent.setOnAction(e -> {
		    TextInputDialog dialog = new TextInputDialog();
		    dialog.setTitle("Change Student");
		    dialog.setHeaderText("Enter Student Number: ");

		    Optional<String> result = dialog.showAndWait();
		    result.ifPresent(studentNumber -> {
		        studentNum = studentNumber;
		        close();
		        Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        alert.setHeaderText(null);
		        alert.setContentText("Student has been changed.\nSearching for your student number...\n(Press enter or ok to continue)");
		        alert.showAndWait();
		       
		        new Frame(studentNum); // Refresh the stage and its components
		        
		    });
		});
		
		
		search = new PlaceholderTextField("Search for courses, etc..."); 
		search.setLayoutX(127);
		search.setLayoutY(344);
		search.setPrefSize(417, 30);
		search.setStyle("-fx-background-radius: 20");
		
		root.getChildren().addAll(upperPanel, b, chklstLbl, personalPeekInfo, showCmpltInfo, changeStudent, cvsuLogo, infoContainer(), search, yearSemMenuBar(), menuBar(), scrollPad());
	}
	
	private HBox infoContainer() {
		HBox pane = new HBox(10);
		pane.setLayoutX(426);
		pane.setLayoutY(121);
		pane.setPrefSize(899, 150);
		
		String[] headerStr = {"Courses Taken", "Courses Left", "Failed Courses"};
		ArrayList<Integer> info = new ArrayList<Integer>();
		sql.openConnection();
		sql.getCourseInfo(studentNum, info);
		sql.closeConnection();
		
		for(int i = 0; i<headerStr.length; i++) {
			pane.getChildren().add(container(headerStr[i], info.get(i)));
		}
		
		
		return pane;
	}
	
	private Pane container(String headerStr, int numTextStr) {
		Pane container = new Pane();
		container.setPrefSize(293, 150);
		container.setStyle("-fx-background-radius: 20; -fx-background-color: #144C19;");
		container.setOpacity(0.7);
		
		Label header = new Label("Number of\n"+headerStr);
		header.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 24; -fx-text-fill: white;");
		header.setLayoutX(92);
		header.setLayoutY(26);
		
		Label numText = new Label("Total: " + numTextStr);
		numText.setStyle("-fx-font-family: 'Serif'; -fx-font-size: 16; -fx-font-style: italic; -fx-text-fill: white;");
		numText.setLayoutX(92);
		numText.setLayoutY(96);
		
		container.getChildren().addAll(header, numText);
		return container;
	}
	
	private MenuBar menuBar() {
		MenuBar menuBar = new MenuBar();
		menuBar.setLayoutX(1050);
		menuBar.setLayoutY(344);
		menuBar.setPrefSize(180, 30);
		menuBar.setStyle("-fx-background-color: transparent; -fx-border-color: #144C19; -fx-border-width: 1px; -fx-border-radius: 20px; -fx-font-size: 12px; -fx-text-fill: #144C19;");
        
		sb = new MenuItem("Sort by\t\t\t");
        scc = new MenuItem("Sort by Course Code");
        sba = new MenuItem("Sort by Ascending");
        sbd = new MenuItem("Sort by Descending");
        sbhg = new MenuItem("Sort by Highest Grade");
        sblg = new MenuItem("Sort by Lowest Grade");
        
        fileMenu = new Menu(sb.getText());
        
        fileMenu.getItems().addAll(sb, scc, sba, sbd, sbhg, sblg);
        menuBar.getMenus().add(fileMenu);
		return menuBar;
	}
	
	private MenuBar yearSemMenuBar() {
		MenuBar menuBar = new MenuBar();
		menuBar.setLayoutX(1050-180-30);
		menuBar.setLayoutY(344);
		menuBar.setPrefSize(180, 30);
		menuBar.setStyle("-fx-background-color: transparent; -fx-border-color: #144C19; -fx-border-width: 1px; -fx-border-radius: 20px; -fx-font-size: 12px; -fx-text-fill: #144C19;");
        
		sby = new MenuItem("Sort by year");
		firstSort = new MenuItem("Sort by 1st Year");
		secondSort = new MenuItem("Sort by 2nd Year");
		thirdfirstSort = new MenuItem("Sort by 3rd Year");
		fourthSort = new MenuItem("Sort by 4th Year");
        
        yearSemMenu = new Menu(sby.getText());
        
        yearSemMenu.getItems().addAll(sby, firstSort, secondSort, thirdfirstSort, fourthSort);
        menuBar.getMenus().add(yearSemMenu);
		return menuBar;
	}
	
	private ScrollPane scrollPad() {
		GridPane gridPad = new GridPane();
		gridPad.setVgap(15);
		gridPad.setHgap(15);
		gridPad.setPadding(new Insets(10));
		
		ScrollPane scrollPad = new ScrollPane();
		scrollPad.setLayoutX(127);
		scrollPad.setLayoutY(380);
		scrollPad.setPrefSize(1102, 340);
		scrollPad.setContent(gridPad);
		
		ArrayList<String> courseCode = new ArrayList<String>();
		ArrayList<String> profName = new ArrayList<String>();
		ArrayList<String> grade = new ArrayList<String>();
		ArrayList<Integer> year = new ArrayList<Integer>();
		ArrayList<Integer> semester = new ArrayList<Integer>();
		
		//pre-loaded function for visble info
		sql.openConnection();
		sql.getCourseStash(studentNum,"", courseCode, profName, grade, year, semester, addon, sortSelection);
		sql.closeConnection();
		
		// default view for stash pad
		if(sql.searchFound) {
			stashPad(gridPad, courseCode, profName, grade, year, semester, addon, sortSelection);
		}else {
			noRecord(gridPad);
		}
		
		//search function
		search.setOnKeyReleased(e->{
			stashPad(gridPad, courseCode, profName, grade, year, semester, addon, sortSelection);
			gridPad.getChildren().clear();
			
			if(sql.searchFound) {
				stashPad(gridPad, courseCode, profName, grade, year, semester, addon, sortSelection);
			}else {
				noRecord(gridPad);
			}
		});
		
		// sorting function
		sb.setOnAction(e -> {
			gridPad.getChildren().clear();
			fileMenu.setText(sb.getText());
			yearSemMenu.setText(sby.getText());
			sortSelection = "order by grade_ID asc";
			addon = "";
			if(sql.searchFound) {
				stashPad(gridPad, courseCode, profName, grade, year, semester, addon, sortSelection);
			}else {
				noRecord(gridPad);
			}
		});
		scc.setOnAction(e -> {
			gridPad.getChildren().clear();
			fileMenu.setText(scc.getText());
			sortSelection = "order by course_code asc";
			if(sql.searchFound) {
				stashPad(gridPad, courseCode, profName, grade, year, semester, addon, sortSelection);
			}else {
				noRecord(gridPad);
			}
		});
		sba.setOnAction(e -> {
			gridPad.getChildren().clear();
			fileMenu.setText(sba.getText());
			sortSelection = "order by grade_ID asc";
			if(sql.searchFound) {
				stashPad(gridPad, courseCode, profName, grade, year, semester, addon, sortSelection);
			}else {
				noRecord(gridPad);
			}
		});
		sbd.setOnAction(e -> {
			gridPad.getChildren().clear();
			fileMenu.setText(sbd.getText());
			sortSelection = "order by grade_ID desc";
			if(sql.searchFound) {
				stashPad(gridPad, courseCode, profName, grade, year, semester, addon, sortSelection);
			}else {
				noRecord(gridPad);
			}
		});
		
		sbhg.setOnAction(e -> {
			gridPad.getChildren().clear();
			fileMenu.setText(sbhg.getText());
			sortSelection = "and grade is not null order by grade desc";
			if(sql.searchFound) {
				stashPad(gridPad, courseCode, profName, grade, year, semester, addon, sortSelection);
			}else {
				noRecord(gridPad);
			}
		});
		
		sblg.setOnAction(e -> {
			gridPad.getChildren().clear();
			fileMenu.setText(sblg.getText());
			sortSelection = "and grade is not null order by grade asc";
			if(sql.searchFound) {
				stashPad(gridPad, courseCode, profName, grade, year, semester, addon, sortSelection);
			}else {
				noRecord(gridPad);
			}
		});
		
		sby.setOnAction(e -> {
			gridPad.getChildren().clear();
			yearSemMenu.setText(sby.getText());
			addon = "";
			if(sql.searchFound) {
				stashPad(gridPad, courseCode, profName, grade, year, semester, addon, sortSelection);
			}else {
				noRecord(gridPad);
			}
		});
		
		firstSort.setOnAction(e -> {
			gridPad.getChildren().clear();
			yearSemMenu.setText(firstSort.getText());
			addon = "and year = 1";
			if(sql.searchFound) {
				stashPad(gridPad, courseCode, profName, grade, year, semester, addon, sortSelection);
			}else {
				noRecord(gridPad);
			}
		});
		secondSort.setOnAction(e -> {
			gridPad.getChildren().clear();
			yearSemMenu.setText(secondSort.getText());
			addon = "and year = 2";
			if(sql.searchFound) {
				stashPad(gridPad, courseCode, profName, grade, year, semester, addon, sortSelection);
			}else {
				noRecord(gridPad);
			}
		});
		thirdfirstSort.setOnAction(e -> {
			gridPad.getChildren().clear();
			yearSemMenu.setText(thirdfirstSort.getText());
			addon = "and year = 3";
			if(sql.searchFound) {
				stashPad(gridPad, courseCode, profName, grade, year, semester, addon, sortSelection);
			}else {
				noRecord(gridPad);
			}
		});
		fourthSort.setOnAction(e -> {
			gridPad.getChildren().clear();
			yearSemMenu.setText(fourthSort.getText());
			addon = "and year = 4";
			if(sql.searchFound) {
				stashPad(gridPad, courseCode, profName, grade, year, semester, addon, sortSelection);
			}else {
				noRecord(gridPad);
			}
		});
			
		return scrollPad;
	}
	
	private void noRecord(GridPane gridPad) {
		Pane blank = new Pane();
		blank.setPrefSize(255, 100);
		
		Label noRecord = new Label("No record");
		noRecord.setPrefSize(255, 100);
		noRecord.setStyle("-fx-font-size: 24px; -fx-font-family: 'Serif'; -fx-font-weight: bold; -fx-text-fill: #144C19; -fx-align: center;");
		
		gridPad.add(blank, 1, 1);
		gridPad.add(noRecord, 15, 2);
	}
	
	//updating stash pad for visible stacks
	private void stashPad(GridPane gridPad, ArrayList<String> courseCode, ArrayList<String> profName, ArrayList<String> grade, ArrayList<Integer> year, ArrayList<Integer> semester, String addonStr, String sortStr) {
		sql.openConnection();
		sql.getCourseStash(studentNum,search.getText(), courseCode, profName, grade, year, semester, addonStr, sortStr);
		sql.closeConnection();
		
		int row = 0;
		for (int col = 0; col < courseCode.size(); col++) {
		    if (col % 4 == 0 && col != 0) {
		        row++; 
		    }
		    
		    int yearStr = (year.get(col)!=0) ? year.get(col) : 0;
		    int semesterStr = (semester.get(col)!=0) ? semester.get(col) : 0;
		    String courseCodeStr = (!courseCode.get(col).isEmpty()) ? courseCode.get(col) : "N/A";
		    String profNameStr = (!profName.get(col).isEmpty()) ? profName.get(col) : "No Professor Found";
		    String gradeStr = (grade.get(col)!=null) ? grade.get(col) : "N/A";
		    
		    gridPad.add(courseContainer(yearStr, semesterStr, courseCodeStr, profNameStr, gradeStr), col % 4, row);
		}
	}
	
    private void retrieveData() {
    	sql.openConnection();
	    sql.getGradeAndProfessor(studentNum, crseCode, subjectName, prfName, grde, equi, yr, sm, cuLab, cuLec, chLab, chLec);
	    sql.getStudentInfoAddress(studentNum, nameList, studentNumList, programList, addressList, contactList);
	    sql.closeConnection();
    }
	
	private Pane courseContainer(int year, int sem, String courseCodeStr, String profNameStr, String gradeStr) {
		Pane courseContainer = new Pane();
		courseContainer.setPrefSize(255, 150);
		courseContainer.setStyle("-fx-background-color: #ABDE77; -fx-background-radius: 20;");
		
		String[] codeStr = courseCodeStr.split(" ");
		
		Label courseCode = new Label(codeStr[0] + "\n" + codeStr[1]);
		courseCode.setLayoutX(20);
		courseCode.setLayoutY(26);
		courseCode.setPrefSize(86, 72);
		courseCode.setStyle("-fx-font-size: 24px; -fx-font-family: 'Serif'; -fx-font-weight: bold; -fx-text-fill: #144C19; -fx-align: center;");
		
		Label grade = new Label("Grade: "+gradeStr);
		grade.setLayoutX(117);
		grade.setLayoutY(19);
		grade.setStyle("-fx-font-size: 16px; -fx-font-family: 'Serif'; -fx-font-weight: bold; -fx-text-fill: #144C19;");
		
		Label profName = new Label(profNameStr);
		profName.setLayoutX(117);
		profName.setLayoutY(44);
		profName.setStyle("-fx-font-size: 16px; -fx-font-family: 'Serif'; -fx-font-style: italic; -fx-text-fill: #144C19;");
		
		Label yearSemester = new Label(year + "-" + sem);
		yearSemester.setLayoutX(117);
		yearSemester.setLayoutY(71);
		yearSemester.setStyle("-fx-font-size: 16px; -fx-font-family: 'Serif'; -fx-font-style: italic; -fx-text-fill: #144C19;");
		
		Text text = new Text("Info");
		Button infoBtn = new Button(text.getText());
		infoBtn.setLayoutX(117);
		infoBtn.setLayoutY(103);
		infoBtn.setPrefSize(96, 30);
		infoBtn.setOpacity(0.7);
		infoBtn.setStyle("-fx-background-color: #144C19; -fx-background-radius: 20; -fx-text-fill: white; -fx-font-family: 'Serif';");
		infoBtn.setOnAction(e->{
	        for(int i = 0; i<crseCode.size(); i++) {
	        	if(crseCode.get(i).equalsIgnoreCase(courseCodeStr)) {
			        CoursePopInfo pop = new CoursePopInfo(subjectName.get(i), crseCode.get(i), prfName.get(i), grde.get(i), equi.get(i), yr.get(i), sm.get(i), cuLab.get(i), cuLec.get(i), chLab.get(i), chLec.get(i));
					pop.blurBg(WIDTH, HEIGHT);
	        	}
	        }
		});
		
		courseContainer.getChildren().addAll(courseCode, grade, profName, yearSemester, infoBtn);
		
		return courseContainer;
	}
}
