package kz.yerke.tetris;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import kz.yerke.tetris.engine.Engine;
import kz.yerke.tetris.gui.InitialWindowGUI;
import kz.yerke.tetris.gui.MainGameWindow;

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
            initialWindowGUI.drawInitWindow();


            MainGameWindow mainGameWindow = new MainGameWindow(terminal);
            //mainGameWindow.buildGameWindow();
            Engine engine = new Engine(terminal);
            Thread.sleep(1000);
            //engine.start();


        } catch(IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if(screen != null) {
                try {
                    /*
                    The close() call here will restore the terminal by exiting from private mode which was done in
                    the call to startScreen()
                     */
                    screen.close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
