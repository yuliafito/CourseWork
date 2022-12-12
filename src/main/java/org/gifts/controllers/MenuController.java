package org.gifts.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
  private Stage stage;
  private Scene scene;
  Controller controller = new Controller();

  private Parent root;
  @FXML
  private Button addSweet;

  @FXML
  private Button deleteSweet;

  @FXML
  private Button filterSweets;

  @FXML
  private Button showAllSweets;

  @FXML
  private Button showSweets;

  @FXML
  private Button sortSweet;

  @FXML
  private Button updateSweet;


  @FXML
  void addSweet(ActionEvent event) throws IOException {
    controller.goTo(event, stage, scene,"/org.gifts/addSweet.fxml");
  }

  @FXML
  void deleteSweet(ActionEvent event) throws IOException {
    controller.goTo(event, stage, scene,"/org.gifts/deleteSweet.fxml");
  }

  @FXML
  void updateSweet(ActionEvent event) throws IOException {
    controller.goTo(event, stage, scene,"/org.gifts/updateSweet.fxml");
  }

  @FXML
  void showSweets(ActionEvent event) throws IOException {
    controller.goTo(event, stage, scene,"/org.gifts/showSweets.fxml");
  }
  @FXML
  void showAllSweets(ActionEvent event) throws IOException {
    controller.goTo(event, stage, scene,"/org.gifts/showAllSweets.fxml");
  }

  @FXML
  void filterSweets(ActionEvent event) throws IOException {
    controller.goTo(event, stage, scene,"/org.gifts/filterSweets.fxml");
  }
}

