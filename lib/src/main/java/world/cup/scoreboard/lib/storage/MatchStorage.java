package world.cup.scoreboard.lib.storage;

import world.cup.scoreboard.lib.domain.FootballMatch;

import java.util.List;

/**
 * Cover functionality of persisting matches
 */
public interface MatchStorage {

    FootballMatch saveMatch(FootballMatch match);

    FootballMatch updateMatch(FootballMatch match);

    FootballMatch fetchMatch(Long id);

    List<FootballMatch> getAllMatches();

    List<FootballMatch> getAllInProgressMatches();
}
