package kz.yerke.tetris.gui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;
import kz.yerke.tetris.model.MainSettings;

import java.io.IOException;

public class InitialWindowGUI {

    Terminal terminal;
    TerminalPosition startPosition = new TerminalPosition(0, 0);
    WindowDraw windowDraw;

    public InitialWindowGUI(Terminal terminal) {
        this.terminal = terminal;
        this.windowDraw = new WindowDraw(terminal, startPosition);
    }

    public void drawInitWindow() throws IOException {
        drawTetrisText();
        terminal.setForegroundColor(TextColor.ANSI.YELLOW);
        windowDraw.draw("→", 27, 10);
/*        windowDraw.draw("Start Game", 30, 10);
        windowDraw.draw("Language", 30, 12);
        windowDraw.draw("Exit", 30, 14);*/
    }

    public void updateLocalizationText(MainSettings mainSettings) throws IOException {
        terminal.setForegroundColor(TextColor.ANSI.YELLOW);
        windowDraw.draw(mainSettings.getLocalizationText("start-game"), 30, 10);
        windowDraw.draw(mainSettings.getLocalizationText("language"), 30, 12);
        windowDraw.draw(mainSettings.getLocalizationText("exit"), 30, 14);
    }



    private void drawTetrisText() throws IOException {
        terminal.setForegroundColor(TextColor.ANSI.CYAN);
        windowDraw.draw(" ______  ____    ______  ____    ______  ____  ", 10, 1);
        windowDraw.draw("/\\__  _\\/\\  _`\\ /\\__  _\\/\\  _`\\ /\\__  _\\/\\  _`\\    ", 10, 2);
        windowDraw.draw("\\/_/\\ \\/\\ \\ \\L\\_\\/_/\\ \\/\\ \\ \\L\\ \\/_/\\ \\/\\ \\,\\L\\_\\", 10, 3);
        windowDraw.draw("   \\ \\ \\ \\ \\  _\\L  \\ \\ \\ \\ \\ ,  /  \\ \\ \\ \\/_\\__ \\", 10, 4);
        windowDraw.draw("    \\ \\ \\ \\ \\ \\L\\ \\ \\ \\ \\ \\ \\ \\\\ \\  \\_\\ \\__/\\ \\L\\ \\ ", 10, 5);

        windowDraw.draw("     \\ \\_\\ \\ \\____/  \\ \\_\\ \\ \\_\\ \\_\\/\\_____\\ `\\____\\", 10, 6);
        windowDraw.draw("      \\/_/  \\/___/    \\/_/  \\/_/\\/ /\\/_____/\\/_____/", 10, 7);

    }
}
