/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tetrisgame;

import java.util.ArrayList;


/**
 *
 * @author rhett
 */
public final class PieceSet extends ArrayList<Piece> {

    public PieceSet() {
        /*DEFAULT PIECES*/
        
        //Line
        addPiece(new boolean[][]{
            {true,true,true,true}
        });
        
        //Square
        addPiece(new boolean[][]{
            {true,true},
            {true,true}
        });
        
        //Left L
        addPiece(new boolean[][]{
            {true, true, true},
            {false, false, true},
        });
        
        //Right L
        addPiece(new boolean[][]{
            {false, false, true},
            {true, true, true},
        });
        
        //Left Z
        addPiece(new boolean[][]{
            {false, true, true},
            {true, true, false},
        });
        
        //Left Z
        addPiece(new boolean[][]{
            {false, true, true},
            {true, true, false},
        });
        
        //T
        addPiece(new boolean[][]{
            {true, true, true},
            {false, true, false},
        });
        
    }

    public void addPiece(boolean[][] b){
        add(new Piece(b));
    }    
}
