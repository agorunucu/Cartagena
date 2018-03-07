package cartagena.network;

import cartagena.data.GameData;
import cartagena.interfaces.ServerCommunication;
import cartagena.protocols.Message;

import java.io.*;
import java.net.Socket;

public class Server extends Thread {

    private Socket socket;
    private ServerCommunication sc;

    private InputStream in = null;
    private OutputStream out = null;


    public Server(Socket socket, ServerCommunication sc) {
        this.socket = socket;
        this.sc = sc;

        System.out.println("New player connected. IP: " + socket.getInetAddress().getHostAddress() + " Port: " + socket.getPort());
        start();


    }

    public void run() {
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String request;
            while ((request = br.readLine()) != null) {

                if(!GameData.isGameStarted){
                    String output = "";
                    if(!GameData.isGameStarted)
                        output = sc.newMessageArrivesFromClient(request) + "\n";
                    else{
                        GameServer.receivedData(new Message(request));
                        output = new Message().setCode("000").toString();
                    }


                    out.write(output.getBytes());
                }else{
                    GameServer.receivedData(new Message(request));
                }

            }

        } catch (IOException ex) {
            System.out.println("Unable to get streams from client");
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendToClient(String message) {
        try {
            System.out.println("Request to client w/o response: " + message);
            out.write((message + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
