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

public class QueenChessComponent extends ChessComponent{

    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;
    private Image queenImage;

    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {
            QUEEN_WHITE = ImageIO.read(new File("./images/queen-white.png"));
        }

        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = ImageIO.read(new File("./images/queen-black.png"));
        }
    }

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = QUEEN_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = QUEEN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, MovedController movedController, int size) {
        super(chessboardPoint, location, color, listener, movedController, size);
        initiateQueenImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getX() == destination.getX()) {
            int row = source.getX();
            for (int col = Math.min(source.getY(), destination.getY()) + 1;
                 col < Math.max(source.getY(), destination.getY()); col++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
            return true;
        } else if (source.getY() == destination.getY()) {
            int col = source.getY();
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
            return true;
        } else if(Math.abs(source.getX() - destination.getX()) == Math.abs(source.getY() - destination.getY())){
            if(source.getX() < destination.getX() && source.getY() < destination.getY()){
                for(int i = 1;source.getX() + i < destination.getX();i++){
                    if(chessComponents[source.getX() + i][source.getY() + i].chessColor != ChessColor.NONE){
                        return false;
                    }
                }
            }else if(source.getX() < destination.getX() && source.getY() > destination.getY()){
                for(int i = 1;source.getX() + i < destination.getX();i++){
                    if(chessComponents[source.getX() + i][source.getY() - i].chessColor != ChessColor.NONE){
                        return false;
                    }
                }
            }else if(source.getX() > destination.getX() && source.getY() > destination.getY()) {
                for (int i = 1; source.getX() - i > destination.getX(); i++) {
                    if (chessComponents[source.getX() - i][source.getY() - i].chessColor != ChessColor.NONE) {
                        return false;
                    }
                }
            }else if(source.getX() > destination.getX() && source.getY() < destination.getY()) {
                for (int i = 1; source.getX() - i > destination.getX(); i++) {
                    if (chessComponents[source.getX() - i][source.getY() + i].chessColor != ChessColor.NONE) {
                        return false;
                    }
                }
            }
            return true;
        }

            return false;
    }

    @Override
    public List<ChessboardPoint> getCanMovePoints(ChessComponent[][] chessComponent) {
        List<ChessboardPoint> r = new ArrayList<>();
        for(int i = 0;i < 8;i++){
            for(int j = 0;j < 8;j++){
                ChessboardPoint c = chessComponent[i][j].getChessboardPoint();
                if(canMoveTo(chessComponent,c) && getChessColor() != chessComponent[i][j].chessColor){
                    r.add(c);
                }
            }
        }
        return r;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        if (isMovedIn()){
            g.setColor(Color.cyan);
            g.fillRect(0,0,getWidth(),getHeight());
        }
        g.drawImage(queenImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }

    }

}
