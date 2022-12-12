package org.gifts.controllers;

import javafx.beans.property.ReadOnlyFloatWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.gifts.entity.item.Sweet;

import java.io.IOException;

public class Controller {

  public void goTo(ActionEvent event, Stage stage, Scene scene, String path) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource(path));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void setCellsCurrent(TableColumn<Sweet, String> title, TableColumn<Sweet, Number> sugarLevel, TableColumn<Sweet,
          Number> calorieContent, TableColumn<Sweet, Number> price, TableColumn<Sweet, String> description){
    sugarLevel.setCellValueFactory(cellData -> new ReadOnlyFloatWrapper(cellData.getValue().getCurrentSugarLevel()));
    calorieContent.setCellValueFactory(cellData -> new ReadOnlyFloatWrapper(cellData.getValue().getCurrentCalorieContent()));
    price.setCellValueFactory(cellData -> new ReadOnlyFloatWrapper(cellData.getValue().getCurrentPrice()));
    title.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
    description.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDescription()));
  }
  public void setCells(TableColumn<Sweet, String> title, TableColumn<Sweet, Number> sugarLevel, TableColumn<Sweet,
          Number> calorieContent, TableColumn<Sweet, Number> price, TableColumn<Sweet, String> description){
    sugarLevel.setCellValueFactory(cellData -> new ReadOnlyFloatWrapper(cellData.getValue().getSugarLevel()));
    calorieContent.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getCalorieContent()));
    price.setCellValueFactory(cellData -> new ReadOnlyFloatWrapper(cellData.getValue().getPrice()));
    title.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
    description.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDescription()));
  }

  public void filterName(ObservableList<Sweet> dataList, TextField filterField, TableView<Sweet> tableview){
    FilteredList<Sweet> filteredData = new FilteredList<>(dataList, b -> true);

    filterField.textProperty().addListener((observable, oldValue, newValue) -> {
      filteredData.setPredicate(sweet -> {
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }
        String lowerCaseFilter = newValue.toLowerCase();
        if (sweet.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
          return true;
        } else if (sweet.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
          return true;
        }
        else
          return false;
      });
    });

    SortedList<Sweet> sortedData = new SortedList<>(filteredData);
    sortedData.comparatorProperty().bind(tableview.comparatorProperty());
    tableview.setItems(sortedData);
  }
}
