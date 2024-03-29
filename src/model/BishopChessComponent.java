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

public class BishopChessComponent extends ChessComponent{

    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;
    private Image bishopImage;

    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./images/bishop-white.png"));
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/bishop-black.png"));
        }
    }

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, MovedController movedController, int size) {
        super(chessboardPoint, location, color, listener, movedController, size);
        initiateBishopImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if(Math.abs(source.getX() - destination.getX()) == Math.abs(source.getY() - destination.getY())){
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
    public List<ChessboardPoint> getCanMovePoints(ChessComponent[][] chessComponent){
        List<ChessboardPoint> r = new ArrayList<>();
        for(int i = 0;i < 8;i++){
            for (int j = 0;j < 8;j++){
                    ChessboardPoint to = chessComponent[i][j].getChessboardPoint();
                    if(canMoveTo(chessComponent,to) && chessComponent[i][j].chessColor != getChessColor()){
                        r.add(chessComponent[i][j].getChessboardPoint());
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
        g.drawImage(bishopImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }

}
