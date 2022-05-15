import view.ChessGameFrame;
import view.MainMenu;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu mainMenu = null;
            try {
                mainMenu = new MainMenu(1000,760);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
//            mainFrame.setVisible(true);
            assert mainMenu != null;
            mainMenu.setVisible(true);
        });
    }
}
