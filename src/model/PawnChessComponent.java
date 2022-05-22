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

public class PawnChessComponent extends ChessComponent{
    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;
    private Image pawnImage;

    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
        }
    }

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, MovedController movedController,  int size) {
        super(chessboardPoint, location, color, listener, movedController, size);
        initiatePawnImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if(getChessColor() == ChessColor.BLACK){
                if(source.getX() == 1){
                    if(source.getY() == destination.getY() && destination.getX() - source.getX() == 1
                            && chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.NONE){
                        return true;
                    }else if(source.getY() == destination.getY() && destination.getX() - source.getX() == 2
                            && chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.NONE
                            && chessComponents[destination.getX() - 1][destination.getY()].chessColor == ChessColor.NONE){
                        return true;
                    }
                }else  if(source.getY() == destination.getY() && destination.getX() - source.getX() == 1
                        && chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.NONE){
                        return true;
                }
                if(source.getX() + 1 == destination.getX() && source.getY() + 1 == destination.getY()
                        && chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.WHITE){
                    return true;
                }
            return source.getX() + 1 == destination.getX() && source.getY() - 1 == destination.getY()
                    && chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.WHITE;
            }else {
            if(source.getX() == 6){
                if(source.getY() == destination.getY() && destination.getX() - source.getX() == -1
                        && chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.NONE){
                    return true;
                }else if(source.getY() == destination.getY() && destination.getX() - source.getX() == -2
                        && chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.NONE
                        && chessComponents[destination.getX() + 1][destination.getY()].chessColor == ChessColor.NONE){
                    return true;
                }
            }else  if(source.getY() == destination.getY() && destination.getX() - source.getX() == -1
                    && chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.NONE){
                return true;
            }
            if(source.getX() - 1 == destination.getX() && source.getY() - 1 == destination.getY()
                    && chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.BLACK){
                return true;
            }
            return source.getX() - 1 == destination.getX() && source.getY() + 1 == destination.getY()
                    && chessComponents[destination.getX()][destination.getY()].chessColor == ChessColor.BLACK;
        }
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
        g.drawImage(pawnImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }

    }

}
