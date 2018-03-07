package cartagena.GUI;

import cartagena.data.GameData;
import cartagena.data.PlayerData;
import cartagena.enums.CARD;
import cartagena.gameComponent.Deck;
import cartagena.gameComponent.GameMap;
import cartagena.network.Client;
import cartagena.network.GameServer;
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
import java.util.Collections;

public class GameScreen extends Application {

    private int selectedPirate;
    private int selectedCard;
    private int selectedBackCoordinate;

    @FXML
    public ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9, iv10, iv11, iv12, iv13, iv14, iv15, iv16, iv17;
    public ImageView iv18, iv19, iv20, iv21, iv22, iv23, iv24, iv25, iv26, iv27, iv28, iv29, iv30;
    public ImageView iv31, iv32, iv33, iv34, iv35, iv36, iv37, iv38;

    public CheckBox p1_cb, p2_cb, p3_cb, p4_cb, p5_cb;
    public ImageView p1_color, p2_color, p3_color, p4_color, p5_color;
    public Label p1_name, p2_name, p3_name, p4_name, p5_name;
    public Label g_l_selected_card, g_l_selected_pirate;
    public ImageView g_i_default_deck;
    public Label g_l_card_left;

    // CARD COUNTS
    public Label g_cc_p1_1, g_cc_p1_2, g_cc_p1_3, g_cc_p1_4, g_cc_p1_5, g_cc_p1_6;
    public Label g_cc_p2_1, g_cc_p2_2, g_cc_p2_3, g_cc_p2_4, g_cc_p2_5, g_cc_p2_6;
    public Label g_cc_p3_1, g_cc_p3_2, g_cc_p3_3, g_cc_p3_4, g_cc_p3_5, g_cc_p3_6;
    public Label g_cc_p4_1, g_cc_p4_2, g_cc_p4_3, g_cc_p4_4, g_cc_p4_5, g_cc_p4_6;
    public Label g_cc_p5_1, g_cc_p5_2, g_cc_p5_3, g_cc_p5_4, g_cc_p5_5, g_cc_p5_6;

    // PIRATE LOCATIONS
    public ImageView g_i_p1_1, g_i_p1_2, g_i_p1_3, g_i_p1_4, g_i_p1_5, g_i_p1_6;
    public ImageView g_i_p2_1, g_i_p2_2, g_i_p2_3, g_i_p2_4, g_i_p2_5, g_i_p2_6;
    public ImageView g_i_p3_1, g_i_p3_2, g_i_p3_3, g_i_p3_4, g_i_p3_5, g_i_p3_6;
    public ImageView g_i_p4_1, g_i_p4_2, g_i_p4_3, g_i_p4_4, g_i_p4_5, g_i_p4_6;
    public ImageView g_i_p5_1, g_i_p5_2, g_i_p5_3, g_i_p5_4, g_i_p5_5, g_i_p5_6;

    // FIX IMAGES
    public ImageView g_i_default_1, g_i_default_2, g_i_default_3, g_i_default_4, g_i_default_5, g_i_default_6;

    public Button goBackButton, moveButton, endMoveButton;

    private int[] _pirateCount = new int[38];
    private boolean isFirstMove = true;

