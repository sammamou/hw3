public class Song {
    public enum Genre{
        POP,
        ROCK,
        HIP_HOP,
        COUNTRY,
        JAZZ,
        DISCO
    }
    private String name;
    private String artist;
    private Genre genre;

    private String duration;


    public Song(String name, String artist, Genre genre, String duration) {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
    }
    @Override
    public String toString() {
        return "(" + name + ", " + artist + ", " + genre + ", " + duration + ")";
    }
}
