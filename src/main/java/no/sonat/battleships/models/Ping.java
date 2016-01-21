package no.sonat.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lars on 21.01.16.
 */
public class Ping {
    public final @JsonProperty("class") String metaType = "game.messages.Ping";
}
