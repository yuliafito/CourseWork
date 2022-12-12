package org.gifts.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.gifts.database.DBhandler;
import org.gifts.entity.item.PresentBox;
import org.gifts.entity.item.Sweet;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddSweet implements Initializable {
  Controller controller = new Controller();
  DBhandler dbHandler = new DBhandler();
  PresentBox presentBox = PresentBox.getPresentBox();
  private Stage stage;
  private Scene scene;
  @FXML
  private Button toMenuButton;

  @FXML
  private TextField weightField;

  @FXML
  private Text message;

  @FXML
  private Button addButton;

  @FXML
  private TableColumn<Sweet, String> description;

  @FXML
  private TextField filterField;

  @FXML
  private TableColumn<Sweet, Number> price;

  @FXML
  private TableView<Sweet> tableview;

  @FXML
  private TableColumn<Sweet, String> title;

  @FXML
  private TableColumn<Sweet, Number> sugarLevel;

  @FXML
  private TableColumn<Sweet, Number> calorieContent;

  private final ObservableList<Sweet> dataList = FXCollections.observableArrayList(dbHandler.getAllSweets());

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    controller.setCells(title, sugarLevel, calorieContent, price, description);
    controller.filterName(dataList, filterField, tableview);
  }

  public void add(){
    float weight = Float.parseFloat(weightField.getText());
    if (presentBox.getPresentWeight() + weight > 2.0) {
      message.setText("Weight of present is bigger than 2 kg, sweet isn`t added");
    }
    else{
      dbHandler.addSweet(tableview.getSelectionModel().getSelectedItem().getTitle(), weight, presentBox.getId());
      weightField.setText("");
      message.setText("The sweet is added");
      tableview.getSelectionModel().clearSelection();
    }
  }

  public void menu(ActionEvent event) throws IOException {
    controller.goTo(event, stage, scene, "/org.gifts/menu.fxml");
  }
}