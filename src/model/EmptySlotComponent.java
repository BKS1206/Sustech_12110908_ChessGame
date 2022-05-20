package model;

import controller.MovedController;
import view.ChessboardPoint;
import controller.ClickController;

import java.awt.*;
import java.io.IOException;

/**
 * 这个类表示棋盘上的空位置
 */
public class EmptySlotComponent extends ChessComponent {

    public EmptySlotComponent(ChessboardPoint chessboardPoint, Point location, ClickController listener, MovedController movedController, int size) {
        super(chessboardPoint, location, ChessColor.NONE, listener, movedController, size);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        return false;
    }

    @Override
    public void loadResource() throws IOException {
        //No resource!
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isMovedIn()){
            g.setColor(Color.cyan);
            g.fillRect(0,0,getWidth(),getHeight());
        }
    }

}