    public GameScreen(){

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gameScreen.fxml"));

        primaryStage.setTitle("Cartagena");
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

        GameServer.gs = this;

        updateMap();
        updatePirateColors();
        updatePirateLocations();
        updateCardCounts();

        if(!LocalPlayer.isHost){
            moveButton.setDisable(true);
            endMoveButton.setDisable(true);
        }

        p1_name.setText(LocalPlayer.username);
        p1_color.setImage(LocalPlayer.userColor.getImage());

        iv38.setImage(new Image(getClass().getResourceAsStream("/resources/boat.png")));
        iv1.setImage(new Image(getClass().getResourceAsStream("/resources/start.png")));

        selectedPirate = 0;
        selectedCard = 0;
        g_l_selected_card.setText("Not Selected");
        g_l_selected_pirate.setText("Not Selected");


        g_i_p1_1.setOnMouseClicked((e) -> selectPirate(1));
        g_i_p1_2.setOnMouseClicked((e) -> selectPirate(2));
        g_i_p1_3.setOnMouseClicked((e) -> selectPirate(3));
        g_i_p1_4.setOnMouseClicked((e) -> selectPirate(4));
        g_i_p1_5.setOnMouseClicked((e) -> selectPirate(5));
        g_i_p1_6.setOnMouseClicked((e) -> selectPirate(6));

        g_i_default_1.setImage(new Image(getClass().getResourceAsStream("/resources/bottle.png")));
        g_i_default_2.setImage(new Image(getClass().getResourceAsStream("/resources/keys.png")));
        g_i_default_3.setImage(new Image(getClass().getResourceAsStream("/resources/sword.png")));
        g_i_default_4.setImage(new Image(getClass().getResourceAsStream("/resources/skull.png")));
        g_i_default_5.setImage(new Image(getClass().getResourceAsStream("/resources/hat.png")));
        g_i_default_6.setImage(new Image(getClass().getResourceAsStream("/resources/pistol.png")));
        g_i_default_deck.setImage(new Image(getClass().getResourceAsStream("/resources/deck.png")));
        g_l_card_left.setText(Deck.size() + " cards left");

        g_i_default_1.setOnMouseClicked((e) -> selectCard(1));
        g_i_default_2.setOnMouseClicked((e) -> selectCard(2));
        g_i_default_3.setOnMouseClicked((e) -> selectCard(3));
        g_i_default_4.setOnMouseClicked((e) -> selectCard(4));
        g_i_default_5.setOnMouseClicked((e) -> selectCard(5));
        g_i_default_6.setOnMouseClicked((e) -> selectCard(6));

        for(int i = 0; i < _pirateCount.length; i++) _pirateCount[i] = 0;

    }

    private void selectPirate(int pirateID){

        if(pirateID == 0) {
            selectedPirate = 0;
            selectedBackCoordinate = 0;
            g_l_selected_pirate.setText("Not Selected");
            return;
        }

        if(LocalPlayer.pirates[pirateID -1].location == 37){
            JOptionPane.showMessageDialog(null, "This pirate is already in boat!");
            return;
        }
        selectedPirate = 0;
        selectedPirate = pirateID;
        g_l_selected_pirate.setText(pirateID + "");
    }

    private void selectCard(int cardID){
        if(cardID == 0 || !LocalPlayer.userCards.contains(CARD.getById(cardID))){
            selectedCard = 0;
            g_l_selected_card.setText("Not Selected");
        }

        else{
            selectedCard = cardID;
            g_l_selected_card.setText(CARD.values()[cardID-1] + "");
        }
    }

    private void findNearestBackLocation(){
        if(selectedPirate == 0){
            JOptionPane.showMessageDialog(null, "You must select a pirate to return back.");
            selectedBackCoordinate = 0;
            return;
        }

        for(int i = LocalPlayer.pirates[selectedPirate-1].location -1; i > 0; i--){
            for(int j = 0; j < 6; j++)
                if(LocalPlayer.pirates[j].location == i){
                    selectedBackCoordinate = i;
                    return;
                }

                for(RemotePlayer rp : PlayerData.remotePlayers){
                    for(int j = 0; j < 6; j++)
                        if(rp.pirates[j].location == i){
                            selectedBackCoordinate = i;
                            return;
                        }
                }
        }
    }

