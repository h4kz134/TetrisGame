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
public class TetrisGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GameWorld world= new GameWorld();
        
        world.spawnNewPiece();
        world.printLayer(GameWorld.MOVING);
        System.out.println();
        world.rotatePiece();
        world.printLayer(GameWorld.MOVING);
    }
    
}
