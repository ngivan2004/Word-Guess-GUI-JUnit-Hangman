package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStatTest {
    private PlayerStat testStat;

    @BeforeEach
    void runBefore() {
        testStat = new PlayerStat();
    }

    @Test
    void testCalculateHP() {
        testStat.calculateHP(5, 1);
        assertEquals(4, testStat.getHP());
    }


    @Test
    void testCalculateHPEdge() {
        testStat.calculateHP(1, 4);
        assertEquals(1, testStat.getHP());
    }

    @Test
    void testCalculateScore() {
        testStat.calculateScore(5, 12);
        assertEquals(41, testStat.getScore());
    }

    @Test
    void testCalculateHighestPossibleScore() {
        testStat.calculateHighestPossibleScore(5, 14);
        assertEquals(33, testStat.getHighestPossibleScore());
    }







    @Test
    void testDeltaHP() {
        testStat.deltaHP(-1);
        assertEquals(-1, testStat.getHP());
    }


    @Test
    void testDeltaHPEdgeCase() {
        testStat.deltaHP(-599);
        assertEquals(-599, testStat.getHP());
    }
}