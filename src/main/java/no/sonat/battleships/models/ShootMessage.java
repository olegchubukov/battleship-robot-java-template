package no.sonat.battleships.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lars on 20.06.15.
 */
public class ShootMessage {

    public final Coordinate coordinate;
    public final @JsonProperty("class") String metaType = "game.messages.ShootMessage";

    public ShootMessage(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
