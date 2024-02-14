package example.cache;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Optional.ofNullable;

public class TtlInMemoryCache<K, V> implements Cache<K, V>, AutoCloseable{

    private final Map<K, ValueWithTimestamp<V>> store = new ConcurrentHashMap<>();

    private final Thread cleanerThread;

    public TtlInMemoryCache(Long ttlInMilliseconds) {
        cleanerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(ttlInMilliseconds);
                     // Remove all entries that are older than the ttl
                     // This is an atomic operation
                    store.entrySet().removeIf(entry -> {
                        long now = System.currentTimeMillis();
                        long entryTime = entry.getValue().timestamp().getTime();
                        boolean shouldRemove = now - entryTime > ttlInMilliseconds;
                        if(shouldRemove) {
                            System.out.println("Removing entry with key because ttl expired: " + entry.getKey());
                        }
                        return shouldRemove;
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        cleanerThread.start();
    }

    @Override
    public void put(K key, V value) {
        store.put(key, new ValueWithTimestamp<>(value, new Timestamp(System.currentTimeMillis())));
    }

    @Override
    public Optional<V> get(K key) {
        return ofNullable(store.get(key))
                .map(ValueWithTimestamp::value);
    }

    @Override
    public void close() throws Exception {
        cleanerThread.interrupt();
    }

    record ValueWithTimestamp<V>(V value, Timestamp timestamp) {
    }
}
