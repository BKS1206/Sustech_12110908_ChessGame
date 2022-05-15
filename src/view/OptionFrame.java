package view;

import javax.swing.*;
import java.awt.*;

public class OptionFrame extends JFrame {
    private int width;
    private int height;

    public void setBackground(String path){
        JPanel imPanel=(JPanel) this.getContentPane();//注意内容面板必须强转为JPanel才可以实现下面的设置透明
        imPanel.setOpaque(false);//将内容面板设为透明
        ImageIcon icon=new ImageIcon(path);
        JLabel label = new JLabel(icon);//往一个标签中加入图片
        label.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());//设置标签位置大小，记得大小要和窗口一样大
        this.getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE+1));//标签添加到层面板
    }

    public OptionFrame(int width, int height){

        JPanel imPanel=(JPanel) this.getContentPane();//注意内容面板必须强转为JPanel才可以实现下面的设置透明
        imPanel.setOpaque(false);//将内容面板设为透明
        ImageIcon icon=new ImageIcon("./images/Background1.jpg");
        JLabel label = new JLabel(icon);//往一个标签中加入图片
        label.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());//设置标签位置大小，记得大小要和窗口一样大
        this.getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE));//标签添加到层面板

        this.width=width;this.height=height;
        setSize(width,height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel option = new JLabel("Option",JLabel.CENTER);
        option.setLocation(width/5,height/15); option.setSize(width*3/5,height/5);
        option.setFont(new Font("Rockwell", Font.BOLD, 51));
        add(option);

        JButton ChangeImage = new JButton("Change background");
        ChangeImage.setLocation(width*3/10,height/3);ChangeImage.setSize(width*4/10,height/8);
        ChangeImage.setFont(new Font("Rockwell",Font.BOLD,35));
        add(ChangeImage);
        ChangeImage.addActionListener(e -> {
            String path = JOptionPane.showInputDialog(this,"Input Path here. Form: \"Background\"+\"number\"");
            setBackground("./images/"+path);

        });

        JButton ChangeMusic = new JButton("Change music");
        ChangeMusic.setLocation(width*3/10,height/2);ChangeMusic.setSize(width*4/10,height/8);
        ChangeMusic.setFont(new Font("Rockwell",Font.BOLD,35));
        add(ChangeMusic);

        JButton back = new JButton("Back to menu");
        back.setLocation(width*3/10,height*2/3);back.setSize(width*4/10,height/8);
        back.setFont(new Font("Rockwell",Font.BOLD,35));
        add(back);
        back.addActionListener(e -> {
            this.dispose();
        });
    }



}
