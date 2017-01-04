package main.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class HealthPackTest {

    @Test
    void testSpawnLocation() {
        HealthPack testHealthPack = new HealthPack(1);
        assertEquals(20, testHealthPack.getX());
        assertEquals(162, testHealthPack.getY());

        testHealthPack = new HealthPack(2);
        assertEquals(370, testHealthPack.getX());
        assertEquals(323, testHealthPack.getY());

        testHealthPack = new HealthPack(3);
        assertEquals(20, testHealthPack.getX());
        assertEquals(162, testHealthPack.getY());

    }

}