    private void updatePirateColors(){
        g_i_p1_1.setImage(LocalPlayer.userColor.getImage());
        g_i_p1_2.setImage(LocalPlayer.userColor.getImage());
        g_i_p1_3.setImage(LocalPlayer.userColor.getImage());
        g_i_p1_4.setImage(LocalPlayer.userColor.getImage());
        g_i_p1_5.setImage(LocalPlayer.userColor.getImage());
        g_i_p1_6.setImage(LocalPlayer.userColor.getImage());



        if(PlayerData.remotePlayers.size() > 0){
            RemotePlayer rp = PlayerData.remotePlayers.get(0);
            g_i_p2_1.setImage(rp.userColor.getImage());
            g_i_p2_2.setImage(rp.userColor.getImage());
            g_i_p2_3.setImage(rp.userColor.getImage());
            g_i_p2_4.setImage(rp.userColor.getImage());
            g_i_p2_5.setImage(rp.userColor.getImage());
            g_i_p2_6.setImage(rp.userColor.getImage());
        }
        if(PlayerData.remotePlayers.size() > 1){
            RemotePlayer rp = PlayerData.remotePlayers.get(1);
            g_i_p3_1.setImage(rp.userColor.getImage());
            g_i_p3_2.setImage(rp.userColor.getImage());
            g_i_p3_3.setImage(rp.userColor.getImage());
            g_i_p3_4.setImage(rp.userColor.getImage());
            g_i_p3_5.setImage(rp.userColor.getImage());
            g_i_p3_6.setImage(rp.userColor.getImage());
        }
        if(PlayerData.remotePlayers.size() > 2){
            RemotePlayer rp = PlayerData.remotePlayers.get(2);
            g_i_p4_1.setImage(rp.userColor.getImage());
            g_i_p4_2.setImage(rp.userColor.getImage());
            g_i_p4_3.setImage(rp.userColor.getImage());
            g_i_p4_4.setImage(rp.userColor.getImage());
            g_i_p4_5.setImage(rp.userColor.getImage());
            g_i_p4_6.setImage(rp.userColor.getImage());
        }
        if(PlayerData.remotePlayers.size() > 3){
            RemotePlayer rp = PlayerData.remotePlayers.get(3);
            g_i_p5_1.setImage(rp.userColor.getImage());
            g_i_p5_2.setImage(rp.userColor.getImage());
            g_i_p5_3.setImage(rp.userColor.getImage());
            g_i_p5_4.setImage(rp.userColor.getImage());
            g_i_p5_5.setImage(rp.userColor.getImage());
            g_i_p5_6.setImage(rp.userColor.getImage());
        }


    }

    private void updatePirateLocations(){

        for(int i = 0; i < _pirateCount.length; i++) _pirateCount[i] = 0;

        ImageView[][] imageViews = {{g_i_p1_1, g_i_p1_2, g_i_p1_3, g_i_p1_4, g_i_p1_5, g_i_p1_6},
                {g_i_p2_1, g_i_p2_2, g_i_p2_3, g_i_p2_4, g_i_p2_5, g_i_p2_6},
                {g_i_p3_1, g_i_p3_2, g_i_p3_3, g_i_p3_4, g_i_p3_5, g_i_p3_6},
                {g_i_p4_1, g_i_p4_2, g_i_p4_3, g_i_p4_4, g_i_p4_5, g_i_p4_6},
                {g_i_p5_1, g_i_p5_2, g_i_p5_3, g_i_p5_4, g_i_p5_5, g_i_p5_6}};

        Platform.runLater(
                () -> {

                    for(int i = 0; i < PlayerData.remotePlayers.size() + 1; i++)
                        for(int j = 0; j < 6; j++){

                            try {
                                int[] widthHeight;
                                if (i == 0)
                                    widthHeight = GameMap.getLocation(LocalPlayer.pirates[j].location,
                                            _pirateCount[LocalPlayer.pirates[j].location]++);
                                else
                                    widthHeight = GameMap.getLocation(PlayerData.remotePlayers.get(i - 1).pirates[j].location,
                                            _pirateCount[PlayerData.remotePlayers.get(i - 1).pirates[j].location]++);

                                imageViews[i][j].setLayoutX(widthHeight[0]);
                                imageViews[i][j].setLayoutY(widthHeight[1]);
                            }catch (ArrayIndexOutOfBoundsException e){}
                        }

                }
        );



    }

