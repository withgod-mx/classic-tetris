package kz.yerke.tetris.gui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class LanguageSelectWindow {

    Terminal terminal;
    TerminalPosition startPosition = new TerminalPosition(0, 0);
    WindowDraw windowDraw;

    public LanguageSelectWindow(Terminal terminal) {
        this.terminal = terminal;
        this.windowDraw = new WindowDraw(terminal, startPosition);
    }

    public void drawLanguageWindow() throws IOException {
        terminal.setForegroundColor(TextColor.ANSI.RED);
        windowDraw.draw("Выберите язык / ", 18, 7);
        terminal.setForegroundColor(TextColor.ANSI.MAGENTA);
        windowDraw.draw("Select language /", 35, 7);
        terminal.setForegroundColor(TextColor.ANSI.WHITE);
        windowDraw.draw("Тілді таңдаңыз", 53, 7);

        terminal.setForegroundColor(TextColor.ANSI.YELLOW);
        windowDraw.draw("→", 27, 10);
        terminal.setForegroundColor(TextColor.ANSI.RED);
        windowDraw.draw("English", 30, 10);
        terminal.setForegroundColor(TextColor.ANSI.MAGENTA);
        windowDraw.draw("Русскии", 30, 12);
        terminal.setForegroundColor(TextColor.ANSI.WHITE);
        windowDraw.draw("Қазақ тілі", 30, 14);

    }

}
