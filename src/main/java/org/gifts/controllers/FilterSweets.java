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
import javafx.stage.Stage;

import org.gifts.database.DBhandler;
import org.gifts.entity.item.PresentBox;
import org.gifts.entity.item.Sweet;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FilterSweets implements Initializable {
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
  private TableColumn<Sweet, Number> price;

  @FXML
  private TableColumn<Sweet, Number> weight;

  @FXML
  private TableView<Sweet> tableview;

  @FXML
  private TableColumn<Sweet, String> title;

  @FXML
  private TextField minSugarLevel;

  @FXML
  private TextField maxSugarLevel;

  @FXML
  private TableColumn<Sweet, Number> sugarLevel;

  @FXML
  private TableColumn<Sweet, Number> calorieContent;
  private final List<Sweet> listOfSweets = dbHandler.getSweets(presentBox.getId());
  private final ObservableList<Sweet> dataList = FXCollections.observableArrayList(listOfSweets);

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    controller.setCellsCurrent(title, sugarLevel, calorieContent, price, description);
    weight.setCellValueFactory(cellData -> new ReadOnlyFloatWrapper(cellData.getValue().getWeight()));
    tableview.setItems(dataList);

  }

  public void filter(){
    int min = Integer.parseInt(minSugarLevel.getText());
    int max = Integer.parseInt(maxSugarLevel.getText());
    ObservableList<Sweet> data = FXCollections.observableArrayList(dbHandler.filterSweets(presentBox.getId(), "sugarLevel",min,max));
    tableview.setItems(data);
  }


  public void menu(ActionEvent event) throws IOException {
    controller.goTo(event, stage, scene,"/org.gifts/menu.fxml");
  }
}
