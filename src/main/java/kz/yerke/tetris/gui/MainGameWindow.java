package kz.yerke.tetris.gui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;
import kz.yerke.tetris.model.MainSettings;

import java.io.IOException;

public class MainGameWindow {

    Terminal terminal;
    TerminalPosition startPosition = new TerminalPosition(0, 0);
    WindowDraw windowDraw;
    public MainGameWindow(Terminal terminal) throws IOException {
        this.terminal = terminal;
        this.windowDraw = new WindowDraw(terminal, startPosition);
    }

    /**
     * For game start position column 27
     * @throws IOException
     */
    public void buildGameWindow() throws IOException, InterruptedException {
        terminal.setForegroundColor(TextColor.ANSI.GREEN);
        for (int i = 1; i <= 20; i++) {
            windowDraw.draw("<! . . . . . . . . . .!>", 25, i);
            Thread.sleep(100);
        }
        Thread.sleep(100);
        windowDraw.draw("<!====================!>", 25, 21);
        Thread.sleep(100);
        windowDraw.draw("<!\\/\\/\\/\\/\\/\\/\\/\\/\\/\\//!>", 25, 22);

        Thread.sleep(100);
    }

    public void updateLocalizationText(MainSettings mainSettings) throws IOException, InterruptedException {
        terminal.setForegroundColor(TextColor.ANSI.GREEN);
        windowDraw.draw(mainSettings.getLocalizationText("full-line") + ":", 0, 1);
        windowDraw.draw("3", 16, 1);
        Thread.sleep(100);
        windowDraw.draw(mainSettings.getLocalizationText("level") + ":", 0, 2);
        windowDraw.draw("1", 16, 2);
        Thread.sleep(100);
        windowDraw.draw(mainSettings.getLocalizationText("score") + ":", 3, 3);
        windowDraw.draw("0", 16, 3);

        Thread.sleep(100);
        windowDraw.draw("←  " + mainSettings.getLocalizationText("left"), 55, 2);
        windowDraw.draw("→  " + mainSettings.getLocalizationText("right"), 65, 2);
        Thread.sleep(100);
        windowDraw.draw("↑ " + mainSettings.getLocalizationText("rotation"), 55, 3);
        Thread.sleep(100);
        windowDraw.draw("↓ " + mainSettings.getLocalizationText("fast"), 55, 5);
    }

}
