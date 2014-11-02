/*
 * JUnit testing for Board class
 */

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Olly
 */

public class BoardTest {
    
    @Test
    public void isShipPlacedTest () {
            Assert.assertTrue("Is the ship placed correctly", isShipCorrect () );
    }
    
    @Test
    public void isShipPickedUpTest () {
            Assert.assertTrue("Has the ship been picked up", isShipRemoved());
    }
    
    @Test
    public void isGameOverTest () {
            Assert.assertTrue("Is the game over", isGameOver());
    }
    
    @Test
    public void isShipPlacementEqualsTest () {
            Assert.assertTrue("Is ship placement equal", isShipPlacementEqualsCorrect());
    }
    
    
    
    //checks for correct placement of ship
    public boolean isShipCorrect () {
        
            model.Board board = new model.Board(true);
            model.Ship ship = board.getShips().get(4); //gets a submarine
            ship.setVertical(true);
            board.placeShip(ship, 3, 2);
            
            //checks for placement on squares
            for ( int i = 0; i < board.BOARD_DIMENSION; i++ ) {
                    for ( int j = 0; j < board.BOARD_DIMENSION; j++ ) {
                            //checks each square for correct ship status
                            if ( (i == 3 && (j >= 2 && j <= 4) && !board.getSquare(i, j).isShip() ) ||
                                ( !(i == 3 && (j >= 2 && j <= 4) ) && board.getSquare(i, j).isShip() ) ) {
                                        return false;
                            }
                    }
            }
            
            return true;
    }
    
    public boolean isShipRemoved () {
            
            //creates and places ship on the board
            model.Board board = new model.Board(true);
            model.Ship ship = board.getShips().get(0);
            board.placeShip(ship, 5, 5);
            
            //removes ship from board
            board.pickUpShip(ship);
            
            //checks if board is clear
            for ( int i = 0; i < board.BOARD_DIMENSION; i++ ) {
                    for ( int j = 0; j < board.BOARD_DIMENSION; j++ ) {
                            if ( board.getSquare(i, j).isShip() ) {
                                    return false;
                            }
                    }
            }
            
            return true;
    }
    
    public boolean isGameOver () {
        
            model.Board board = new model.Board(true);
            
            //game should not be over yet
            if ( board.gameOver() ) {
                    return false;
            }
            
            //sinks all ships
            for ( model.Ship ship : board.getShips() ) {
                    ship.sink();
            }
            
            //should return true as all ships have been sunk
            return board.gameOver();
    }
    
    //checks whether shipPlacementEquals correctly tests board equality
    public boolean isShipPlacementEqualsCorrect () {
            model.Board board1 = new model.Board(true);
            model.Board board2 = new model.Board(true);
            
            //places ships on board1
            ArrayList<model.Ship> board1Ships = board1.getShips();
            board1.placeShip(board1Ships.get(0), 1, 1); //AIRCRAFT_CARRIER
            board1.placeShip(board1Ships.get(1), 0, 3); //BATTLESHIP
            board1.placeShip(board1Ships.get(2), 2, 5); //DESTROYER
            board1Ships.get(3).setVertical(true);
            board1.placeShip(board1Ships.get(3), 1, 7); //PATROL_BOAT
            board1Ships.get(4).setVertical(true);
            board1.placeShip(board1Ships.get(4), 8, 3); //SUBMARINE
            
            //places ships on board2 in same locations
            ArrayList<model.Ship> board2Ships = board2.getShips();
            board2.placeShip(board2Ships.get(0), 1, 1); //AIRCRAFT_CARRIER
            board2.placeShip(board2Ships.get(1), 0, 3); //BATTLESHIP
            board2.placeShip(board2Ships.get(2), 2, 5); //DESTROYER
            board2Ships.get(3).setVertical(true);
            board2.placeShip(board2Ships.get(3), 1, 7); //PATROL_BOAT
            board2Ships.get(4).setVertical(true);
            board2.placeShip(board2Ships.get(4), 8, 3); //SUBMARINE
            
            return board1.shipPlacementEquals(board2);
    }
}
