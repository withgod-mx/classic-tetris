package kz.yerke.tetris;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import kz.yerke.tetris.engine.Engine;
import kz.yerke.tetris.engine.InitEngine;
import kz.yerke.tetris.gui.InitialWindowGUI;
import kz.yerke.tetris.gui.MainGameWindow;
import kz.yerke.tetris.model.MainSettings;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Screen screen = null;
        try {
            Terminal terminal = defaultTerminalFactory.createTerminal();
            terminal.setCursorVisible(false);
            screen = new TerminalScreen(terminal);
            InitialWindowGUI initialWindowGUI = new InitialWindowGUI(terminal);
            MainGameWindow mainGameWindow = new MainGameWindow(terminal);
            MainSettings mainSettings = new MainSettings();
            InitEngine initEngine = new InitEngine(mainSettings, terminal);
            Engine engine = new Engine(terminal);
            boolean quite = false;

            while (!quite) {

                switch (mainSettings.getEventType()) {
                    case INIT -> {
                        initialWindowGUI.drawInitWindow();
                        initEngine.start();

                    }
                    case LANGUAGE -> {


                    }
                    case START_GAME -> {
                        terminal.clearScreen();
                        mainGameWindow.buildGameWindow();
                        Thread.sleep(1000);
                        engine.start();

                    }
                    case EXIT -> {
                        quite = true;
                    }
                }

            }

            terminal.close();

        } catch(IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if(screen != null) {
                try {
                    screen.close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
