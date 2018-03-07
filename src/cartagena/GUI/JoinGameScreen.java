package cartagena.GUI;

import cartagena.data.GameData;
import cartagena.data.PlayerData;
import cartagena.gameComponent.GameMap;
import cartagena.interfaces.ClientCommunication;
import cartagena.network.Client;
import cartagena.players.LocalPlayer;
import cartagena.players.RemotePlayer;
import cartagena.protocols.Message;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class JoinGameScreen extends Application implements ClientCommunication {

    public static final Object syncObject = new Object();

    private static int PORT_NUMBER = 8081;

    @FXML
    public ImageView f_iv1, f_iv2, f_iv3, f_iv4, f_iv5, f_iv6, f_iv7, f_iv8, f_iv9, f_iv10, f_iv11, f_iv12, f_iv13, f_iv14, f_iv15, f_iv16, f_iv17;
    public ImageView f_iv18, f_iv19, f_iv20, f_iv21, f_iv22, f_iv23, f_iv24, f_iv25, f_iv26, f_iv27, f_iv28, f_iv29, f_iv30;
    public ImageView f_iv31, f_iv32, f_iv33, f_iv34, f_iv35, f_iv36, f_iv37, f_iv38;

    @FXML
    public CheckBox f_p1_cb, f_p2_cb, f_p3_cb, f_p4_cb, f_p5_cb;
    @FXML
    public ImageView f_p1_color, f_p2_color, f_p3_color, f_p4_color, f_p5_color;
    @FXML
    public Label f_p1_name, f_p2_name, f_p3_name, f_p4_name, f_p5_name;
    @FXML
    public Label f_IP_label, f_Port_label;

    private Timer refresher = new Timer();
    @FXML
    public Button refresh;

    @FXML
    public Button f_b_ready;

    private static boolean isMapChanged, isPlayerChanged;


    private Stage currentStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        currentStage = primaryStage;

        /////////////////////////// UNCOMMENT HERE TO CONNECT LOCAL GAME DIRECTLY
        // new Thread(() -> Client.initialize(xField.getText(),  Integer.parseInt(yField.getText()), this)).start();

        ////////////////////////// UNCOMMENT HERE TO CONNECT SPECIFIC ADDRESS

        JTextField xField = new JTextField(15);
        JTextField yField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("IP:"));
        myPanel.add(xField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("PORT:"));
        myPanel.add(yField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Network Information", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            new Thread(() -> Client.initialize(xField.getText(),  Integer.parseInt(yField.getText()), this)).start();

        }else{
            System.exit(1);
        }



        synchronized (syncObject) {
            try {
                syncObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Parent root = FXMLLoader.load(getClass().getResource("joinGameScreen.fxml"));


        primaryStage.setOnCloseRequest((t) -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.setTitle("Join Game");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    @FXML
    private void initialize() {

        LocalPlayer.initialize();


        f_iv38.setImage(new Image(getClass().getResourceAsStream("/resources/boat.png")));
        f_iv1.setImage(new Image(getClass().getResourceAsStream("/resources/start.png")));

        f_p1_cb.setSelected(LocalPlayer.isHost);
        f_p1_color.setImage(LocalPlayer.userColor.getImage());
        f_p1_name.setText(LocalPlayer.username);

        refresher.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> refresh.fire());
            }
        }, 0, 100);

        updateUserList();
        f_Port_label.setText("Port: " + PORT_NUMBER);
        try {
            f_IP_label.setText("IP: " + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            f_IP_label.setText("IP: Unknown");
        }
    }


    private void updateMap() {

        f_iv2.setImage(GameMap.getCellByID(1).card.getImage());
        f_iv3.setImage(GameMap.getCellByID(2).card.getImage());
        f_iv4.setImage(GameMap.getCellByID(3).card.getImage());
        f_iv5.setImage(GameMap.getCellByID(4).card.getImage());
        f_iv6.setImage(GameMap.getCellByID(5).card.getImage());
        f_iv7.setImage(GameMap.getCellByID(6).card.getImage());
        f_iv8.setImage(GameMap.getCellByID(7).card.getImage());
        f_iv9.setImage(GameMap.getCellByID(8).card.getImage());
        f_iv10.setImage(GameMap.getCellByID(9).card.getImage());
        f_iv11.setImage(GameMap.getCellByID(10).card.getImage());
        f_iv12.setImage(GameMap.getCellByID(11).card.getImage());
        f_iv13.setImage(GameMap.getCellByID(12).card.getImage());
        f_iv14.setImage(GameMap.getCellByID(13).card.getImage());
        f_iv15.setImage(GameMap.getCellByID(14).card.getImage());
        f_iv16.setImage(GameMap.getCellByID(15).card.getImage());
        f_iv17.setImage(GameMap.getCellByID(16).card.getImage());
        f_iv18.setImage(GameMap.getCellByID(17).card.getImage());
        f_iv19.setImage(GameMap.getCellByID(18).card.getImage());
        f_iv20.setImage(GameMap.getCellByID(19).card.getImage());
        f_iv21.setImage(GameMap.getCellByID(20).card.getImage());
        f_iv22.setImage(GameMap.getCellByID(21).card.getImage());
        f_iv23.setImage(GameMap.getCellByID(22).card.getImage());
        f_iv24.setImage(GameMap.getCellByID(23).card.getImage());
        f_iv25.setImage(GameMap.getCellByID(24).card.getImage());
        f_iv26.setImage(GameMap.getCellByID(25).card.getImage());
        f_iv27.setImage(GameMap.getCellByID(26).card.getImage());
        f_iv28.setImage(GameMap.getCellByID(27).card.getImage());
        f_iv29.setImage(GameMap.getCellByID(28).card.getImage());
        f_iv30.setImage(GameMap.getCellByID(29).card.getImage());
        f_iv31.setImage(GameMap.getCellByID(30).card.getImage());
        f_iv32.setImage(GameMap.getCellByID(31).card.getImage());
        f_iv33.setImage(GameMap.getCellByID(32).card.getImage());
        f_iv34.setImage(GameMap.getCellByID(33).card.getImage());
        f_iv35.setImage(GameMap.getCellByID(34).card.getImage());
        f_iv36.setImage(GameMap.getCellByID(35).card.getImage());
        f_iv37.setImage(GameMap.getCellByID(36).card.getImage());

    }

    public void readyGameButton() {
        System.out.println("Ready button clicked!");

        if(f_b_ready.getText().equalsIgnoreCase("ready")){
            Client.sendToServerWoutResponse(new Message().setCode("120").setUser(LocalPlayer.username));
            f_p1_cb.setSelected(true);
            f_b_ready.setText("Not Ready");
        }else{
            Client.sendToServerWoutResponse(new Message().setCode("121").setUser(LocalPlayer.username));
            f_p1_cb.setSelected(false);
            f_b_ready.setText("Ready");
        }

    }

    public void refreshButton(){
        if(isPlayerChanged){
            updateUserList();
            isPlayerChanged = false;
        }
        if(isMapChanged){
            updateMap();
            isMapChanged = false;
        }
    }

    // As client
    @Override
    public String newMessageArrivesFromServer(Message message) {

        System.out.println("Request from server: " + message);

        switch (message.getCode()) {
            case "420": addNewUsers(message); break;
            case "430": changeMap(message); break;
            case "700": GameData.updateGameStatus(message);
                Platform.runLater(() -> startGame()); break;
        }

        return null;
    }

    private void changeMap(Message message){
        GameMap.set(message.getContent().get(0));
        isMapChanged = true;
    }

    private void addNewUsers(Message message) {
        PlayerData.remotePlayers.clear();
        for (String userData : message.getContent()) {
            if (!RemotePlayer.createFromString(userData).username.equalsIgnoreCase(LocalPlayer.username))
                PlayerData.remotePlayers.add(RemotePlayer.createFromString(userData));
            else{
                LocalPlayer.isReady = RemotePlayer.createFromString(userData).isReady;
                LocalPlayer.queue = RemotePlayer.createFromString(userData).queue;
            }
        }
        isPlayerChanged = true;
    }

    private void updateUserList() {


        if(Objects.isNull(f_p2_color)){
            System.out.println("Passing...");
            return;
        }
        f_p1_cb.setSelected(LocalPlayer.isReady);

        if(f_b_ready.getText().equalsIgnoreCase("ready") && LocalPlayer.isReady)
            f_b_ready.setText("Not Ready");

        if(f_b_ready.getText().equalsIgnoreCase("not ready") && !LocalPlayer.isReady)
            f_b_ready.setText("Ready");

        if (PlayerData.remotePlayers.size() > 0) {
            Image tempI1 = PlayerData.remotePlayers.get(0).userColor.getImage();
            f_p2_cb.setSelected(PlayerData.remotePlayers.get(0).isReady);
            f_p2_color.setImage(tempI1);
            f_p2_name.setText(PlayerData.remotePlayers.get(0).username);
        }
        if (PlayerData.remotePlayers.size() > 1) {
            f_p3_cb.setSelected(PlayerData.remotePlayers.get(1).isReady);
            f_p3_color.setImage(PlayerData.remotePlayers.get(1).userColor.getImage());
            f_p3_name.setText(PlayerData.remotePlayers.get(1).username);
        }
        if (PlayerData.remotePlayers.size() > 2) {
            f_p4_cb.setSelected(PlayerData.remotePlayers.get(2).isReady);
            f_p4_color.setImage(PlayerData.remotePlayers.get(2).userColor.getImage());
            f_p4_name.setText(PlayerData.remotePlayers.get(2).username);
        }
        if (PlayerData.remotePlayers.size() > 3) {
            f_p5_cb.setSelected(PlayerData.remotePlayers.get(3).isReady);
            f_p5_color.setImage(PlayerData.remotePlayers.get(3).userColor.getImage());
            f_p5_name.setText(PlayerData.remotePlayers.get(3).username);
        }



    }

    private void startGame(){

        try {

            GameScreen gc = new GameScreen();

            // Close current page
            currentStage.close();
            GameData.isGameStarted = true;

            gc.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
