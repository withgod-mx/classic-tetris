package kz.yerke.tetris.engine;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;
import kz.yerke.tetris.gui.WindowDraw;
import kz.yerke.tetris.model.GameEventType;
import kz.yerke.tetris.model.Language;
import kz.yerke.tetris.model.MainSettings;

import java.io.IOException;

public class LanguageSelector {

    private MainSettings mainSettings;

    private Terminal terminal;

    private WindowDraw windowDraw;

    TerminalPosition startPosition = new TerminalPosition(0, 0);

    public LanguageSelector(MainSettings mainSettings, Terminal terminal) {
        this.mainSettings = mainSettings;
        this.terminal = terminal;
        this.windowDraw = new WindowDraw(terminal, startPosition);
    }

    public void start() throws IOException {
        boolean selectLanguage = false;
        while(!selectLanguage) {
            KeyStroke keyStroke = terminal.pollInput();
            if(keyStroke != null && (keyStroke.getKeyType() == KeyType.ArrowDown)) {
                switch (mainSettings.getLanguage()) {
                    case ENGLISH -> {
                        this.mainSettings.setLanguage(Language.RUSSIAN);
                        windowDraw.draw(" ", 27, 10);
                        windowDraw.draw("→", 27, 12);
                    }
                    case RUSSIAN -> {
                        this.mainSettings.setLanguage(Language.QAZAQ);
                        windowDraw.draw(" ", 27, 12);
                        windowDraw.draw("→", 27, 14);
                    }

                }
            } else if (keyStroke != null && (keyStroke.getKeyType() == KeyType.ArrowUp)) {

                switch (mainSettings.getLanguage()) {
                    case QAZAQ -> {
                        this.mainSettings.setLanguage(Language.RUSSIAN);
                        windowDraw.draw("→", 27, 12);
                        windowDraw.draw(" ", 27, 14);
                    }
                    case RUSSIAN -> {
                        this.mainSettings.setLanguage(Language.ENGLISH);
                        windowDraw.draw("→", 27, 10);
                        windowDraw.draw(" ", 27, 12);
                    }
                }

            } else if (keyStroke != null && (keyStroke.getKeyType() == KeyType.Enter)) {
                selectLanguage = true;
                mainSettings.setEventType(GameEventType.INIT);
            }
        }
    }


}
