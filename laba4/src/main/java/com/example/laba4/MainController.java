package com.example.laba4;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.*;
import java.util.Objects;

public class MainController {
    @FXML
    private Label result;

    private float number1=0;

    private float number2=0;

    private String operator="";

    private boolean start=true;
    private Calclulate calculate=new Calclulate();
    public static int counter = 0;
    @FXML
    public void processNumber(ActionEvent event){
        if(start){
            result.setText("");
            start=false;
        }
        String value=((Button)event.getSource()).getText();
        for(int i = 0; i < result.getText().length(); ++i) {
            if (result.getText().charAt(i) == '.' && value.equals(".")) {
                value = "";
            }
        }
        result.setText(result.getText()+ value);
    }

    @FXML
    public void processBinaryOperator(ActionEvent event) {
        String value=((Button)event.getSource()).getText();
        if(!value.equals("=")){
            if(!operator.isEmpty())
                return;

            operator = value;
            number1=Float.parseFloat(result.getText());
            result.setText("");
        }else{
            if(operator.isEmpty())
                return;
            float output = Float.MAX_VALUE;
            number2=Float.parseFloat(result.getText());
            if (number1 < 0 && Objects.equals(operator, "^")) {
                result.setText("Основание меньше нуля");
            }
            else {
                output = calculate.calculateBinaryNumber(number1, number2, operator);
                result.setText(String.valueOf(output));
                if(output == 0 && operator.equals("/")) {
                    result.setText("Деление на 0");
                }
            }
            try(FileWriter history = new FileWriter("History.txt", true)){
                history.write(Float.toString(number1));
                history.write(' ');
                history.write(operator);
                history.write(' ');
                history.write(Float.toString(number2));
                history.write(' ');
                history.write('=');
                history.write(' ');
                history.write(Float.toString(output));
                history.write('\n');
            }
            catch (IOException exception){
                System.out.println(exception.getMessage());
            }
            operator="";
        }
    }
    public void processUnaryOperator(ActionEvent event) {

        String value=((Button)event.getSource()).getText();
        if(!operator.isEmpty())
            return;

        operator = value;
        number1=Float.parseFloat(result.getText());
        result.setText("");
        if (number1 < 0 && Objects.equals(operator, "√")){
            result.setText("Взятие корня из отрицательного числа");
        }
        else {
            float output = calculate.calculateUnaryNumber(number1, operator);
            result.setText(String.valueOf(output));
            try (FileWriter history = new FileWriter("History.txt", true)) {
                history.write(operator);
                history.write(' ');
                history.write(Float.toString(number1));
                history.write(' ');
                history.write('=');
                history.write(Float.toString(output));
                history.write('\n');
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        }
        operator="";
    }

    public void ClearFunction(ActionEvent event){
        operator="";
        start=true;
        result.setText("");
    }
    public void ClearFunctionCM(ActionEvent event){
        operator="";
        number1=Float.parseFloat(result.getText());
        start=true;
        int counter = 0;
        for(int i = 0; i < Float.toString(number1).length(); i++){
            if(Float.toString(number1).charAt(i) == '.' && Float.toString(number1).charAt(i + 1) == '0') {
                counter = 3;
            }
            if(Float.toString(number1).charAt(i) == '.' && Float.toString(number1).charAt(i + 1) != '0') {
                counter = 1;
            }
        }
        char[] number = new char[Float.toString(number1).length() - counter];
        for(int i = 0; i < Float.toString(number1).length() - counter; ++i) {
            number[i] = Float.toString(number1).charAt(i);
        }
        String newNumber = new String(number);
        result.setText(newNumber);
    }
    public void getOneAction(ActionEvent event) {
        String value=((Button)event.getSource()).getText();
    }

}
