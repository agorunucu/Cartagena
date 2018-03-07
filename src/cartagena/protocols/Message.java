package cartagena.protocols;

import cartagena.players.LocalPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {

    private String username = "Undefined";
    private String messageCode = "000";
    private List<String> content = new ArrayList<>();

    public Message(){

    }

    public Message(String messageCode, String username){
        this.messageCode = messageCode;
        this.username = username;
    }

    public Message(String message){
        String[] splittedMessage = message.split("\\|");
        messageCode = splittedMessage[0].split("&&")[0];
        username = splittedMessage[0].split("&&")[1];
        for(int i = 0; i < splittedMessage.length -1; i++){
            content.add(splittedMessage[i+1]);
        }

    }

    public String getCode(){
        return messageCode;
    }
    public String getUser(){
        return username;
    }
    public List<String> getContent(){
        return content;
    }

    public Message setCode(String code){
        this.messageCode = code;
        return this;
    }
    public Message setUser(String username){
        this.username = username;
        return this;
    }
    public Message addContent(String... data){
        if(data.length == 0)
            return this;
        content.addAll(Arrays.asList(data));
        return this;
    }

    public String toString(){
        String returnString = messageCode + "&&" + username;

        for(String mess : content){
            returnString += "|" + mess;
        }
        return returnString;
    }
}
