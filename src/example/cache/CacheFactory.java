package example.cache;

public class CacheFactory {

    public static <K, V> Cache<K, V> createCache(CacheType type, Long ttl) {
        if (type == CacheType.SIMPLE_IN_MEMORY) {
            return new SimpleInMemoryCache<K, V>();
        }
        if (type == CacheType.TTL_IN_MEMORY) {
            return new TtlInMemoryCache<K, V>(ttl);
        }
        throw new IllegalArgumentException("Cache type not supported: " + type);
    }
}
