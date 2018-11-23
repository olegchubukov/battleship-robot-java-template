package no.sonat.battleships;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.sonat.battleships.models.*;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.drafts.Draft_76;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by lars on 16.06.15.
 */
public class BattleshipRobot {

    final ObjectMapper json = new ObjectMapper();
    final String token;
    WebSocketClient wsClient;

    public BattleshipRobot(String token) throws Exception {
        this.token = token;
    }

    public void initiate() throws URISyntaxException {

        System.out.println("before connect");

        Map<String, String> headers = new HashMap<String, String>(){{
            put("Authorization", "Bearer " + token);
        }};

        // ping server every 5 seconds to keep ws connection alive.
        Thread pingThread = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(5000);

                    wsClient.send(json.writeValueAsString(new Ping()));
                }
            } catch (InterruptedException e) {
                // ignore. Let the thread die.

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        this.wsClient = new WebSocketClient(new URI("ws://localhost:9000/connect"), new Draft_10(), headers, 500) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                System.out.println("Connected!");

                pingThread.start();
            }

            @Override
            public void onMessage(String s) {

                System.out.println(String.format("message received: %1$s", s));

                final JsonNode msg;
                try {
                    msg = json.readTree(s);
                } catch (IOException e) {
                    throw new RuntimeException("error reading json", e);
                }
                final String type = msg.get("class").asText();

                switch (type) {
                    case "game.broadcast.GameIsInPlanningMode":
                        placeShips();
                        break;
                    case "game.broadcast.GameIsStarted":
                        onGameStart(msg);
                        break;
                    case "game.messages.ItsYourTurnMessage":
                        shoot();
                        break;
                    case "game.broadcast.GameOver":
                        // allright!! be ready for next game
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                System.out.println("Disconnected!");
            }

            @Override
            public void onError(Exception e) {
                System.out.println("onError");
                System.out.println(e.getMessage());
            }


        };

        wsClient.connect();
    }


    /**
     * This method is called when game is in planning mode, and the robot should place ships
     */
    public void placeShips() {
        SetShipMessage ship1 = new SetShipMessage(new Ship(new Coordinate[] {new Coordinate(2,2), new Coordinate(2,3)}));
        SetShipMessage ship2 = new SetShipMessage(new Ship(new Coordinate[] {new Coordinate(4,4), new Coordinate(4,5), new Coordinate(4,6)}));
        SetShipMessage ship3 = new SetShipMessage(new Ship(new Coordinate[] {new Coordinate(8,1), new Coordinate(9,1), new Coordinate(10, 1)}));
        SetShipMessage ship4 = new SetShipMessage(new Ship(new Coordinate[] {new Coordinate(1,1), new Coordinate(2,1), new Coordinate(3,1), new Coordinate(4,1)}));
        SetShipMessage ship5 = new SetShipMessage(new Ship(new Coordinate[] {new Coordinate(8,5), new Coordinate(9,5), new Coordinate(10,5), new Coordinate(11,5)}));
        SetShipMessage ship6 = new SetShipMessage(new Ship(new Coordinate[] {new Coordinate(11,7), new Coordinate(11,8),new Coordinate(11,9), new Coordinate(11,10), new Coordinate(11, 11)}));

        try {
            wsClient.send(json.writeValueAsString(ship1));
            wsClient.send(json.writeValueAsString(ship2));
            wsClient.send(json.writeValueAsString(ship3));
            wsClient.send(json.writeValueAsString(ship4));
            wsClient.send(json.writeValueAsString(ship5));
            wsClient.send(json.writeValueAsString(ship6));
        } catch (Exception e) {
            throw new RuntimeException("marshalling failure", e);
        }

    }

    final List<Coordinate> availableCoordinates = new ArrayList<>();
    final Random rand = new Random();

    /**
     * This method is called when the game is started. Use this method to do any initialization of shooting algorithm.
     * At this moment, it simply fills a list with available coordinates, which enables the robot to avoid shooting the
     * same place twice (which is allowed, but not smart)
     * @param msg
     */
    public void onGameStart(JsonNode msg) {
        // if another game is finished, available coordinates might still contain old data. Clear it and refill it.
        availableCoordinates.clear();
        for (int x = 0; x < 12; x++) {
            for (int y = 0; y < 12; y++) {
                availableCoordinates.add(new Coordinate(x, y));
            }
        }
    }

    /**
     * This method is called when it is your robots turn to shoot.
     */
    public void shoot() {
        int idx = rand.nextInt(availableCoordinates.size());
        Coordinate coord = availableCoordinates.get(idx);

        // remove coordinate from available coordinates, so I don't shoot at the same coordinate twice.
        availableCoordinates.remove(idx);
        ShootMessage shootMessage = new ShootMessage(coord);

        try {
            wsClient.send(json.writeValueAsString(shootMessage));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error marshalling object", e);
        }
    }
}
