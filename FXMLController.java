package controllers;


import utils.CalculatorFX;
import utils.EvaluateString;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class FXMLController{
    
    @FXML
    Label expression;
    
    @FXML
    Label result;
    
    private ArrayList<String> calculationHistory = new ArrayList<>();
    
    public void insertNumber(String number){
        expression.setText(expression.getText() + number);
    }
    
    public void onMouseClick(MouseEvent mouseEvent){
        Button button = (Button)mouseEvent.getSource();
        String number = button.getText();
        mouseEvent.getButton();
        insertNumber(number);
    }  
    
    public void insertOperator(String operator){
        expression.setText(expression.getText() + " " + operator + " ");
    }
    
    public void insertAnswer(String answer){
        expression.setText(expression.getText() + answer);
    }
    
    public void deleteLast(){
        if (!getExpression().getText().isEmpty()) {
            StringBuilder text = new StringBuilder(getExpression().getText());
            text.deleteCharAt(text.length()-1);
            getExpression().setText(text.toString());
        }
    }
    
    public void clearExpression(){
        expression.setText("");
    }
    
    public Label getExpression(){
        return expression;
    }
    
    public void setResult(String newResult){
        this.result.setText("= " + newResult);
    }
    public Label getResult(){
        return result;
    }
    
    public void openHistoryWindow(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/history.fxml"));
            Parent root = loader.load();
            CalculatorFX.getHistoryStage().setScene(new Scene(root));   
            HistoryController historyController = loader.getController();
            historyController.initializeCalculations(calculationHistory);
            
            CalculatorFX.getHistoryStage().show();
            
        } catch(IOException ex){
            System.out.println(ex);
        }
    }
    
    public void addCalculation(String expression, String result){
        this.calculationHistory.add(expression + " = " + result);
        
    }
    public void playSound(){
        String musicFile = "StabSF.mp3";
        
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        
    }

    @FXML
    private void onMouseClick(javafx.scene.input.MouseEvent event) {
        Button button = (Button)event.getSource();
        String buttonText = button.getText();
        event.getButton();     
        
        switch(buttonText){
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "0":
                insertNumber(buttonText);
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                insertOperator(buttonText);
                break;
            case "CLEAR":
                clearExpression();
                break;
            case "=":
                int result = EvaluateString.evaluate(this.getExpression().getText());
                addCalculation(this.getExpression().getText(), String.valueOf(result));
                setResult(String.valueOf(result)); 
                break;
            case "ANS":
                insertAnswer(getResult().getText().substring(2));
                break;
            case "DELETE":
                deleteLast();
                break;
            case "HIST":
                openHistoryWindow();
                break;
            case "Stab":
                playSound();   
        }
    }
    
    
}
