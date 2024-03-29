package controller;

import model.ChessComponent;
import view.Chessboard;

public class MovedController {
    private final Chessboard chessboard;
    private ChessComponent chessComponent;
    public MovedController(Chessboard chessboard){this.chessboard = chessboard;}
    public void moved(ChessComponent chessComponent, boolean in){
        chessComponent.setMoved(in);
        this.chessComponent = chessComponent;
        this.chessComponent.repaint();
    }
}