    private void updateCardCounts(){

        ImageView[] playerColors = {p1_color, p2_color, p3_color, p4_color, p5_color};
        Label[] playerNames = {p1_name, p2_name, p3_name, p4_name, p5_name};
        CheckBox[] playerChecks = {p1_cb, p2_cb, p3_cb, p4_cb, p5_cb};

        Label[][] userCardCounts = {
                {g_cc_p1_1, g_cc_p1_2, g_cc_p1_3, g_cc_p1_4, g_cc_p1_5, g_cc_p1_6},
                {g_cc_p2_1, g_cc_p2_2, g_cc_p2_3, g_cc_p2_4, g_cc_p2_5, g_cc_p2_6},
                {g_cc_p3_1, g_cc_p3_2, g_cc_p3_3, g_cc_p3_4, g_cc_p3_5, g_cc_p3_6},
                {g_cc_p4_1, g_cc_p4_2, g_cc_p4_3, g_cc_p4_4, g_cc_p4_5, g_cc_p4_6},
                {g_cc_p5_1, g_cc_p5_2, g_cc_p5_3, g_cc_p5_4, g_cc_p5_5, g_cc_p5_6}};

        userCardCounts[0][0].setText(Collections.frequency(LocalPlayer.userCards, CARD.Bottle) + "");
        userCardCounts[0][1].setText(Collections.frequency(LocalPlayer.userCards, CARD.Keys) + "");
        userCardCounts[0][2].setText(Collections.frequency(LocalPlayer.userCards, CARD.Sword) + "");
        userCardCounts[0][3].setText(Collections.frequency(LocalPlayer.userCards, CARD.Skull) + "");
        userCardCounts[0][4].setText(Collections.frequency(LocalPlayer.userCards, CARD.Hat) + "");
        userCardCounts[0][5].setText(Collections.frequency(LocalPlayer.userCards, CARD.Pistol) + "");
        playerChecks[0].setSelected(true);
        playerColors[0].setImage(LocalPlayer.userColor.getImage());
        playerNames[0].setText(LocalPlayer.username);

        for(int i = 0; i < PlayerData.remotePlayers.size(); i++){
            RemotePlayer rp = PlayerData.remotePlayers.get(i);
            userCardCounts[i+1][0].setText(Collections.frequency(rp.userCards, CARD.Bottle) + "");
            userCardCounts[i+1][1].setText(Collections.frequency(rp.userCards, CARD.Keys) + "");
            userCardCounts[i+1][2].setText(Collections.frequency(rp.userCards, CARD.Sword) + "");
            userCardCounts[i+1][3].setText(Collections.frequency(rp.userCards, CARD.Skull) + "");
            userCardCounts[i+1][4].setText(Collections.frequency(rp.userCards, CARD.Hat) + "");
            userCardCounts[i+1][5].setText(Collections.frequency(rp.userCards, CARD.Pistol) + "");

            playerChecks[i+1].setSelected(rp.isReady);
            playerColors[i+1].setImage(rp.userColor.getImage());
            playerNames[i+1].setText(rp.username);

        }

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

    private int calculateNextMove(int location, CARD card){
        int newCoordinate = GameMap.findNext(location, card);
        if(newCoordinate == 37) return 37;
        for(int i = 0; i < 6; i++)
            if(LocalPlayer.pirates[i].location == newCoordinate)
                return calculateNextMove(newCoordinate, card);
        for(RemotePlayer rp : PlayerData.remotePlayers)
            for(int i = 0; i < 6; i++)
                if(rp.pirates[i].location == newCoordinate)
                    return calculateNextMove(newCoordinate, card);

        return newCoordinate;

    }

    public void goBackButton(){

        if(selectedPirate == 0){
            JOptionPane.showMessageDialog(null, "You must select a pirate!");
            return;
        }

        findNearestBackLocation();
        if(selectedBackCoordinate == 0){
            JOptionPane.showMessageDialog(null, "There is no place to return back for this pirate!");
            return;
        }


        LocalPlayer.pirates[selectedPirate-1].location = selectedBackCoordinate;
        for(int i = 0; i < _pirateCount[selectedBackCoordinate]; i++)
            LocalPlayer.addCard(Deck.draw());

        g_l_card_left.setText(Deck.size() + " cards left.");

        moveButton.setDisable(true);
        refreshFrame();

        if(!isFirstMove){
            goBackButton.setDisable(true);
            GameData.currentQueue = (GameData.currentQueue + 1) % (PlayerData.remotePlayers.size() + 1);
            endMoveButton.setDisable(true);
            isFirstMove = true;
        }

        isFirstMove = false;

        // Host
        if(LocalPlayer.isHost){
            GameServer.sendGameStatus();
        }
        // Remote
        else{
            Client.sendToServerWoutResponse(getGameStatus().setCode("810"));
        }


    }

    public void moveButton(){
        if(selectedPirate == 0){
            JOptionPane.showMessageDialog(null, "You must select a pirate!");
            return;
        }

        if(selectedCard == 0){
            JOptionPane.showMessageDialog(null, "You must select a card!");
            return;
        }


        LocalPlayer.pirates[selectedPirate-1].location = calculateNextMove(
                LocalPlayer.pirates[selectedPirate-1].location,
                CARD.getById(selectedCard));
        LocalPlayer.useCard(CARD.getById(selectedCard));


        moveButton.setDisable(true);
        refreshFrame();

        isFirstMove = false;

        // Host
        if(LocalPlayer.isHost){
            GameServer.sendGameStatus();
        }
        // Remote
        else{
            Client.sendToServerWoutResponse(getGameStatus().setCode("810"));
        }

    }
        public void endMoveButton(){
        GameData.currentQueue = (GameData.currentQueue + 1) % (PlayerData.remotePlayers.size() + 1);

        moveButton();
        endMoveButton.setDisable(true);
        goBackButton.setDisable(true);
        isFirstMove = true;

    }

    public Message getGameStatus(){
        Message returnMessage = new Message()
                .setCode("810")
                .setUser(LocalPlayer.username);

        for(int i = 0; i < 6; i++)
            returnMessage.addContent(LocalPlayer.username, LocalPlayer.pirates[i].location + "");
        returnMessage.addContent(LocalPlayer.getCards());

        for(RemotePlayer rp : PlayerData.remotePlayers){
            for(int i = 0; i < 6; i++)
                returnMessage.addContent(rp.username, rp.pirates[i].location + "");
            returnMessage.addContent(rp.getCards());
        }

        returnMessage.addContent(Deck.export());
        returnMessage.addContent(GameData.currentQueue + "");

        return returnMessage;
    }

    public String newMessageArrives(Message message) {


        switch (message.getCode()){
            case "710":
                System.out.println("Request from server: " + message);
                refreshData(message);
                break;
            case "720":
                finishGame(message.getContent().get(0));
                break;
            case "810":
                System.out.println("Request from client: " + message);
                refreshData(message);
                GameServer.sendToClients(message);
                controlFinish();
                break;
        }

        return "";
    }

    private void refreshData(Message message){
        GameData.updateGameStatus(message);
        if(GameData.currentQueue == LocalPlayer.queue){
            Platform.runLater(() -> {
                endMoveButton.setDisable(false);
                goBackButton.setDisable(false);
                if(isFirstMove)
                    moveButton.setDisable(false);
            });
        }
        Platform.runLater(() -> refreshFrame());


    }

    private void refreshFrame(){

        updatePirateLocations();
        updateCardCounts();
        g_l_card_left.setText(Deck.size() + " cards left");

        selectCard(0);
        selectPirate(0);
    }

    private void controlFinish(){
        boolean isFinished = true;
        for(int i = 0; i < 6; i++)
            if(LocalPlayer.pirates[i].location != 37){
                isFinished = false;
                break;
            }
        if(isFinished) finishGame(LocalPlayer.username);


        for(RemotePlayer rp : PlayerData.remotePlayers){
            isFinished = true;

            for(int i = 0; i < 6; i++)
                if(rp.pirates[i].location != 37){
                    isFinished = false;
                    break;
                }
            if(isFinished)finishGame(rp.username);

        }

    }

    private void finishGame(String winnerName){
        if(LocalPlayer.isHost)
            GameServer.sendToClients(new Message().setCode("720").addContent(winnerName));

        JOptionPane.showMessageDialog(null, "Game finished. WINNER: " + winnerName);
        Platform.exit();
        System.exit(1);
    }
}
