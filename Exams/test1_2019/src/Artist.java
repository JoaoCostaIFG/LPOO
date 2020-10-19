import java.util.Objects;

public class Artist extends Act {
    private String name, country;

    public Artist(String name, String country) {
        this.name = name;
        this.country = country;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return name.equals(artist.name) &&
                country.equals(artist.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country);
    }
}
