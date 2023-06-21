package kz.yerke.tetris.model;

public class MainSettings {

    private GameEventType eventType = GameEventType.INIT;
    private Language language = Language.ENGLISH;

    public GameEventType getEventType() {
        return eventType;
    }

    public void setEventType(GameEventType eventType) {
        this.eventType = eventType;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
