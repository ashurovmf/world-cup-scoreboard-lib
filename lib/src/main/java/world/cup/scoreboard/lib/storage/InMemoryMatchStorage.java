package world.cup.scoreboard.lib.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import world.cup.scoreboard.lib.domain.FootballMatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryMatchStorage implements MatchStorage {

    Logger logger = LoggerFactory.getLogger(InMemoryMatchStorage.class);

    /**
     * For id generation
     */
    private static volatile Long idCounter = 1L;

    private final Map<Long, FootballMatch> storage;

    public InMemoryMatchStorage() {
        this(new HashMap<>());
    }

    public InMemoryMatchStorage(Map<Long, FootballMatch> storage) {
        this.storage = storage;
    }

    @Override
    public FootballMatch saveMatch(FootballMatch match) {
        if (logger.isDebugEnabled()) {
            logger.debug("Save match " + match);
        }

        if (match.getId() > 0L && storage.containsKey(match.getId())) {
            throw new IllegalArgumentException("Match object is already in storage");
        } else {
            FootballMatch matchCandidate = match;
            if (matchCandidate.getId() == 0L) {
                matchCandidate = matchCandidate.toBuilder().id(generateNextId()).build();
            }
            storage.put(matchCandidate.getId(), matchCandidate);
            return matchCandidate;
        }

    }

    @Override
    public FootballMatch updateMatch(FootballMatch match) {
        if (logger.isDebugEnabled()) {
            logger.debug("Update match " + match);
        }
        if (storage.containsKey(match.getId())) {
            storage.put(match.getId(), match);
            return match;
        } else {
            throw new IllegalArgumentException("There is no object in storage");
        }

    }

    @Override
    public FootballMatch fetchMatch(Long id) {
        if (logger.isDebugEnabled()) {
            logger.debug("Try to fetch match using id " + id);
        }
        if (!storage.containsKey(id)) {
            throw new IllegalArgumentException("There is no match with that id");
        }
        return storage.get(id);
    }

    @Override
    public List<FootballMatch> getAllMatches() {
        return storage.values().stream().toList();
    }

    @Override
    public List<FootballMatch> getAllInProgressMatches() {
        return storage.values().stream().filter(m -> m.fetchEndTime().isEmpty()).toList();
    }

    private synchronized Long generateNextId() {
        return idCounter++;
    }
}
