package no.sonat.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShootResult {
    private String metaType;
    private String ok;
    private String hit;

    public ShootResult(@JsonProperty("class") String metaType, @JsonProperty("ok") String ok, @JsonProperty("hit") String hit) {
        this.metaType = metaType;
        this.ok = ok;
        this.hit = hit;
    }

    public String getMetaType() {
        return metaType;
    }

    public void setMetaType(String metaType) {
        this.metaType = metaType;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public String getHit() {
        return hit;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }

    public boolean isSplash() {
        return hit.equals("SPLASH");
    }

    public boolean isBang() {
        return hit.equals("BANG");
    }

    public boolean isSunk() {
        return hit.equals("SUNK");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String metaType;
        private String ok;
        private String hit;

        private Builder() {
        }


        public Builder metaType(String metaType) {
            this.metaType = metaType;
            return this;
        }

        public Builder ok(String ok) {
            this.ok = ok;
            return this;
        }

        public Builder hit(String hit) {
            this.hit = hit;
            return this;
        }

        public ShootResult build() {
            return new ShootResult(metaType, ok, hit);
        }
    }
}
