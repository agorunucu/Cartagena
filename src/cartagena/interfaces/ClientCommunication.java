package cartagena.interfaces;

import cartagena.protocols.Message;

public interface ClientCommunication {
    String newMessageArrivesFromServer(Message message);

}
