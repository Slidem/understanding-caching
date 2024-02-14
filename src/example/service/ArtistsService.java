package example.service;

import example.cache.Cache;
import example.cache.CacheFactory;
import example.cache.CacheType;
import example.cache.TtlInMemoryCache;
import example.mock.ApiMock;
import example.mock.Artist;

import java.util.List;
import java.util.Optional;

public class ArtistsService {

        private final Cache<String, List<Artist>> cache;

        private final ApiMock apiMock;

        public ArtistsService(CacheType cacheType, Long ttl) {
            this.cache = CacheFactory.createCache(cacheType, ttl);
            this.apiMock = new ApiMock();
        }

        public List<Artist> getArtists() {

            Optional<List<Artist>> artists = cache.get("artists");

            if(artists.isPresent()) {
                System.out.println("Cache HIT ! getting artists from cache");
                return artists.get();
            } else {
                System.out.println("Cache MISS ! getting artists from api");
                List<Artist> artistsFromApi = apiMock.getArtists();
                cache.put("artists", artistsFromApi);
                return artistsFromApi;
            }
        }

}
