package view;

import controller.GameController;
import model.ChessColor;
import model.ChessComponent;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGHT;
    public static final int CHESSBOARD_SIZE = 608;
    public static GameController gameController;
    public static JLabel currentPlayer = new JLabel("White",JLabel.CENTER);
    public static Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE,CHESSBOARD_SIZE);


    public ChessGameFrame(int width, int height) {

        JPanel imPanel = (JPanel) this.getContentPane();//注意内容面板必须强转为JPanel才可以实现下面的设置透明
        imPanel.setOpaque(false);//将内容面板设为透明
        ImageIcon icon = new ImageIcon("./images/Background1.jpg");
        JLabel label = new JLabel(icon);//往一个标签中加入图片
        label.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());//设置标签位置大小，记得大小要和窗口一样大
        this.getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE));//标签添加到层面板

        setTitle("Chess Game"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);

        JLabel statusLabel = new JLabel("Current player:", JLabel.CENTER);
        statusLabel.setLocation(width * 3 / 4, HEIGHT / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);

        currentPlayer.setLocation(width * 3 / 4, HEIGHT / 7);
        currentPlayer.setSize(200, 60);
        currentPlayer.setFont(new Font("Rockwell", Font.BOLD, 25));
        add(currentPlayer);

        JButton ResetButton = new JButton("Reset");
        ResetButton.setLocation(width * 3 / 4, HEIGHT / 10 + 120);
        ResetButton.setSize(200, 60);
        ResetButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(ResetButton);
        ResetButton.addActionListener(e -> {
            int index = JOptionPane.showConfirmDialog(null, "确认重新开始？", null, JOptionPane.YES_NO_OPTION);
            if (index == JOptionPane.YES_OPTION) {
                this.dispose();
                ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
                mainFrame.setVisible(true);
            }
        });

        JButton SaveButton = new JButton("Save");
        SaveButton.setLocation(width * 3 / 4, HEIGHT / 10 + 240);
        SaveButton.setSize(200, 60);
        SaveButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(SaveButton);

        JButton BackToMainMenu = new JButton("Back to menu");
        BackToMainMenu.setLocation(width * 3 / 4, HEIGHT / 10 + 360);
        BackToMainMenu.setSize(200, 60);
        BackToMainMenu.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(BackToMainMenu);
        BackToMainMenu.addActionListener(e -> {
            int index = JOptionPane.showConfirmDialog(null, "确认返回到主菜单？", null, JOptionPane.YES_NO_OPTION);
            if (index == JOptionPane.YES_OPTION) {
                this.dispose();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                chessboard.setLocation(width / 10, height / 10);
                chessboard.setSize(width * 4 / 5, height * 4 / 5);
                statusLabel.setLocation(width * 3 / 4, height / 10);
                statusLabel.setSize(width / 5, height / 12);
                ResetButton.setLocation(width * 3 / 4, height / 10 + 120);
                ResetButton.setSize(width / 5, height / 12);
                SaveButton.setLocation(width * 3 / 4, height / 10 + 240);
                SaveButton.setSize(width / 5, height / 12);
                BackToMainMenu.setLocation(width * 3 / 4, height / 10 + 360);
                BackToMainMenu.setSize(width / 5, height / 12);
                currentPlayer.setLocation(width * 3 / 4, height / 7);
                currentPlayer.setSize(width / 5, height / 12);
            }
        });
    }






    /**
     * 在游戏面板中添加棋盘
     */


    /**
     * 在游戏面板中添加标签
     */


    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */




}
