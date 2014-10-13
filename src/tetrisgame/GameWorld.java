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
    private static final int COL = 10;//x
    private static final int ROW = 20;//y
    
	/*Default starting position*/
    private static final int DEFAULT_X = 4;
    private static final int DEFAULT_Y = 0;
    
    /*ATTRIBUTES*/
    private int[][] moving_layer;//2d array layer for moving piece
    private int[][] static_layer;//2d array layer for collied pieces

    private PieceSet piece_set;//ArrayList of Pieces
	
    private Piece curr_piece;//Holds current piece
    private Piece prev_piece;//Holds previous piece
    
    Random rand = new Random();//For spawning new piece

    /*METHODS*/
    public GameWorld() {
	//Initialize
        moving_layer = new int[ROW][COL];
        static_layer = new int[ROW][COL];
        piece_set = new PieceSet();
        
        curr_piece = piece_set.get(rand.nextInt(piece_set.size()));
        prev_piece = piece_set.get(rand.nextInt(piece_set.size()));
    }
    
    public void spawnNewPiece(){
	//Revise to spawn in prev_piece
        curr_piece = piece_set.get(rand.nextInt(piece_set.size()));
        curr_piece.setPosition(DEFAULT_X, DEFAULT_Y);
        updateMovingLayer();
    }
    
    public void movePiece(int dir){
        //Revise for collision
	switch(dir){
		case DOWN:
			curr_piece.setY(curr_piece.getY()+1);
                        if(layersColllied()){//rollback move if collieded
                            curr_piece.setY(curr_piece.getY()-1);
                            //then move piece to static layer;
                        }
			break;
		case RIGHT:
			curr_piece.setX(curr_piece.getX()+1);
                        if(layersColllied())//rollback move if collieded
                            curr_piece.setX(curr_piece.getX()-1);
                        break;
		case LEFT:
			curr_piece.setX(curr_piece.getX()-1);
                        if(layersColllied())//rollback move if collieded
                            curr_piece.setX(curr_piece.getX()+1);
                        break;    
	}
        updateMovingLayer();
    }
    
    public void rotatePiece(){
	//Revise for collision
        curr_piece.rotate();
        if(layersColllied())//rollback move if collieded
            curr_piece.rotate();
        updateMovingLayer();        
    }
	
    private boolean layersColllied(){
        for(int y = 0; y < ROW; y++){
            for(int x = 0; x < COL; x++){
                if(moving_layer[y][x] != 0 && static_layer[y][x] != 0){
                    return true;
                }
            }
        }
	return false;
    }
    
    public void updateMovingLayer() {
        clearLayer(MOVING);
        
        for(int y = 0; y < curr_piece.getStructure()[0].length; y++){
            for(int x = 0; x < curr_piece.getStructure().length; x++){
                //Note: Revise for collision
                if(curr_piece.getY() + y >= 0 && curr_piece.getX() + x >= 0 && curr_piece.getY() + y < ROW && curr_piece.getX() + x < COL){
                    if(curr_piece.getStructure(x, y)){
                    moving_layer[curr_piece.getY() + y][curr_piece.getX() + x] = piece_set.indexOf(curr_piece) + 1;
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
}
