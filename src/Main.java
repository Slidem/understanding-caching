import example.cache.CacheType;
import example.service.ArtistsService;

public class Main {
    public static void main(String[] args) {
        ArtistsService artistsService = new ArtistsService(CacheType.TTL_IN_MEMORY, 2500L);
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    artistsService.getArtists();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}