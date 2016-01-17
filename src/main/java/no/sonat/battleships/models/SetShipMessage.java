package no.sonat.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lars on 20.06.15.
 */
public class SetShipMessage {
    public final Ship ship;
    public final @JsonProperty("class") String metaType = "game.messages.SetShipMessage";

    public SetShipMessage(Ship ship) {
        this.ship = ship;
    }
}
