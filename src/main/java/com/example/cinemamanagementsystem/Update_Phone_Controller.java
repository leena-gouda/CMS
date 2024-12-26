package com.example.cinemamanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.stage.Window;

public class Update_Phone_Controller extends Controller {

    @FXML
    private Button CancelButton;

    @FXML
    private Button UpdateButton;

    @FXML
    private TextField NewNumberTF;

    @FXML
    private TextField OldNumberTF;

    String OldPhoneNumber = OldNumberTF.getText();
    String NewPhoneNumber = NewNumberTF.getText();

    Window owner = UpdateButton.getScene().getWindow();

    // Regex for validating phone numbers
    String phoneRegex = "^\\+?[0-9]{1,4}[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,4}$";

    // Create a Pattern object
    Pattern pattern = Pattern.compile(phoneRegex);

    // Create a Matcher object
    Matcher match = pattern.matcher(OldPhoneNumber);
    Matcher matcher = pattern.matcher(NewPhoneNumber);

    String query="UPDATE person SET phone=? WHERE id=?";


    @FXML
    void CancelFunc(ActionEvent event) {
        try{
            if (event.getSource() == CancelButton) {
                switchScene(event,"UpdateInfo.fxml","UpdateInfo",userid);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void UpdateFunc(ActionEvent event) {
        if (event.getSource() == UpdateButton) {
            if(OldPhoneNumber.equals("") || NewPhoneNumber.equals("")){
                showAlert(owner);
            } else if (NewPhoneNumber.equals(OldPhoneNumber)) {
                showAlert(owner,"Both Phone numbers are the same. Please enter a new phone number.");
            } else if (!matcher.matches()) {
                // If the phone number is not in a valid format, show an error
                showAlert(owner, "Please enter a valid phone number.");
            }else if (!match.matches()) {
                // If the phone number is not in a valid format, show an error
                showAlert(owner, "Please enter a valid phone number.");
            }else{
                boolean flag = Jdbc.UpdateEmail(NewPhoneNumber,query,userid);
                try {
                    if(flag){
                        infoBox("Your phone number has been updated in our system.", "Phone number Updated Successfully", " Update Successful");
                        switchScene(event, "UpdateInfo.fxml", "UpdateInfo", userid);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

}