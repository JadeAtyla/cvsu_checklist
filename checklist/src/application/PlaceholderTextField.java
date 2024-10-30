package application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class PlaceholderTextField extends TextField {
    private final String placeholder;
    private final Label label;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
        label = new Label(placeholder);
        label.setTextFill(Color.GRAY);
        label.visibleProperty().bind(textProperty().isEmpty());
        
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER_LEFT);
        root.getChildren().add(label);

        getChildren().addAll(root);
    }
}
