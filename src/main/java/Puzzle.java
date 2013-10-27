import com.google.common.base.Predicate;
import domain.Match;
import domain.item.Color;
import domain.item.Family;
import domain.item.Gift;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Lists.newArrayList;

public class Puzzle {
    private List<Match> matches;

    public Puzzle() {
        matches = new ArrayList<Match>();
        for (Family family : Family.values()) {
            for (Gift gift : Gift.values()) {
                for (Color color : Color.values()) {
                    matches.add(new Match(family, gift, color));
                }
            }
        }
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void eliminate(Family family, Gift gift) {
        ArrayList<Match> toRemove = new ArrayList<Match>();
        for (Match match : matches) {
            if(match.getFamily() == family && match.getGift() == gift)
                toRemove.add(match);
        }
        matches.removeAll(toRemove);

    }

    public void eliminate(Gift gift, Color color) {
        ArrayList<Match> toRemove = new ArrayList<Match>();
        for (Match match : matches) {
            if(match.getGift() == gift && match.getColor() == color)
                toRemove.add(match);
        }
        matches.removeAll(toRemove);
    }

    public void eliminate(Family family, Color color) {
        ArrayList<Match> toRemove = new ArrayList<Match>();
        for (Match match : matches) {
            if (match.getFamily() == family && match.getColor() == color) {
                toRemove.add(match);
            }
        }
        matches.removeAll(toRemove);
    }

    public void correlate(final Family family, final Color color) {
        Collection<Match> otherColorsForFam = filter(getMatchesFor(family), new Predicate<Match>() {
            @Override
            public boolean apply(Match match) {
                return match.getColor() != color;
            }
        });
        matches.removeAll(otherColorsForFam);

        Collection<Match> otherFamilyForColor = filter(getMatchesFor(color), new Predicate<Match>() {
            @Override
            public boolean apply(Match match) {
                return match.getFamily() != family;
            }
        });
        matches.removeAll(otherFamilyForColor);
    }

    public void correlate(final Family family, final Gift gift) {
        Collection<Match> otherGiftsForFam = filter(getMatchesFor(family), new Predicate<Match>() {
            @Override
            public boolean apply(Match match) {
                return match.getGift() != gift;
            }
        });
        matches.removeAll(otherGiftsForFam);

        Collection<Match> otherFamForGift = filter(getMatchesFor(gift), new Predicate<Match>() {
            @Override
            public boolean apply(Match match) {
                return match.getFamily() != family;
            }
        });
        matches.removeAll(otherFamForGift);
    }

    public void correlate(final Gift gift, final Color color) {
        Collection<Match> otherColorsForGift = filter(getMatchesFor(gift), new Predicate<Match>() {
            @Override
            public boolean apply(Match match) {
                return match.getColor() != color;
            }
        });
        matches.removeAll(otherColorsForGift);

        Collection<Match> otherGiftsForColor = filter(getMatchesFor(color), new Predicate<Match>() {
            @Override
            public boolean apply(Match match) {
                return match.getGift() != gift;
            }
        });
        matches.removeAll(otherGiftsForColor);
    }

    public List<Match> getMatchesFor(final Family family) {
        return newArrayList(filter(new ArrayList<Match>(this.matches), new Predicate<Match>() {
            @Override
            public boolean apply(Match match) {
                return match.getFamily() == family;
            }
        }));
    }

    public List<Match> getMatchesFor(final Color color) {
        return newArrayList(filter(new ArrayList<Match>(this.matches), new Predicate<Match>() {
            @Override
            public boolean apply(Match match) {
                return match.getColor() == color;
            }
        }));
    }

    public List<Match> getMatchesFor(final Gift gift) {
        return newArrayList(filter(new ArrayList<Match>(this.matches), new Predicate<Match>() {
            @Override
            public boolean apply(Match match) {
                return match.getGift() == gift;
            }
        }));
    }

    public void solve() {
        ArrayList<Match> finalMatches = new ArrayList<Match>();
        for (Match match : matches) {
            if(anyItemsAreSolved(match)){
                finalMatches.add(match);
            }
        }
        for (Match finalMatch : finalMatches) {
            solveForMatch(finalMatch);
        }
    }

    private boolean anyItemsAreSolved(Match match) {
        return isSolved(getMatchesFor(match.getColor()))
                || isSolved(getMatchesFor(match.getFamily()))
                || isSolved(getMatchesFor(match.getGift()));
    }

    private void solveForMatch(Match match) {
        correlate(match.getFamily(), match.getColor());
        correlate(match.getFamily(), match.getGift());
        correlate(match.getGift(), match.getColor());
    }

    private boolean isSolved(List<Match> matchesFor) {
        return matchesFor.size() == 1;
    }
}
