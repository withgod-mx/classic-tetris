package kz.yerke.tetris.gui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class WindowDraw {

    private Terminal terminal;

    private TerminalPosition startPosition;


    public WindowDraw(Terminal terminal, TerminalPosition startPosition) {
        this.terminal = terminal;
        this.startPosition = startPosition;
    }

    public void draw(String text, int columnPosition, int rowPosition) throws IOException {

        terminal.setCursorPosition(startPosition.withRelativeColumn(columnPosition).withRelativeRow(rowPosition));
        terminal.putString(text);
        terminal.flush();
    }
}
