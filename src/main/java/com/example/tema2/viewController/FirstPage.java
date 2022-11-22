package com.example.tema2.viewController;

import com.example.tema2.model.SelectionPolicy;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FirstPage implements Initializable {

    @FXML
    public ProgressBar progresBar;
    public Label finishLabel;
    @FXML
    private TextField maxTasksPerServiceTextField, minProcessingTimeTextField, maxProcessingTimeTextField;
    @FXML
    private TextField noOfServersTextField, noOfClientsTextField, minArrivalTimeTextField, maxArrivalTimeTextField;
    @FXML
    private TextField timeLimitTextField;
    @FXML
    private ComboBox strategyApplyed;
    public ComboBox queueComboBox;
    public TextField waitingPeriodQueue, noClientsInQueue;

    private SimulationManager simulationManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> observableList = FXCollections.observableArrayList("Queue", "Time");
        strategyApplyed.setItems(observableList);
        finishLabel.setVisible(false);
    }


    public void startButtonOnAction(ActionEvent actionEvent) {
        simulationManager = new SimulationManager(this);
        Thread t = new Thread(simulationManager);
        t.start();
        ArrayList<String> itemi = new ArrayList<>();
        for (int i = 0; i < getNoOfServers(); ++i)
            itemi.add(String.valueOf(i));
        ObservableList<String> observableList = FXCollections.observableArrayList(itemi);
        queueComboBox.setItems(observableList);

    }

    public int getTimeLimit() {
        if (timeLimitTextField.getText().equals(""))
            return 0;
        return Integer.parseInt(timeLimitTextField.getText());
    }

    public int getMaxArrivalTime() {
        if (maxArrivalTimeTextField.getText().equals(""))
            return 0;
        return Integer.parseInt(maxArrivalTimeTextField.getText());
    }

    public int getMinArrivalTime() {
        if (minArrivalTimeTextField.getText().equals(""))
            return 0;
        return Integer.parseInt(minArrivalTimeTextField.getText());
    }

    public SelectionPolicy getStrategyApplyed() {
        String s = (String) strategyApplyed.getValue();
        if (s == null || s.equals("Queue"))
            return SelectionPolicy.SHORTHEST_QUEUE;
        return SelectionPolicy.SHORTEST_TIME;
    }

    public int minProcessingTime() {
        if (minProcessingTimeTextField.getText().equals(""))
            return 0;
        return Integer.parseInt(minProcessingTimeTextField.getText());
    }

    public int maxProcessingTime() {
        if (maxProcessingTimeTextField.getText().equals(""))
            return 0;
        return Integer.parseInt(maxProcessingTimeTextField.getText());
    }

    public int getNoOfServers() {
        if (noOfServersTextField.getText().equals(""))
            return 0;
        return Integer.parseInt(noOfServersTextField.getText());
    }

    public int getNoOfClients() {
        if (noOfClientsTextField.getText().equals(""))
            return 0;
        return Integer.parseInt(noOfClientsTextField.getText());
    }

    public int getMaxTasksPerServer() {
        if (maxTasksPerServiceTextField.getText().equals(""))
            return 0;
        return Integer.parseInt(maxTasksPerServiceTextField.getText());
    }

}
