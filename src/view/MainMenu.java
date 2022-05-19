package view;

import controller.GameController;


import javax.sound.sampled.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import view.ChessGameFrame;


public class MainMenu extends JFrame{
    private final int WIDTH;
    private final int HEIGHT;
    private GameController gameController;
    byte[] data;
    AudioFormat format;
    int length;

    public void playMusic(){/*代码参考：https://blog.csdn.net/m0_37672234/article/details/73605410*/
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    clip.open(format,data,0,length);
                    clip.start();
                } catch (LineUnavailableException e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

    public void setBackground(String path){
        JPanel imPanel=(JPanel) this.getContentPane();//注意内容面板必须强转为JPanel才可以实现下面的设置透明
        imPanel.setOpaque(false);//将内容面板设为透明
        ImageIcon icon=new ImageIcon(path);
        JLabel label = new JLabel(icon);//往一个标签中加入图片
        label.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());//设置标签位置大小，记得大小要和窗口一样大
        this.getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE+1));//标签添加到层面板
    }

    public MainMenu(int width, int height) throws Exception {

        AudioInputStream in = AudioSystem.getAudioInputStream(new File("./images/Tsuki.wav"));
        format = in.getFormat();
        length = (int)in.getFrameLength();
        data = new byte[length];
        in.read(data);
        in.close();
        playMusic();

        /*背景图片设置参考：https://blog.csdn.net/weixin_52719777/article/details/112567104*/
        JPanel imPanel=(JPanel) this.getContentPane();//注意内容面板必须强转为JPanel才可以实现下面的设置透明
        imPanel.setOpaque(false);//将内容面板设为透明
        ImageIcon icon=new ImageIcon("./images/Background1.jpg");
        JLabel label = new JLabel(icon);//往一个标签中加入图片
        label.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());//设置标签位置大小，记得大小要和窗口一样大
        this.getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE));//标签添加到层面板

        setTitle("Chess Game");
        WIDTH = width;HEIGHT=height;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        JLabel Welcome = new JLabel("Welcome to chess game!",JLabel.CENTER);
        Welcome.setLocation(width/5,height/15); Welcome.setSize(width*3/5,height/5);
        Welcome.setFont(new Font("Rockwell", Font.BOLD, 51));
        add(Welcome);

        JButton Start = new JButton("New Game");
        Start.setLocation(width*3/10,height/4);Start.setSize(width*4/10,height/8);
        Start.setFont(new Font("Rockwell",Font.BOLD,35));
        add(Start);
        Start.addActionListener(e -> {
            ChessGameFrame mainFrame1 = new ChessGameFrame(1000, 760);
            mainFrame1.setVisible(true);
        });

        JButton Load = new JButton("Load");
        Load.setLocation(width*3/10,height*2/5);Load.setSize(width*4/10,height/8);
        Load.setFont(new Font("Rockwell",Font.BOLD,35));
        add(Load);
        Load.addActionListener(e -> {
            System.out.println("Click load");
            boolean legal = true;
            String path = JOptionPane.showInputDialog(this,"Input Path here");
            String FileType = path.substring(path.lastIndexOf('.')+1);
            if ((!FileType.equals("txt")) && (!FileType.equals("TXT"))){
                JOptionPane.showMessageDialog(this, "文件格式错误！");
                legal = false;
            }
            try {
                List<String> chessData = Files.readAllLines(Path.of(path));
                if (chessData.size() != 9){
                    if (!chessData.get(chessData.size()-1).equals("w") && !chessData.get(chessData.size()-1).equals("b")){
                        JOptionPane.showMessageDialog(this, "缺少行棋方！");
                    }else {
                        JOptionPane.showMessageDialog(this,"棋盘并非8*8！");
                    }
                    legal = false;
                }else {
                    if (!chessData.get(chessData.size() - 1).equals("w") && !chessData.get(chessData.size() - 1).equals("b")) {
                        JOptionPane.showMessageDialog(this, "缺少行棋方！");
                        legal = false;
                    } else {
                        for (int i=0; i<8; i++) {
                            if (chessData.get(i).length() != 8){
                                JOptionPane.showMessageDialog(this, "棋盘并非8*8！");
                                legal = false;
                                break;
                            }
                        }
                    }
                }
                if (legal) {
                    for (int i=0; i<8; i++) {
                        for (int j = 0; j < chessData.get(i).length(); j++) {
                            String s = String.valueOf(chessData.get(i).charAt(j));
                            if ((!s.equals("R")) && (!s.equals("N")) && (!s.equals("B")) && (!s.equals("Q")) && (!s.equals("K")) && (!s.equals("P")) && (!s.equals("r")) && (!s.equals("n")) && (!s.equals("b")) && (!s.equals("q")) && (!s.equals("k")) && (!s.equals("p")) && (!s.equals("_"))) {
                                JOptionPane.showMessageDialog(this, "存在非法棋子！");
                                legal = false;
                                break;
                            }
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                legal = false;
                JOptionPane.showMessageDialog(this,"未找到文件！");
            }

            if (legal) {
                ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
                mainFrame.setVisible(true);
                ChessGameFrame.gameController.loadGameFromFile(path);
            }
        });


        JButton ChangeImage = new JButton("Change background");
        ChangeImage.setLocation(width*3/10,height*53/96);ChangeImage.setSize(width*4/10,height/8);
        ChangeImage.setFont(new Font("Rockwell",Font.BOLD,35));
        add(ChangeImage);
        ChangeImage.addActionListener(e -> {
            String path = JOptionPane.showInputDialog(this,"Input Path here. Form: \"Background\"+\"number\"");
            this.setBackground("./images/"+path+".jpg");
        });


        JButton Exit = new JButton("Exit");
        Exit.setLocation(width*3/10,height*68/96);Exit.setSize(width*4/10,height/8);
        Exit.setFont(new Font("Rockwell",Font.BOLD,35));
        add(Exit);
        Exit.addActionListener(e -> {
            System.exit(0);
        });


        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width=getWidth();
                int height=getHeight();
                Welcome.setLocation(width/5,height/15); Welcome.setSize(width*3/5,height/5);
                Start.setLocation(width*3/10,height/4);Start.setSize(width*4/10,height/8);
                Load.setLocation(width*3/10,height*2/5);Load.setSize(width*4/10,height/8);
                ChangeImage.setLocation(width*3/10,height*53/96);ChangeImage.setSize(width*4/10,height/8);
                Exit.setLocation(width*3/10,height*68/96);Exit.setSize(width*4/10,height/8);
            }
        });
    }
}
