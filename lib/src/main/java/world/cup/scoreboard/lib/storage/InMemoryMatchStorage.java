package world.cup.scoreboard.lib.storage;

import world.cup.scoreboard.lib.domain.FootballMatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryMatchStorage implements MatchStorage{

    private final Map<Long, FootballMatch> storage;

    public InMemoryMatchStorage() {
        this(new HashMap<>());
    }

    public InMemoryMatchStorage(Map<Long, FootballMatch> storage) {
        this.storage = storage;
    }

    @Override
    public Boolean saveMatch(FootballMatch match) {
        if(storage.containsKey(match.getId())){
            return Boolean.FALSE;
        } else {
            storage.put(match.getId(), match);
            return Boolean.TRUE;
        }
    }

    @Override
    public Boolean updateMatch(FootballMatch match) {
        if(storage.containsKey(match.getId())){
            storage.put(match.getId(), match);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public List<FootballMatch> getAllMatches() {
        return storage.values().stream().toList();
    }

    @Override
    public List<FootballMatch> getAllInProgressMatches() {
        return storage.values().stream().filter(m -> m.fetchEndTime().isEmpty()).toList();
    }
}
