package world.cup.scoreboard.lib.storage;

import world.cup.scoreboard.lib.domain.Match;

import java.util.List;

public interface MatchStorage {
    Boolean saveMatch(Match match);

    List<Match> getAllMatches();

    List<Match> getAllInProgressMatches();
}
