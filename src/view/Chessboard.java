package view;


import controller.GameController;
import controller.MovedController;
import model.*;
import controller.ClickController;
import view.ChessGameFrame;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    private ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final MovedController movedController = new MovedController(this);
    private int CHESS_SIZE;


    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = getWidth() / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0,CHESSBOARD_SIZE-2, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE-1,1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE-1,CHESSBOARD_SIZE-2, ChessColor.WHITE);
        initBishopOnBoard(0,2,ChessColor.BLACK);
        initBishopOnBoard(0,CHESSBOARD_SIZE-3,ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE-1,2,ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE-1, CHESSBOARD_SIZE-3, ChessColor.WHITE);
        initQueenOnBoard(0,3,ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE-1,3,ChessColor.WHITE);
        initKingOnBoard(0,4,ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE-1,4,ChessColor.WHITE);
        for (int i = 0; i<CHESSBOARD_SIZE; i++){
            initPawnOnBoard(1, i, ChessColor.BLACK);
        }
        for (int i = 0; i<CHESSBOARD_SIZE; i++){
            initPawnOnBoard(CHESSBOARD_SIZE-2, i, ChessColor.WHITE);
        }
    }

    public void changeChessSize(){
        CHESS_SIZE = getWidth()/8;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessComponent chessComponent = chessComponents[i][j];
                chessComponent.setLocation(j * CHESS_SIZE, i * CHESS_SIZE);
                chessComponent.setSize(CHESS_SIZE, CHESS_SIZE);
            }
        }
    }

    public void Reset(){
        removeAll();
        initiateEmptyChessboard();
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0,CHESSBOARD_SIZE-2, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE-1,1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE-1,CHESSBOARD_SIZE-2, ChessColor.WHITE);
        initBishopOnBoard(0,2,ChessColor.BLACK);
        initBishopOnBoard(0,CHESSBOARD_SIZE-3,ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE-1,2,ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE-1, CHESSBOARD_SIZE-3, ChessColor.WHITE);
        initQueenOnBoard(0,3,ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE-1,3,ChessColor.WHITE);
        initKingOnBoard(0,4,ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE-1,4,ChessColor.WHITE);
        for (int i = 0; i<CHESSBOARD_SIZE; i++){
            initPawnOnBoard(1, i, ChessColor.BLACK);
        }
        for (int i = 0; i<CHESSBOARD_SIZE; i++){
            initPawnOnBoard(CHESSBOARD_SIZE-2, i, ChessColor.WHITE);
        }
        ChessGameFrame.currentPlayer.setText("White");
        currentColor = ChessColor.WHITE;
        repaint();
    }

    public ChessComponent getChess(int i, int j){
        return chessComponents[i][j];
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, movedController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, movedController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        if (currentColor == ChessColor.BLACK) {
            ChessGameFrame.currentPlayer.setText("Black");
        }else {
            ChessGameFrame.currentPlayer.setText("White");
        }
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, movedController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color){
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, movedController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color){
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, movedController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color){
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, movedController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color){
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, movedController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color){
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, movedController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        for (int i = 0; i<8; i++){
            for (int j = 0; j<8; j++){
                if (chessData.get(i).charAt(j) == 'R'){
                    initRookOnBoard(i,j,ChessColor.BLACK);
                }
                if (chessData.get(i).charAt(j) == 'r'){
                    initRookOnBoard(i,j,ChessColor.WHITE);
                }
                if (chessData.get(i).charAt(j) == 'N'){
                    initKnightOnBoard(i,j,ChessColor.BLACK);
                }
                if (chessData.get(i).charAt(j) == 'n'){
                    initKnightOnBoard(i,j,ChessColor.WHITE);
                }
                if (chessData.get(i).charAt(j) == 'B'){
                    initBishopOnBoard(i,j,ChessColor.BLACK);
                }
                if (chessData.get(i).charAt(j) == 'b'){
                    initBishopOnBoard(i,j,ChessColor.WHITE);
                }
                if (chessData.get(i).charAt(j) == 'K'){
                    initKingOnBoard(i,j,ChessColor.BLACK);
                }
                if (chessData.get(i).charAt(j) == 'k'){
                    initKingOnBoard(i,j,ChessColor.WHITE);
                }
                if (chessData.get(i).charAt(j) == 'Q'){
                    initQueenOnBoard(i,j,ChessColor.BLACK);
                }
                if (chessData.get(i).charAt(j) == 'q'){
                    initQueenOnBoard(i,j,ChessColor.WHITE);
                }
                if (chessData.get(i).charAt(j) == 'P'){
                    initPawnOnBoard(i,j,ChessColor.BLACK);
                }
                if (chessData.get(i).charAt(j) == 'p'){
                    initPawnOnBoard(i,j,ChessColor.WHITE);
                }
                if (chessData.get(i).charAt(j) == '_'){
                    putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, movedController, CHESS_SIZE));
                }
            }
        }
        if (chessData.get(8).charAt(0) == 'w'){
            this.currentColor = ChessColor.WHITE;
            ChessGameFrame.currentPlayer.setText("White");
        }else {
            this.currentColor = ChessColor.BLACK;
            ChessGameFrame.currentPlayer.setText("Black");
        }
        chessData.forEach(System.out::println);
    }
}
