package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Polynomial;

public class CalculatorView extends Application implements EventHandler<ActionEvent> {
    private Button addButton, subButton, mulButton, divButton, derButton, intButton, clrButton;
    private Label giveLabel, p1Label, p2Label, resLabel, optLabel;
    private TextField p1Input, p2Input, resOutput;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Roxie's Polynomial Application");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(30, 30, 30, 30));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        giveLabel = new Label("Give the polynomials:");
        giveLabel.setFont(Font.font("Georgia", FontWeight.NORMAL, 14));
        GridPane.setConstraints(giveLabel, 0, 0);

        p1Label = new Label("Polynomial 1:");
        GridPane.setConstraints(p1Label, 0, 1);

        p1Input = new TextField();
        GridPane.setConstraints(p1Input, 1, 1);

        p2Label = new Label("Polynomial 2:");
        GridPane.setConstraints(p2Label, 0, 2);

        p2Input = new TextField();
        GridPane.setConstraints(p2Input, 1, 2);

        resLabel = new Label("Result:");
        GridPane.setConstraints(resLabel, 0, 3);

        resOutput = new TextField();
        GridPane.setConstraints(resOutput, 1, 3);
        resOutput.setEditable(false);

        clrButton = new Button("Clear");
        GridPane.setConstraints(clrButton, 1, 4);
        clrButton.setOnAction(e -> {
            p1Input.clear();
            p2Input.clear();
            resOutput.clear();
        });

        optLabel = new Label("Choose an operation");
        optLabel.setFont(Font.font("Georgia", FontWeight.NORMAL, 14));
        GridPane.setConstraints(optLabel, 5, 0);

        addButton = new Button("Addition");
        addButton.setMaxWidth(Double.MAX_VALUE);
        GridPane.setConstraints(addButton, 5, 1);

        subButton = new Button("Subtraction");
        subButton.setMaxWidth(Double.MAX_VALUE);
        GridPane.setConstraints(subButton, 5, 2);

        mulButton = new Button("Multiplication");
        mulButton.setMaxWidth(Double.MAX_VALUE);
        GridPane.setConstraints(mulButton, 5, 3);

        divButton = new Button("Division");
        divButton.setMaxWidth(Double.MAX_VALUE);
        GridPane.setConstraints(divButton, 5, 4);

        derButton = new Button("Derivation");
        derButton.setMaxWidth(Double.MAX_VALUE);
        GridPane.setConstraints(derButton, 5, 5);

        intButton = new Button("Integration");
        intButton.setMaxWidth(Double.MAX_VALUE);
        GridPane.setConstraints(intButton, 5, 6);

        gridPane.getChildren().addAll(giveLabel, p1Label, p1Input, p2Label, p2Input, resLabel, resOutput, clrButton, optLabel, addButton, subButton, mulButton, divButton, derButton, intButton);

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();

        addButton.setOnAction(this);
        subButton.setOnAction(this);
        mulButton.setOnAction(this);
        divButton.setOnAction(this);
        derButton.setOnAction(this);
        intButton.setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        Button btn = (Button) event.getSource();

        Polynomial p1 = new Polynomial(p1Input.getText());
        Polynomial p2 = new Polynomial(p2Input.getText());

        if (btn == addButton) {
            resOutput.setText(p1.addition(p2).toString());
        } else if (btn == subButton) {
            resOutput.setText(p1.subtraction(p2).toString());
        } else if (btn == mulButton) {
            resOutput.setText(p1.multiplication(p2).toString());
        } else if (btn == derButton) {
            resOutput.setText(p1.derivate().toString());
        } else if (btn == intButton) {
            resOutput.setText(p1.integrate().toString());
        } else if (btn == divButton) {
            resOutput.setText("Q: " + p1.division(p2)[0].toString() + " ,R: " + p1.division(p2)[1].toString());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Unknown button");
            alert.setContentText("Unknown button was pressed.");
            alert.showAndWait();
        }
    }
}

