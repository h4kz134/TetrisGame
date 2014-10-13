/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tetrisgame;

/**
 *
 * @author rhett
 */
public class Piece {
    /*Attributes*/    
    private boolean[][] structure;
    
    private int X = 0;
    private int Y = 0;

    public Piece(boolean[][] structure) {
       this.structure = structure; 
    }
    
    public void rotate(){
        //Rotate matrix by tranposing and fliping
        boolean[][] temp = new boolean[structure[0].length][structure.length];
        
        //Transpose
        for(int y = 0; y < structure.length; y++){
            for(int x = 0; x < structure[y].length; x++){
                temp[x][y] = structure[y][x];
            }
        }
        
        //Calculate Offset
        setX(getX() + (int)((temp[0].length - structure[0].length)/2.0));
        setY(getY() + (int)((temp.length - structure.length)/2.0));
        
        structure = new boolean[temp.length][temp[0].length];
        
        //Flip matrix
        for(int y = 0; y < temp.length; y++){
            for(int x = 0; x < temp[y].length; x++){
                structure[y][x] = temp[y][temp[y].length - x - 1];
            }
        }
    }
    
    public boolean[][] getStructure() {
        return structure;
    }
    
    public boolean getStructure(int first_index, int second_index) {
        return structure[first_index][second_index];
    }
    
    public void print(){
        for(int y = 0; y < structure[0].length; y++){
            for(int x = 0; x < structure.length; x++){
                System.out.print(structure[x][y]);
            }
            System.out.println();
        }
    }
    
    public void setPosition(int x, int y){
        this.setX(x);
        this.setY(y);
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
    
    public void setX(int X) {
        this.X = X;
    }

    public void setY(int Y) {
        this.Y = Y;
    }
}
