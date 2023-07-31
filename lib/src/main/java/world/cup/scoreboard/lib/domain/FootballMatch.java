package world.cup.scoreboard.lib.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Match functionality to contains information about play
 */
@Data
@AllArgsConstructor
@Builder(toBuilder=true)
public class FootballMatch {

    /**
     * Container to hold scores in format 0-0
     * @param homeTeamScore Integer value for home team
     * @param awayTeamScore Integer value for away team
     */
    public record MatchScores(Integer homeTeamScore, Integer awayTeamScore) {}
    private final Long id;
    private final String homeTeamName;
    private final String awayTeamName;
    private final ZonedDateTime startTime;

    /**
     * Container with current match scores
     */
    private final MatchScores matchScores;
    private final ZonedDateTime endTime;



    /**
     * Total match score
     * @return Calculated total score using current {@MatchScores}
     */
    public Integer getTotalScores(){
        return matchScores.homeTeamScore + matchScores.awayTeamScore;
    }

    public Optional<ZonedDateTime> fetchEndTime(){
        return Optional.ofNullable(endTime);
    }
}
