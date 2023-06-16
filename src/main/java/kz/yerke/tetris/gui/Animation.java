package kz.yerke.tetris.gui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Animation {

    Terminal terminal;

    TerminalPosition startPosition = new TerminalPosition(0, 0);

    WindowDraw windowDraw;
    public Animation(Terminal terminal) {
        this.terminal = terminal;
        this.windowDraw = new WindowDraw(terminal, startPosition);
    }

    public void defaultMove() throws IOException, InterruptedException {
        int index = 0;
        while (true) {
            windowDraw.draw(" .", 37, index - 1);
            windowDraw.draw(" . . .", 35, index);

            windowDraw.draw("[]", 37, index);
            windowDraw.draw("[][][]", 35, index + 1);
            Thread.sleep(1000);

            index++;

            if(index == 20) break;
        }
    }

}
