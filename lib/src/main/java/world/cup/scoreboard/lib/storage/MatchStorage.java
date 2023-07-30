package world.cup.scoreboard.lib.storage;

import world.cup.scoreboard.lib.domain.FootballMatch;

import java.util.List;

public interface MatchStorage {
    Boolean saveMatch(FootballMatch match);

    Boolean updateMatch(FootballMatch match);

    List<FootballMatch> getAllMatches();

    List<FootballMatch> getAllInProgressMatches();
}
