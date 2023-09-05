package model;

import model.Event;
import model.HangmanCharacter;
import model.PlayerStat;
import model.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Date d;

    @BeforeEach
    public void runBefore() {
        HangmanCharacter hc = new HangmanCharacter('a');


        e = new Event("HangmanCharacter created: " + hc.getCharacter()); // (1)
        d = Calendar.getInstance().getTime(); // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("HangmanCharacter created: a", e.getDescription());
        assertEquals(d, e.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "HangmanCharacter created: a", e.toString());
    }
}