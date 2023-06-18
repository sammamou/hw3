import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Playlist implements FilteredSongIterable, Cloneable, OrderedSongIterable{
    private  ArrayList<Song> songs;
    private ArrayList<Song> originalPlaylist;
    private int numberOfSong;
    private Song.Genre genre;
    private String artist;
    private int duration;
    private ScanningOrder scanningOrder;

    public Playlist() {
        originalPlaylist = new ArrayList<>();
        songs = new ArrayList<>();
        numberOfSong = 0;
        genre = null;
        artist = null;
        duration = -1;
        scanningOrder = ScanningOrder.ADDING;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < songs.size(); i++) {
            sb.append("(");
            sb.append(songs.get(i).toString());
            sb.append(")");
            if (i != songs.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    public void addSong(Song song) throws SongAlreadyExistsException {
        for (Song existingSong : originalPlaylist) {
            if (existingSong.equals(song)) {
                throw new SongAlreadyExistsException("Cannot add the song!");
            }
        }
        originalPlaylist.add(song);
        //in case of calling the function ToString without iterating
        songs.add(song);
        numberOfSong ++;
    }

    public boolean removeSong(Song song) {
        for (int i = 0; i < originalPlaylist.size(); i++) {
            if (originalPlaylist.get(i).equals(song)) {
                songs.remove(i);
                originalPlaylist.remove(i);
                numberOfSong--;
                return true;
            }
        }
        return false;
    }
    @Override
    public Playlist clone(){
        try {
            int index = 0;
            Playlist copyPlaylist = (Playlist) super.clone();
            copyPlaylist.songs = (ArrayList<Song>) songs.clone();
            copyPlaylist.originalPlaylist = (ArrayList<Song>) originalPlaylist.clone();
            copyPlaylist.numberOfSong = numberOfSong;
            for (Song song : songs){
                Song songCopy = song.clone();
                copyPlaylist.songs.set(index, songCopy);
                copyPlaylist.originalPlaylist.set(index, songCopy);
                index ++;
            }
            return copyPlaylist;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public boolean equals(Object other){
        if (!(other instanceof Playlist)) {
            return false;
        }
        Playlist otherPlaylist = (Playlist) other;
        if (numberOfSong != otherPlaylist.numberOfSong){
            return false;
        }else {
            int counter = 0;
            for (Song song1 : originalPlaylist){
                for (Song song2 : otherPlaylist.originalPlaylist){
                    if(song1.equals(song2)){
                        counter++;
                        break;
                    }
                }
            }
            //je compte combien de chansons en commun si c'est le meme nombre que le nombre de chanson total alors egaux.
            return counter == numberOfSong;
        }
    }
    @Override
    public int hashCode(){
        int hashCode = numberOfSong;
        for (Song song : songs){
            hashCode += song.hashCode();
        }
        return hashCode;
    }

    public void creatFilteredList(){
        this.songs = new ArrayList<>();
        for (Song song : originalPlaylist){
            songs.add(song.clone());
        }
        if (artist != null){
            removeArtist();
        }
        if (genre != null){
            removeGenre();
        }
        if (duration != -1){
            removeDuration();
        }
    }
    @Override
    public void filterArtist(String artistName){
        artist = artistName;
    }
    public void removeArtist(){
        for(int i =0; i<songs.size(); i++){
            if (!(artist.equals(songs.get(i).getArtist()))){
                songs.remove(i);
                i--;
            }
        }
    }

    @Override
    public void filterGenre(Song.Genre genre){
        this.genre = genre;
    }

    public void removeGenre(){
        for(int i =0; i<songs.size(); i++){
            if (!(genre.equals(songs.get(i).getGenre()))){
                songs.remove(i);
                i--;
            }
        }
    }
    @Override
    public void filterDuration(int maxDuration){
        duration = maxDuration;
    }

    public void removeDuration() {
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getDuration() > duration) {
                songs.remove(i);
                i--;
            }
        }
    }
    @Override
    public void setScanningOrder(ScanningOrder order) {
        this.scanningOrder = order;
    }
    public void createOrder(){
        if (scanningOrder.equals(ScanningOrder.NAME)) {
            Comparator<Song> byName = Comparator.comparing(Song::getName);
            songs.sort(byName);
        } else if (scanningOrder.equals(ScanningOrder.ADDING)) {
        } else if (scanningOrder.equals(ScanningOrder.DURATION)) {
            Comparator<Song> byTime = Comparator.comparing(Song::getDuration);
            songs.sort(byTime);
        }
    }

    @Override
    public Iterator<Song> iterator() {
        creatFilteredList();
        createOrder();
        return new PlaylistIterator();
    }

    private class PlaylistIterator implements Iterator<Song> {
        private int currentIndex;
        public PlaylistIterator() {
            currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < songs.size();
        }

        @Override
        public Song next() {
            Song song = songs.get(currentIndex);
            currentIndex++;
            return song;
        }
    }
}
