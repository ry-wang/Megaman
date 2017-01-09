package main.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Test
    void testUpdateImageNum() {
        Player testPlayer = new Player(0, 0, 100, "Still", "Right");

        //Checking that the imageNum is correctly set to 1 when the max number is passed in
        testPlayer.setImageNum(3);
        testPlayer.updateImageNum(4);
        assertEquals(1, testPlayer.getImageNum());

        //Checking that jumping is false when maxImageNum is reached for Jump
        testPlayer.setImageNum(8);
        testPlayer.setState("Jump");
        testPlayer.updateImageNum(9);
        assertEquals(false, testPlayer.isJumping());
        assertEquals("Still", testPlayer.getState());

        //Checking that character state is still after jump or shoot animation finishes
        testPlayer.setImageNum(2);
        testPlayer.setState("Shoot");
        testPlayer.updateImageNum(3);
        assertEquals("Still", testPlayer.getState());
    }

}