package cartagena;


import cartagena.GUI.GameScreen;
import cartagena.GUI.HostGameScreen;
import cartagena.GUI.JoinGameScreen;
import cartagena.enums.COLOR;
import cartagena.network.Client;
import cartagena.players.LocalPlayer;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        int a = JOptionPane.showOptionDialog(null,
                "What would you like to do?",
                "GAME",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"HOST GAME", "JOIN GAME"}, // this is the array
                "default");


        switch (a){
            case 1: joinGame(); break;
            case 0: hostGame(); break;
            default: System.exit(1);
        }

    }

    public static void hostGame(){
        LocalPlayer.isHost = true;
        LocalPlayer.isReady = true;

        String username = "";
        COLOR usercolor = null;
        try {
            while (username.equals("")) {
                username = JOptionPane.showInputDialog("Enter a username");
            }

            usercolor = COLOR.values()[JOptionPane.showOptionDialog(null, "Pick a color", "COLOR",
                    0, JOptionPane.INFORMATION_MESSAGE, null, COLOR.values(), COLOR.values()[0])];

        } catch (NullPointerException | ArrayIndexOutOfBoundsException e){
            System.exit(1);
        }

        LocalPlayer.username = username;
        LocalPlayer.userColor = usercolor;


        System.out.println("Hosting game...");

        // Open hostgame screen
        HostGameScreen.launch(HostGameScreen.class);
    }
    public static void joinGame(){

        LocalPlayer.isHost = false;
        LocalPlayer.isReady = false;
        System.out.println("Joining game...");

        JoinGameScreen.launch(JoinGameScreen.class);


    }
}
