package server.messages;

import java.io.Serializable;

/**
 * Created by joe on 10/11/14.
 */
public class ClientMessage implements Serializable {

    private String[] message;

    public ClientMessage(String[] message) {
        this.message = message;
    }

    public String[] getMessage() {
        return message;
    }
}
