package tetrisgame;

import java.util.Random;

/**
 *
 * @author rhett
 */
public class GameWorld {
    /*Constants*/
    public static final int MOVING = 1;
    public static final int STATIC = 2;    
    
    private static final int COL = 10;//x
    private static final int ROW = 20;//y
    
    private static final int DEFAULT_X = 4;//x
    private static final int DEFAULT_Y = 0;//y
    
    /*Atrributes*/
    private int[][] moving_layer;
    private int[][] static_layer;

    private PieceSet piece_set;
    private Piece current_piece;
    
    Random rand = new Random();

    /*Methods*/
    public GameWorld() {
        moving_layer = new int[ROW][COL];
        static_layer = new int[ROW][COL];
        piece_set = new PieceSet();
    }
    
    public void spawnNewPiece(){
        current_piece = piece_set.get(0);
        current_piece.setPosition(DEFAULT_X, DEFAULT_Y);
        updateMovingLayer();
    }
    
    public void movePiece(){
        //Revise for left right movements
        current_piece.setPosition(current_piece.getX(), current_piece.getY()+1);
        updateMovingLayer();
    }
    
    public void rotatePiece(){
        current_piece.rotate();
        updateMovingLayer();        
    }
    
    public void updateMovingLayer() {
        clearLayer(MOVING);
        
        for(int y = 0; y < current_piece.getStructure()[0].length; y++){
            for(int x = 0; x < current_piece.getStructure().length; x++){
                //Note: Revise for collision
                if(current_piece.getY() + y >= 0 && current_piece.getX() + x >= 0 && current_piece.getY() + y < ROW && current_piece.getX() + x < COL){
                    if(current_piece.getStructure(x, y)){
                    moving_layer[current_piece.getY() + y][current_piece.getX() + x] = piece_set.indexOf(current_piece) + 1;
                    }
                }
            }
        }
    }
    
    public int[][] getMoving_layer() {
        return moving_layer;
    }

    public int[][] getStatic_layer() {
        return static_layer;
    }
    
    
    public void clearLayer(int l){//MOVING or STATIC
        int[][] layer;
        switch(l){
            case MOVING:
                layer = getMoving_layer();
                break;
            case STATIC:
                layer = getStatic_layer();
                break;
            default:
                layer = getMoving_layer();
        }
        for(int y = 0; y < ROW; y++){
            for(int x = 0; x < COL; x++){
                layer[y][x] = 0;
            }
        }
    }
    
    public void printLayer(int l){//MOVING or STATIC
        int[][] layer;
        switch(l){
            case MOVING:
                layer = getMoving_layer();
                break;
            case STATIC:
                layer = getStatic_layer();
                break;
            default:
                layer = getMoving_layer();
        }
        for(int y = 0; y < ROW; y++){
            for(int x = 0; x < COL; x++){
                System.out.print(layer[y][x]);
            }
            System.out.println();
        }
    }
}
