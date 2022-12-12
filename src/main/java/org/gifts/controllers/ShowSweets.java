package org.gifts.controllers;

import javafx.beans.property.ReadOnlyFloatWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.gifts.database.DBhandler;
import org.gifts.entity.item.PresentBox;
import org.gifts.entity.item.Sweet;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowSweets implements Initializable {
  Controller controller = new Controller();
  DBhandler dbHandler = new DBhandler();
  PresentBox presentBox = PresentBox.getPresentBox();
  private Stage stage;
  private Scene scene;

  @FXML
  private Button toMenuButton;

  @FXML
  private TableColumn<Sweet, String> description;

  @FXML
  private TextField filterField;

  @FXML
  private TableColumn<Sweet, Number> price;

  @FXML
  private TableColumn<Sweet, Number> weight;

  @FXML
  private TableView<Sweet> tableview;

  @FXML
  private TableColumn<Sweet, String> title;

  @FXML
  private TableColumn<Sweet, Number> sugarLevel;

  @FXML
  private TableColumn<Sweet, Number> calorieContent;
  private final List<Sweet> listOfSweets = dbHandler.getSweets(presentBox.getId());
  private final ObservableList<Sweet> dataList = FXCollections.observableArrayList(listOfSweets);


  @FXML
  private Text totalCalorieContent;

  @FXML
  private Text totalSugarLevel;

  @FXML
  private Text totalWeight;

  @FXML
  private Text totalprice;



  @Override
  public void initialize(URL url, ResourceBundle rb) {
    controller.setCells(title, sugarLevel, calorieContent, price, description);
    weight.setCellValueFactory(cellData -> new ReadOnlyFloatWrapper(cellData.getValue().getWeight()));
    controller.filterName(dataList, filterField, tableview);
    totalprice.setText(String.format("%.2f", presentBox.getPrice()));
    totalCalorieContent.setText(presentBox.getCalorieContent() + "");
    totalSugarLevel.setText(String.format("%.2f", presentBox.getSugarLevel()));
    totalWeight.setText(String.format("%.2f", presentBox.getPresentWeight()));
  }

  public void menu(ActionEvent event) throws IOException {
    controller.goTo(event, stage, scene,"/org.gifts/menu.fxml");
  }
}
