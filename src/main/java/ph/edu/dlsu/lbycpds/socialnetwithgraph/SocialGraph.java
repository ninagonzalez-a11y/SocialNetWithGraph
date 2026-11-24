package ph.edu.dlsu.lbycpds.socialnetwithgraph;
import java.util.*;

// creates an undirected graph
// person -> vertex
// friendship -> edge

public class SocialGraph {
    private final Map<String, Profile> profiles = new LinkedHashMap<>();
    private final Map<String, Set<String>> adjacency = new HashMap<>();

    public boolean addProfile(String name) {
        // if name exists, return false
        // otherwise, make a Profile and store it in profiles
        // give the profile an empty friend set in adjacency
        if (profiles.containsKey(name)) return false;
        profiles.put(name, new Profile(name));
        adjacency.put(name, new LinkedHashSet<>());
        return true;
    }

    public boolean deleteProfile(String name) {
        // if name does not exist, return false
        // otherwise, remove the name from the friend's set
        // remove name from both maps to remove a vertex (friend) and all its edges (friendships)
        if (!profiles.containsKey(name)) return false;
        for (String friend : adjacency.get(name)) adjacency.get(friend).remove(name);
        adjacency.remove(name);
        profiles.remove(name);
        return true;
    }

    public Profile lookup(String name) { return profiles.get(name); }

    public boolean addFriend(String a, String b) {
        // rejects a person adding themselves or non-existent person
        // otherwise, it adds b to a's set and a to b's set since friendship should show up in both profiles
        if (a.equals(b) || !profiles.containsKey(a) || !profiles.containsKey(b)) return false;
        adjacency.get(a).add(b);
        adjacency.get(b).add(a);
        return true;
    }

    public boolean unfriend(String a, String b) {
        // removes each person from one another's set
        // returns true if they were originally friends
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

