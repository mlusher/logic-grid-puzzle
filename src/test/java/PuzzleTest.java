import domain.Color;
import domain.Family;
import domain.Gift;
import junitx.framework.ListAssert;
import logic.Match;
import logic.Puzzle;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static domain.Color.*;
import static domain.Family.*;
import static domain.Gift.*;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertTrue;
import static junitx.framework.Assert.assertEquals;

public class PuzzleTest {

    private Puzzle puzzle;

    @Before
    public void setUp() throws Exception {
        puzzle = new Puzzle();
    }

    @Test
    public void puzzleSolved() {
        List<Match> answer = asList(
                new Match(AUNT, BOOK, GREEN),
                new Match(GRANDFATHER, WATCH, RED),
                new Match(MOTHER, SCARF, SILVER),
                new Match(SISTER, PEN, GOLD),
                new Match(UNCLE, GLOVES, PURPLE)
        );

        //1
        puzzle.correlate(BOOK, GREEN);
        //2
        puzzle.correlate(SISTER, GOLD);
        puzzle.eliminate(SCARF, GOLD);
        puzzle.eliminate(SISTER, SCARF);
        //3
        puzzle.correlate(GRANDFATHER, WATCH);
        puzzle.eliminate(GRANDFATHER, PURPLE);
        puzzle.eliminate(WATCH, PURPLE);
        //4
        puzzle.correlate(MOTHER, SILVER);
        //5
        puzzle.correlate(UNCLE, GLOVES);

        puzzle.solve();
        ListAssert.assertEquals(answer, puzzle.getMatches());
    }

    @Test
    public void initializeShouldGenerateAllPossibleOptions() {
        assertEquals(125, puzzle.getMatches().size());
    }

    @Test
    public void getMatchesForGrandfatherShouldReturnAllPossibleGrandfatherMatches() {
        List<Match> matchesFor = puzzle.getMatchesFor(GRANDFATHER);
        assertEquals(25, matchesFor.size());
    }

    @Test
    public void getMatchesForPurpleReturnsAllPossibleMatches() {
        List<Match> matchesFor = puzzle.getMatchesFor(PURPLE);
        assertEquals(25, matchesFor.size());
    }

    @Test
    public void getMatchesForWatchReturnsAllPossibleMatches() {
        List<Match> matchesFor = puzzle.getMatchesFor(WATCH);
        assertEquals(25, matchesFor.size());
    }

    @Test
    public void eliminatingFamilyColorPairShouldRemoveMatchesWithBoth() {
        puzzle.eliminate(GRANDFATHER, GOLD);
        assertDoesNotContain(puzzle.getMatchesFor(GRANDFATHER), GOLD);
        assertDoesNotContain(puzzle.getMatchesFor(GOLD), GRANDFATHER);
    }

    @Test
    public void eliminatingFamilyGiftPairShouldRemoveAllMatches() {
        puzzle.eliminate(GRANDFATHER, WATCH);
        assertDoesNotContain(puzzle.getMatchesFor(GRANDFATHER), WATCH);
        assertDoesNotContain(puzzle.getMatchesFor(WATCH), GRANDFATHER);
    }

    @Test
    public void eliminatingGiftColorPairShouldRemoveAllMatches() {
        puzzle.eliminate(GLOVES, PURPLE);
        assertDoesNotContain(puzzle.getMatchesFor(GLOVES), PURPLE);
        assertDoesNotContain(puzzle.getMatchesFor(PURPLE), GLOVES);
    }

    @Test
    public void correlateFamilyColorPairShouldEliminateAllOthers() {
        puzzle.correlate(GRANDFATHER, GOLD);
        assertOnlyContains(puzzle.getMatchesFor(GRANDFATHER), GOLD);
        assertOnlyContains(puzzle.getMatchesFor(GOLD), GRANDFATHER);
    }

    @Test
    public void correlateFamilyGiftPairShouldEliminateAllOthers() {
        puzzle.correlate(GRANDFATHER, WATCH);
        assertOnlyContains(puzzle.getMatchesFor(GRANDFATHER), WATCH);
        assertOnlyContains(puzzle.getMatchesFor(WATCH), GRANDFATHER);
    }

    @Test
    public void correlateGiftColorPairShouldEliminateAllOthers() {
        puzzle.correlate(WATCH, PURPLE);
        assertOnlyContains(puzzle.getMatchesFor(WATCH), PURPLE);
        assertOnlyContains(puzzle.getMatchesFor(PURPLE), WATCH);
    }

    private void assertOnlyContains(List<Match> matches, Color color) {
        for (Match match : matches) {
            assertTrue("Fail: More than " + color + " found in list.", match.getColor() == color);
        }
    }

    private void assertOnlyContains(List<Match> matches, Gift gift) {
        for (Match match : matches) {
            assertTrue("Fail: More than " + gift + " found in list.", match.getGift() == gift);
        }
    }

    private void assertOnlyContains(List<Match> matches, Family family) {
        for (Match match : matches) {
            assertTrue("Fail: More than " + family + " found in list.", match.getFamily() == family);
        }
    }

    private void assertDoesNotContain(List<Match> matches, Gift gift) {
        for (Match match : matches) {
            assertTrue("Fail: " + gift + " found in list.", match.getGift() != gift);
        }
    }

    private void assertDoesNotContain(List<Match> matches, Color color) {
        for (Match match : matches) {
            assertTrue("Fail: " + color + " found in list.", match.getColor() != color);
        }
    }

    private void assertDoesNotContain(List<Match> matches, Family family) {
        for (Match match : matches) {
            assertTrue("Fail: " + family + " found in list.", match.getFamily() != family);
        }
    }
}
