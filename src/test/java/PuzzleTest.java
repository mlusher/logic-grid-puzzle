import domain.Match;
import junitx.framework.ListAssert;
import org.junit.Before;
import org.junit.Test;
import support.MatchAssert;

import java.util.List;

import static domain.item.Color.*;
import static domain.item.Family.*;
import static domain.item.Gift.*;
import static java.util.Arrays.asList;
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
        //3
        puzzle.correlate(GRANDFATHER, WATCH);
        puzzle.eliminate(GRANDFATHER, PURPLE);
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
        MatchAssert.assertDoesNotContain(puzzle.getMatchesFor(GRANDFATHER), GOLD);
        MatchAssert.assertDoesNotContain(puzzle.getMatchesFor(GOLD), GRANDFATHER);
    }

    @Test
    public void eliminatingFamilyGiftPairShouldRemoveAllMatches() {
        puzzle.eliminate(GRANDFATHER, WATCH);
        MatchAssert.assertDoesNotContain(puzzle.getMatchesFor(GRANDFATHER), WATCH);
        MatchAssert.assertDoesNotContain(puzzle.getMatchesFor(WATCH), GRANDFATHER);
    }

    @Test
    public void eliminatingGiftColorPairShouldRemoveAllMatches() {
        puzzle.eliminate(GLOVES, PURPLE);
        MatchAssert.assertDoesNotContain(puzzle.getMatchesFor(GLOVES), PURPLE);
        MatchAssert.assertDoesNotContain(puzzle.getMatchesFor(PURPLE), GLOVES);
    }

    @Test
    public void correlateFamilyColorPairShouldEliminateAllOthers() {
        puzzle.correlate(GRANDFATHER, GOLD);
        MatchAssert.assertOnlyContains(puzzle.getMatchesFor(GRANDFATHER), GOLD);
        MatchAssert.assertOnlyContains(puzzle.getMatchesFor(GOLD), GRANDFATHER);
    }

    @Test
    public void correlateFamilyGiftPairShouldEliminateAllOthers() {
        puzzle.correlate(GRANDFATHER, WATCH);
        MatchAssert.assertOnlyContains(puzzle.getMatchesFor(GRANDFATHER), WATCH);
        MatchAssert.assertOnlyContains(puzzle.getMatchesFor(WATCH), GRANDFATHER);
    }

    @Test
    public void correlateGiftColorPairShouldEliminateAllOthers() {
        puzzle.correlate(WATCH, PURPLE);
        MatchAssert.assertOnlyContains(puzzle.getMatchesFor(WATCH), PURPLE);
        MatchAssert.assertOnlyContains(puzzle.getMatchesFor(PURPLE), WATCH);
    }

}
