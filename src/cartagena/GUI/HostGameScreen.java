package cartagena.GUI;

import cartagena.data.GameData;
import cartagena.data.PlayerData;
import cartagena.enums.COLOR;
import cartagena.gameComponent.GameMap;
import cartagena.interfaces.ServerCommunication;
import cartagena.network.GameServer;
import cartagena.network.Server;
import cartagena.players.LocalPlayer;
import cartagena.players.RemotePlayer;
import cartagena.protocols.Message;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HostGameScreen extends Application implements ServerCommunication{

    private static int PORT_NUMBER = 8081;

    @FXML
    public ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9, iv10, iv11, iv12, iv13, iv14, iv15, iv16, iv17;
    public ImageView iv18, iv19, iv20, iv21, iv22, iv23, iv24, iv25, iv26, iv27, iv28, iv29, iv30;
    public ImageView iv31, iv32, iv33, iv34, iv35, iv36, iv37, iv38;

    public CheckBox p1_cb, p2_cb, p3_cb, p4_cb, p5_cb;
    public ImageView p1_color, p2_color, p3_color, p4_color, p5_color;
    public Label p1_name, p2_name, p3_name, p4_name, p5_name;

    public Label IP_label, Port_label;

    private Stage stage;
    private GameServer gs;
    private int queue = 1;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("hostGameScreen.fxml"));

        this.stage = primaryStage;

        primaryStage.setTitle("Host Game");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);

        primaryStage.show();

        primaryStage.setOnCloseRequest((t) -> {
                Platform.exit();
                System.exit(0);
        });

    }

    @FXML
    private void initialize() {
        p1_name.setText(LocalPlayer.username);
        p1_color.setImage(LocalPlayer.userColor.getImage());

        iv38.setImage(new Image(getClass().getResourceAsStream("/resources/boat.png")));
        iv1.setImage(new Image(getClass().getResourceAsStream("/resources/start.png")));

        gs = new GameServer();

        updateMap();
        Port_label.setText("Port: " + PORT_NUMBER);
        startServer(PORT_NUMBER);
        try {
            IP_label.setText("IP: " + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            IP_label.setText("IP: Unknown");
        }
    }

    private void startServer(int port){

        new Thread(() -> {
            ServerSocket server = null;
            try {
                server = new ServerSocket(port);
                while (true) {
                    /**
                     * create a new {@link SocketServer} object for each connection
                     * this will allow multiple player connections
                     */
                    PlayerData.clients.add(new Server(server.accept(), this));
                }
            } catch (IOException ex) {
                String newPort = "" + port;
                while(true){
                    try {
                        newPort = JOptionPane.showInputDialog("Unable to start server on port '" + newPort + "'");
                        int Port = Integer.parseInt(newPort);
                        PORT_NUMBER = Port;
                        Port_label.setText("Port: " + PORT_NUMBER);
                        startServer(Port);
                        break;
                    }catch (NumberFormatException nfe){}
                }
            } finally {
                try {
                    if (server != null)
                        server.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private void updateMap(){
        iv2.setImage(GameMap.getCellByID(1).card.getImage());
        iv3.setImage(GameMap.getCellByID(2).card.getImage());
        iv4.setImage(GameMap.getCellByID(3).card.getImage());
        iv5.setImage(GameMap.getCellByID(4).card.getImage());
        iv6.setImage(GameMap.getCellByID(5).card.getImage());
        iv7.setImage(GameMap.getCellByID(6).card.getImage());
        iv8.setImage(GameMap.getCellByID(7).card.getImage());
        iv9.setImage(GameMap.getCellByID(8).card.getImage());
        iv10.setImage(GameMap.getCellByID(9).card.getImage());
        iv11.setImage(GameMap.getCellByID(10).card.getImage());
        iv12.setImage(GameMap.getCellByID(11).card.getImage());
        iv13.setImage(GameMap.getCellByID(12).card.getImage());
        iv14.setImage(GameMap.getCellByID(13).card.getImage());
        iv15.setImage(GameMap.getCellByID(14).card.getImage());
        iv16.setImage(GameMap.getCellByID(15).card.getImage());
        iv17.setImage(GameMap.getCellByID(16).card.getImage());
        iv18.setImage(GameMap.getCellByID(17).card.getImage());
        iv19.setImage(GameMap.getCellByID(18).card.getImage());
        iv20.setImage(GameMap.getCellByID(19).card.getImage());
        iv21.setImage(GameMap.getCellByID(20).card.getImage());
        iv22.setImage(GameMap.getCellByID(21).card.getImage());
        iv23.setImage(GameMap.getCellByID(22).card.getImage());
        iv24.setImage(GameMap.getCellByID(23).card.getImage());
        iv25.setImage(GameMap.getCellByID(24).card.getImage());
        iv26.setImage(GameMap.getCellByID(25).card.getImage());
        iv27.setImage(GameMap.getCellByID(26).card.getImage());
        iv28.setImage(GameMap.getCellByID(27).card.getImage());
        iv29.setImage(GameMap.getCellByID(28).card.getImage());
        iv30.setImage(GameMap.getCellByID(29).card.getImage());
        iv31.setImage(GameMap.getCellByID(30).card.getImage());
        iv32.setImage(GameMap.getCellByID(31).card.getImage());
        iv33.setImage(GameMap.getCellByID(32).card.getImage());
        iv34.setImage(GameMap.getCellByID(33).card.getImage());
        iv35.setImage(GameMap.getCellByID(34).card.getImage());
        iv36.setImage(GameMap.getCellByID(35).card.getImage());
        iv37.setImage(GameMap.getCellByID(36).card.getImage());

    }

    public void generateMapButton(){
        System.out.println("Generate Map button clicked!");
        GameMap.generate();
        updateMap();
        sendToClients(new Message().setCode("430").setUser(LocalPlayer.username).addContent(GameMap.asString()));
        for(RemotePlayer rp : PlayerData.remotePlayers)
            rp.isReady = false;
        updateUserList();
        userListChanged();

    }

    public void startGameButton(){
        System.out.println("Start Game button clicked!");

        if(PlayerData.remotePlayers.size() == 0){
            JOptionPane.showMessageDialog(null, "There must be at least 2 players.");
            return;
        }

        for(RemotePlayer rp : PlayerData.remotePlayers)
            if(!rp.isReady){
                JOptionPane.showMessageDialog(null, "Player [" + rp.username + "] is not ready!");
                return;
            }

        try {

            LocalPlayer.initialize();
            gs.initialize();

            sendToClients(gs.getGameStatus().setCode("700"));

            // Hide current page
            p1_cb.getScene().getWindow().hide();

            GameData.isGameStarted = true;
            new GameScreen().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    // As server
    @Override
    public String newMessageArrivesFromClient(String message) {
        System.out.println("Request from client: " + message);
        Message receivedMessage = new Message(message);

        Message returnMessage = new Message();
        returnMessage.setUser(LocalPlayer.username);
        returnMessage.setCode("000");

        switch (receivedMessage.getCode()){
            case "120": m120(receivedMessage); break;
            case "121": m121(receivedMessage); break;
            case "210": returnMessage.addContent(m210()); break;
            case "220": m220(receivedMessage, returnMessage); break;
            case "230": m230(returnMessage); break;
            case "310": returnMessage.setCode(m310(receivedMessage.getContent().get(0))); break;
            case "410": m410(receivedMessage); updateUserList(); break;
            default:
                System.out.println("Unknown message: " + message); break;
        }


        System.out.println("Response to client: " + returnMessage);
        return returnMessage.toString();
    }

    private void updateUserList(){
        Platform.runLater(
                () -> {
                    p1_cb.setSelected(LocalPlayer.isHost);
                    p1_cb.setDisable(LocalPlayer.isHost);
                    p1_color.setImage(LocalPlayer.userColor.getImage());
                    p1_name.setText(LocalPlayer.username);

                    if(PlayerData.remotePlayers.size() > 0){
                        p2_cb.setSelected(PlayerData.remotePlayers.get(0).isReady);
                        p2_color.setImage(PlayerData.remotePlayers.get(0).userColor.getImage());
                        p2_name.setText(PlayerData.remotePlayers.get(0).username);
                    }
                    if(PlayerData.remotePlayers.size() > 1){
                        p3_cb.setSelected(PlayerData.remotePlayers.get(1).isReady);
                        p3_color.setImage(PlayerData.remotePlayers.get(1).userColor.getImage());
                        p3_name.setText(PlayerData.remotePlayers.get(1).username);
                    }
                    if(PlayerData.remotePlayers.size() > 2){
                        p4_cb.setSelected(PlayerData.remotePlayers.get(2).isReady);
                        p4_color.setImage(PlayerData.remotePlayers.get(2).userColor.getImage());
                        p4_name.setText(PlayerData.remotePlayers.get(2).username);
                    }
                    if(PlayerData.remotePlayers.size() > 3){
                        p5_cb.setSelected(PlayerData.remotePlayers.get(3).isReady);
                        p5_color.setImage(PlayerData.remotePlayers.get(3).userColor.getImage());
                        p5_name.setText(PlayerData.remotePlayers.get(3).username);
                    }
                }
        );


    }

    private void m230(Message returnMessage){
        returnMessage.addContent(GameMap.asString());
        returnMessage.setCode("430");
    }

    private void m120(Message message){
        for(RemotePlayer rp : PlayerData.remotePlayers)
            if(rp.username.equalsIgnoreCase(message.getUser()))
                rp.isReady = true;
        updateUserList();
        userListChanged();

    }

    private void m121(Message message){
        for(RemotePlayer rp : PlayerData.remotePlayers)
            if(rp.username.equalsIgnoreCase(message.getUser()))
                rp.isReady = false;
        updateUserList();
        userListChanged();
    }

    // Inform others
    private void userListChanged(){
        Message response = new Message();
        response.setCode("420");
        response.addContent(LocalPlayer.getPlayerAsString());
        for(RemotePlayer rp : PlayerData.remotePlayers)
            response.addContent(rp.toString());

        // Inform other players that there is a new player.
        sendToClients(response);
    }

    private String m210(){
        List<COLOR> availableColors = new ArrayList<>();
        for(COLOR c : COLOR.values())
            if(!LocalPlayer.userColor.equals(c)){
                boolean control = true;
                for(RemotePlayer rp : PlayerData.remotePlayers)
                    if(!Objects.isNull(rp.userColor))
                        if(rp.userColor.equals(c))
                            control = false;
                if(control)
                    availableColors.add(c);
            }

        return availableColors.stream()
                .map(COLOR::toString)
                .collect(Collectors.joining("|"));
    }
    private void m220(Message requestMessage, Message returnMessage){
        returnMessage.addContent(LocalPlayer.getPlayerAsString());
        for(RemotePlayer rp : PlayerData.remotePlayers)
            if(!rp.username.equalsIgnoreCase(requestMessage.getUser())){
                returnMessage.addContent(rp.toString());
            }
        returnMessage.setCode("420");
    }
    private String m310(String remoteUsername){

        if(PlayerData.remotePlayers.size() > 3)
            return "510";

        if(remoteUsername.equalsIgnoreCase(LocalPlayer.username))
            return "520";

        for(RemotePlayer rp : PlayerData.remotePlayers)
            if(rp.username.equalsIgnoreCase(remoteUsername))
                return "520";

        PlayerData.remotePlayers.add(new RemotePlayer(remoteUsername, queue++));
        return "110";
    }
    private void m410(Message message){
        PlayerData.getByName(message.getUser()).userColor = COLOR.getColorByName(message.getContent().get(0));

        Message response = new Message();
        response.setCode("420");
        response.addContent(LocalPlayer.getPlayerAsString());
        for(RemotePlayer rp : PlayerData.remotePlayers)
            response.addContent(rp.toString());

        // Inform other players that there is a new player.
        sendToClients(response);
    }

    private void sendToClients(Message message){
        for(Server s : PlayerData.clients)
            s.sendToClient(message.toString());
    }
}
