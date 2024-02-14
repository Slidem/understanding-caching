package example.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Simple in memory cache, no eviction policy
 * Issues: memory may grow indefinitely
 *
 * @param <K> The Key type of the cache entry
 * @param <V> The Value type of the cache entry
 */
public class SimpleInMemoryCache<K, V> implements Cache<K, V> {

    private final Map<K, V> store = new HashMap<>();

    public void put(K key, V value) {
        store.put(key, value);
    }

    public Optional<V> get(K key) {
        return Optional.ofNullable(store.get(key));
    }
}
