package cartagena.network;

import cartagena.GUI.JoinGameScreen;
import cartagena.data.GameData;
import cartagena.enums.COLOR;
import cartagena.interfaces.ClientCommunication;
import cartagena.players.LocalPlayer;
import cartagena.protocols.Message;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public final class Client {

    static Socket echoSocket = null;
    static PrintWriter out = null;
    static BufferedReader in = null;

    public static ClientCommunication cm;

    public static void initialize(String host, int port, ClientCommunication cmm) {
        try {
            cm = cmm;

            String serverHostname = host;

            System.out.println("Connecting to host " + serverHostname + " on port " + port + ".");


            try {
                echoSocket = new Socket(serverHostname, 8081);
                out = new PrintWriter(echoSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            } catch (UnknownHostException e) {
                JOptionPane.showMessageDialog(null,"Unknown host: " + serverHostname);
                System.exit(1);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,"Unable to get streams from server");
                System.exit(1);
            }

            setUsername();
            pickColor();
            getUsers();
            getMap();

            synchronized(JoinGameScreen.syncObject) {
                JoinGameScreen.syncObject.notify();
            }

            new Thread(() ->
            {
                while(true){
                        try{
                            String obj = in.readLine();
                            if(!GameData.isGameStarted)
                                cm.newMessageArrivesFromServer(new Message(obj));
                            else
                                GameServer.receivedData(new Message(obj));
                        }
                        catch(IOException e){ e.printStackTrace(); }
                    }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void getUsers() {
        cm.newMessageArrivesFromServer(sendToServer(new Message("220", LocalPlayer.username)));
    }

    private static void getMap() {
        cm.newMessageArrivesFromServer(sendToServer(new Message("230", LocalPlayer.username)));
    }

    private static void setUsername() {
        String username = "";

        try {
            String usernamePrompt = "Enter a username";
            while (true) {
                username = JOptionPane.showInputDialog(usernamePrompt);
                if(username.equals("")) continue;

                String response = sendToServer(new Message().setCode("310").addContent(username)).getCode();
                if(response.equals("110")){
                    LocalPlayer.username = username;
                    return;
                }
                else if(response.equals("510")){
                    JOptionPane.showMessageDialog(null,"Game Lobby is full!");
                    System.exit(1);
                }
                else{ // 520
                    usernamePrompt = "This username have picked. Use different username.";
                }

            }

        } catch (NullPointerException | ArrayIndexOutOfBoundsException e){
            System.exit(1);
        }
    }

    private static void pickColor() {
        String[] colors = sendToServer(new Message().setCode("210").setUser(LocalPlayer.username))
                .getContent()
                .toArray(new String[0]);

        COLOR usercolor = COLOR.getColorByName(
                colors[
                        JOptionPane.showOptionDialog(null, "Pick a color", "COLOR",
                0, JOptionPane.INFORMATION_MESSAGE, null, colors, colors[0])
                        ]);

        LocalPlayer.userColor = usercolor;

        sendToServer(new Message().setCode("410").setUser(LocalPlayer.username).addContent(usercolor.toString()));

    }

    public static Message sendToServer(Message send) {
        String response = null;

        try {
            out.println(send);
            System.out.println("Request to server   : " + send);
            response = in.readLine();
            System.out.println("Response from server: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Message(response);
    }

    public static void sendToServerWoutResponse(Message message){
        out.println(message);
        System.out.println("Sent to server w/o response: " + message);

    }
}
