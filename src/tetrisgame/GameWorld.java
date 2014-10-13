package tetrisgame;

import java.util.Random;

/**
 *
 * @author rhett
 */
public class GameWorld {
    /*CONSTANTS*/

    /*For printing and clearing*/
    public static final int MOVING = 1;
    public static final int STATIC = 2;    
	
    /*For moving pieces*/
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    public static final int UP = 4;
    
    /*World size*/
    public static final int COL = 10;//x
    public static final int ROW = 20;//y
    
    /*Default starting position*/
    private static final int DEFAULT_X = 0;
    private static final int DEFAULT_Y = 0;
    
    /*ATTRIBUTES*/
    private int[][] moving_layer;//2d array layer for moving piece
    private int[][] static_layer;//2d array layer for collied pieces

    private PieceSet piece_set;//ArrayList of Pieces
	
    private Piece curr_piece;//Holds current piece
    private Piece prev_piece;//Holds previous piece
    
    //QUEUE 3 stored pieces
    
    Random rand = new Random();//For spawning new piece

    /*METHODS*/
    public GameWorld() {
	//Initialize
        moving_layer = new int[ROW][COL];
        static_layer = new int[ROW][COL];
        piece_set = new PieceSet();
        
        curr_piece = piece_set.get(rand.nextInt(piece_set.size()));
        prev_piece = piece_set.get(rand.nextInt(piece_set.size()));
        
        getCurr_piece().setPosition(DEFAULT_X, DEFAULT_Y);
        updateMovingLayer();
    }
    
    private void spawnNewPiece(){
	//Revise to spawn in prev_piece
        curr_piece = getPrev_piece();
        prev_piece = piece_set.get(rand.nextInt(piece_set.size()));
        
        getCurr_piece().setPosition(DEFAULT_X, DEFAULT_Y);
        updateMovingLayer();
    }
    
    public void movePiece(int dir){
	switch(dir){
		case DOWN:
                        getCurr_piece().setY(getCurr_piece().getY()+1);
                        updateMovingLayer();
                        if(collision()){//rollback move if collied
                            getCurr_piece().setY(getCurr_piece().getY()-1);
                            updateMovingLayer();
                            moveToStatic();
                            spawnNewPiece();
                        }
			break;
		case RIGHT:
			getCurr_piece().setX(getCurr_piece().getX()+1);
                        updateMovingLayer();
                        if(collision()){//rollback move if collied
                            getCurr_piece().setX(getCurr_piece().getX()-1);
                            updateMovingLayer();
                        }
                        break;
		case LEFT:
			getCurr_piece().setX(getCurr_piece().getX()-1);
                        updateMovingLayer();
                        if(collision()){//rollback move if collied
                            getCurr_piece().setX(getCurr_piece().getX()+1);
                            updateMovingLayer();
                        }
                        break;    
	}
    }
    
    public void rotatePiece(){
        getCurr_piece().rotate();
        if(collision())//adjust move if collieded
            getCurr_piece().rotate();
        updateMovingLayer();        
    }
	
    private boolean collision(){
        if(curr_piece.getX() >= 0 && curr_piece.getX()+curr_piece.getStructure().length <= COL && curr_piece.getY()+curr_piece.getStructure()[0].length <= ROW){
            for(int y = 0; y < ROW; y++){
                for(int x = 0; x < COL; x++){
                    if(moving_layer[y][x] != 0 && static_layer[y][x] != 0){
                        return true;
                    }
                }
            }
            return false;
        }
        else{
           return true;
        }
    }
    
    private void moveToStatic(){
        for(int y = 0; y < ROW; y++){
            for(int x = 0; x < COL; x++){
                if(moving_layer[y][x] != 0 && static_layer[y][x] == 0){
                    static_layer[y][x] = moving_layer[y][x];
                    moving_layer[y][x] = 0;
                }
            }
        }
    }
    
    public int checkCompletedLines(){//Returns number of completed lines
        int lines_completed = 0;
        
        for(int y = 0; y < ROW; y++){
            boolean complete = true;
            int x = 0;
            while(x < ROW){
                if(static_layer[y][x] == 0)
                    complete = false;
                x++;
            }
            
            if(complete){
                lines_completed++;
                
            for(int Y = y-1; Y >= 0; Y--){
                for(int X = 0; X < COL; X++){
                    static_layer[y+1][x] = static_layer[y][x];
                }
            }
            }
        }
        
        return lines_completed;
    }
    
    public void updateMovingLayer() {
        clearLayer(MOVING);
        for(int y = 0; y < getCurr_piece().getStructure()[0].length; y++){
            for(int x = 0; x < getCurr_piece().getStructure().length; x++){
                if(getCurr_piece().getY() + y >= 0 && getCurr_piece().getX() + x >= 0 && getCurr_piece().getY() + y < ROW && getCurr_piece().getX() + x < COL){
                    if(getCurr_piece().getStructure(x, y)){
                    moving_layer[getCurr_piece().getY() + y][getCurr_piece().getX() + x] = piece_set.indexOf(getCurr_piece()) + 1;
                    }
                }
            }
        }
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
	
    public int[][] getMoving_layer() {
        return moving_layer;
    }

    public int[][] getStatic_layer() {
        return static_layer;
    }

    public Piece getCurr_piece() {
        return curr_piece;
    }

    public Piece getPrev_piece() {
        return prev_piece;
    }
}
