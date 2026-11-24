package ph.edu.dlsu.lbycpds.socialnetwithgraph;
import java.util.*;

public class SocialGraph {
    private final Map<String, Profile> profiles = new LinkedHashMap<>();
    private final Map<String, Set<String>> adjacency = new HashMap<>();

    public boolean addProfile(String name) {
        if (profiles.containsKey(name)) return false;
        profiles.put(name, new Profile(name));
        adjacency.put(name, new LinkedHashSet<>());
        return true;
    }

    public boolean deleteProfile(String name) {
        if (!profiles.containsKey(name)) return false;
        for (String friend : adjacency.get(name)) adjacency.get(friend).remove(name);
        adjacency.remove(name);
        profiles.remove(name);
        return true;
    }

    public Profile lookup(String name) { return profiles.get(name); }

    public boolean addFriend(String a, String b) {
        if (a.equals(b) || !profiles.containsKey(a) || !profiles.containsKey(b)) return false;
        adjacency.get(a).add(b);
        adjacency.get(b).add(a);
        return true;
    }

    public boolean unfriend(String a, String b) {
        if (!profiles.containsKey(a) || !profiles.containsKey(b)) return false;
        boolean removed = adjacency.get(a).remove(b);
        adjacency.get(b).remove(a);
        return removed;
    }

    public Set<String> getFriends(String name) {
        return adjacency.getOrDefault(name, Collections.emptySet());
    }

    public Collection<Profile> getProfiles() { return profiles.values(); }
}

