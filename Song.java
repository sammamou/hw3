public class Song implements Cloneable {
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
    private int duration;


    public Song(String name, String artist, Genre genre, int duration) {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
    }


    public Song(Song songToClone){
        this.name = songToClone.name;
        this.artist = songToClone.artist;
        this.genre = songToClone.genre;
        this.duration = songToClone.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;

    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public Genre getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }


    public String convertIntToString(int duration){
        int minutes = duration / 60;
        int second = duration % 60;
        String minutesString = String.valueOf(minutes);
        String secondString = String.valueOf(second);
        if (second<10){
            secondString = "0"+secondString;
        }
        return minutesString + ":" + secondString;
    }


    @Override
    public Song clone(){
        Song CopySong = new Song(this);
        return CopySong;
    }
    @Override
    public String toString() {
        return name + ", " + artist + ", " + genre + ", " + convertIntToString(duration);
    }

    @Override
    public boolean equals(Object other){
        if (!(other instanceof Song)) {
            return false;
        }
        Song otherSong = (Song) other;
        return this.name == otherSong.name && this.artist == otherSong.artist;
    }

    @Override
    public int hashCode(){
        int hashCode = 0;
        String fullName = name+artist;
        for (int i = 0; i < fullName.length(); i++) {
            hashCode += fullName.charAt(i);
        }
        return hashCode;
    }
}
