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
    private String realtime;
    private int placeInPlaylist;


    public Song(String name, String artist, Genre genre, int duration) {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.realtime = convertIntToString(duration);
        this.placeInPlaylist = 0;
    }


    public Song(Song songToClone){
        this.name = songToClone.name;
        this.artist = songToClone.artist;
        this.genre = songToClone.genre;
        this.duration = songToClone.duration;
        this.realtime = songToClone.realtime;
        this.placeInPlaylist = songToClone.placeInPlaylist;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        this.realtime = convertIntToString(duration);
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

    public void setPlaceInPlaylist(int placeInPlaylist) {
        this.placeInPlaylist = placeInPlaylist;
    }

    public String convertIntToString(int duration){
        String minutes = String.valueOf(duration/100);
        String second;
        if (duration%100 < 10){
            second = "0" + String.valueOf(duration % 100);
        }else {
            second = String.valueOf(duration % 100);
        }
        String durationString = minutes + ":" + second;
        return durationString;
    }


    @Override
    public Song clone(){
        Song CopySong = new Song(this);
        return CopySong;
    }
    @Override
    public String toString() {
        return "(" + name + ", " + artist + ", " + genre + ", " + realtime + ")";
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
