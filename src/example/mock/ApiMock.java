package example.mock;

import java.util.List;

public class ApiMock {

    /**
     * This is just a mock for an api
     */
    public List<Artist> getArtists() {
        return List.of(
                new Artist("Drake", 0.3),
                new Artist("Eminem", 0.5),
                new Artist("Kanye West", 0.6)
        );
    }
}
