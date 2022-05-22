package model;

import controller.ClickController;
import controller.MovedController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KingChessComponent extends ChessComponent{
    private static Image KING_WHITE;
    private static Image KING_BLACK;
    private Image kingImage;

    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE = ImageIO.read(new File("./images/king-white.png"));
        }

        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("./images/king-black.png"));
        }
    }

    private void initiateKingImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage = KING_WHITE;
            } else if (color == ChessColor.BLACK) {
                kingImage = KING_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, MovedController movedController, int size) {
        super(chessboardPoint, location, color, listener, movedController, size);
        initiateKingImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        return Math.abs(source.getX() - destination.getX()) <= 1 && Math.abs(source.getY() - destination.getY()) <= 1;
    }
    @Override
    public List<ChessboardPoint> getCanMovePoints(ChessComponent[][] chessComponents) {
        List<ChessboardPoint> r = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessboardPoint c = chessComponents[i][j].getChessboardPoint();
                if(canMoveTo(chessComponents,c) && getChessColor() != chessComponents[i][j].chessColor){
                    r.add(chessComponents[i][j].getChessboardPoint());
                }
            }
        }
        return r;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        if (isMovedIn()){
            g.setColor(Color.cyan);
            g.fillRect(0,0,getWidth(),getHeight());
        }
        g.drawImage(kingImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }

    }

}
