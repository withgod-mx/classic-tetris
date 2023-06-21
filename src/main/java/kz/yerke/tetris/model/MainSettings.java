package kz.yerke.tetris.model;

import java.util.HashMap;
import java.util.Map;

public class MainSettings {

    private GameEventType eventType = GameEventType.INIT;
    private Language language = Language.ENGLISH;

    private Map<String, String> localization = new HashMap<>();

    public MainSettings() {
        localizationInit();
    }

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

    public String getLocalizationText(String code) {
        String languageCode = getLanguageCode();
        return this.localization.get(languageCode + "-" + code);
    }

    private String getLanguageCode() {
        switch (this.language) {
            case RUSSIAN -> {
                return "ru";
            }
            case QAZAQ -> {
                return "kz";
            }
            default -> {
                return "en";
            }
        }

    }

    private void localizationInit() {

        /*
        Russian
         */
        localization.put("ru-start-game", "Новая игра");
        localization.put("ru-language", "Сменить язык");
        localization.put("ru-exit", "Выход");

        localization.put("ru-full-line", "ПОЛНЫХ СТРОК");
        localization.put("ru-level", "УРОВЕНЬ");
        localization.put("ru-score", "СЧЕТ");

        localization.put("ru-left", "НАЛЕВО");
        localization.put("ru-right", "НАПРАВО");
        localization.put("ru-fast", "УСКОРИТЬ");
        localization.put("ru-rotation", "ПОВОРОТ");

     /*
        English
         */
        localization.put("en-start-game", "New Game");
        localization.put("en-language", "Language");
        localization.put("en-exit", "Exit");

        localization.put("en-full-line", "FULL LINES");
        localization.put("en-level", "LEVEL");
        localization.put("en-score", "SCORE");

        localization.put("en-left", "LEFT");
        localization.put("en-right", "RIGHT");
        localization.put("en-fast", "FAST");
        localization.put("en-rotation", "ROTATION");

        /*
        Kazakh
         */
        localization.put("kz-start-game", "Жаңа ойын");
        localization.put("kz-language", "Тілді өзгерту");
        localization.put("kz-exit", "Шығу");

        localization.put("kz-full-line", "ТОЛЫҚ ЖОЛДАР");
        localization.put("kz-level", "ДЕҢГЕЙ");
        localization.put("kz-score", "ҰПАЙ");

        localization.put("kz-left", "СОЛҒА");
        localization.put("kz-right", "ОҢҒА");
        localization.put("kz-fast", "ТЕЗДЕТУ");
        localization.put("kz-rotation", "БҰРУ");



    }

}
