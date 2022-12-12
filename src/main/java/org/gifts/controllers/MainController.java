package org.gifts.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import org.gifts.database.DBhandler;
import org.gifts.entity.item.PresentBox;
import org.gifts.entity.item.PresentShape;
import org.gifts.entity.item.Sweet;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {
  private Stage stage;
  private Scene scene;
  DBhandler dbHandler = new DBhandler();
  Controller controller = new Controller();

  private Parent root;
  @FXML
  private Button makeButton;

  @FXML
  private Button createGiftButton;

  @FXML
  void make(ActionEvent event) throws IOException {
    controller.goTo(event, stage, scene,"/org.gifts/menu.fxml");

    PresentBox present = PresentBox.getPresentBox((int) (Math.random() * 1000) + 1, PresentShape.SQUARE, "green", new ArrayList<Sweet>());
    dbHandler.addPresentBox(present.getId(), present.getColor(), present.getPresentShape());
  }
}
