import java.util.LinkedList;
import java.util.List;

public class Band extends Act {
    private String name, country;
    private List<Artist> artists;

    public Band(String name, String country) {
        this.name = name;
        this.country = country;

        this.artists = new LinkedList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCountry() {
        return country;
    }

    public void addArtist(Artist artist) {
        this.artists.add(artist);
    }

    public List<Artist> getArtists() {
        return this.artists;
    }

    public boolean containsArtist(Artist artist) {
        for (Artist a : artists)
            if (artist.equals(a))
                return true;

        return false;
    }
}
