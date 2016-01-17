package no.sonat.battleships;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by lars on 16.06.15.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        String token = args[0];
        VeryDumbRobot robot = new VeryDumbRobot(token);
        robot.initiate();
    }
}
