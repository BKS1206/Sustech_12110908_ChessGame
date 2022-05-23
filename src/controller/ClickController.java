package controller;


import model.ChessColor;
import model.ChessComponent;
import model.KingChessComponent;
import view.ChessGameFrame;
import view.Chessboard;


import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.desktop.AppForegroundListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;
    public ChessComponent temp;

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }



    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                first.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                try {
                    URL url = new URL("file:./images/effect.wav");
                    AudioClip ac = Applet.newAudioClip(url);
                    ac.play();
                }catch (Exception e){
                    System.out.println(e);
                }

                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                first.setSelected(false);
                if (chessComponent instanceof KingChessComponent && chessComponent.getChessColor()== ChessColor.BLACK){
                    JOptionPane.showMessageDialog(chessboard, "White win!");
                }
                if (chessComponent instanceof KingChessComponent && chessComponent.getChessColor()== ChessColor.WHITE){
                    JOptionPane.showMessageDialog(chessboard, "Black win!");
                }
                chessboard.check(chessboard.getChessComponents(),first);
//                chessboard.checkMate(chessboard.getChessComponents(), first);
                first = null;

            }
        }
    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        if(chessComponent.getChessColor() != chessboard.getCurrentColor()
                && first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint())){
            temp = first;
            return true;
        }
        return false;
    }
}
