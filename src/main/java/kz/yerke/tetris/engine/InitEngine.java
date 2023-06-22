package kz.yerke.tetris.engine;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;
import kz.yerke.tetris.gui.WindowDraw;
import kz.yerke.tetris.model.GameEventType;
import kz.yerke.tetris.model.MainSettings;
import kz.yerke.tetris.model.ObjectMove;

import java.io.IOException;

public class InitEngine {

    private MainSettings mainSettings;

    private Terminal terminal;

    private WindowDraw windowDraw;

    TerminalPosition startPosition = new TerminalPosition(0, 0);

    public InitEngine(MainSettings mainSettings, Terminal terminal) {
        this.mainSettings = mainSettings;
        this.terminal = terminal;
        this.windowDraw = new WindowDraw(terminal, startPosition);
    }

    public void start() throws IOException {
        boolean changeGameEvent = false;
        while(!changeGameEvent) {
            KeyStroke keyStroke = terminal.pollInput();
            if(keyStroke != null && (keyStroke.getKeyType() == KeyType.ArrowDown)) {
                switch (this.mainSettings.getEventType()) {
                    case INIT, START_GAME -> {
                        this.mainSettings.setEventType(GameEventType.LANGUAGE);
                        windowDraw.draw(" ", 27, 10);
                        windowDraw.draw("→", 27, 12);
                    }
                    case LANGUAGE -> {
                        this.mainSettings.setEventType(GameEventType.EXIT);
                        windowDraw.draw("→", 27, 14);
                        windowDraw.draw(" ", 27, 12);
                    }
                }

            } else if (keyStroke != null && (keyStroke.getKeyType() == KeyType.ArrowUp)) {
                switch (this.mainSettings.getEventType()) {
                    case EXIT -> {
                        this.mainSettings.setEventType(GameEventType.LANGUAGE);
                        windowDraw.draw("→", 27, 12);
                        windowDraw.draw(" ", 27, 14);
                    }
                    case LANGUAGE -> {
                        this.mainSettings.setEventType(GameEventType.START_GAME);
                        windowDraw.draw("→", 27, 10);
                        windowDraw.draw(" ", 27, 12);
                    }
                }
            } else if (keyStroke != null && (keyStroke.getKeyType() == KeyType.Enter)) {
                if (this.mainSettings.getEventType() == GameEventType.INIT) {
                    mainSettings.setEventType(GameEventType.START_GAME);
                }
                changeGameEvent = true;
            }


        }
    }

}
