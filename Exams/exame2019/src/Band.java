import java.util.ArrayList;
import java.util.List;

public class Band extends Act {
    private List<Artist> artists;

    public Band(String name, String country) {
        super(name, country);
        this.artists = new ArrayList<>();
    }

    public void addArtist(Artist a) {
        this.artists.add(a);
    }

    public List<Artist> getArtists() {
        return this.artists;
    }

    public boolean containsArtist(Artist toFind) {
        for (Artist a : artists) {
            if (a.equals(toFind))
                return true;
        }
        return false;
    }
}
