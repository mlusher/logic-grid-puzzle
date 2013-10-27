package support;

import domain.Match;
import domain.item.Color;
import domain.item.Family;
import domain.item.Gift;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class MatchAssert {

    public static void assertOnlyContains(List<Match> matches, Color color) {
        for (Match match : matches) {
            assertTrue("More than " + color + " found in list.", match.getColor() == color);
        }
    }

    public static void assertOnlyContains(List<Match> matches, Gift gift) {
        for (Match match : matches) {
            assertTrue("More than " + gift + " found in list.", match.getGift() == gift);
        }
    }

    public static void assertOnlyContains(List<Match> matches, Family family) {
        for (Match match : matches) {
            assertTrue("More than " + family + " found in list.", match.getFamily() == family);
        }
    }

    public static void assertDoesNotContain(List<Match> matches, Gift gift) {
        for (Match match : matches) {
            assertTrue(gift + " found in list.", match.getGift() != gift);
        }
    }

    public static void assertDoesNotContain(List<Match> matches, Color color) {
        for (Match match : matches) {
            assertTrue(color + " found in list.", match.getColor() != color);
        }
    }

    public static void assertDoesNotContain(List<Match> matches, Family family) {
        for (Match match : matches) {
            assertTrue(family + " found in list.", match.getFamily() != family);
        }
    }
}