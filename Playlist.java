import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Iterator;

public class Playlist implements FilteredSongIterable, Cloneable, OrderedSongIterable{
    private  ArrayList<Song> songs;
    private ArrayList<Song> originalPlaylist;
    private int numberOfSong;
    private boolean genre;
    private boolean artist;
    private boolean duration;
    private ScanningOrder scanningOrder;

    public Playlist() {
        songs = new ArrayList<>();
        originalPlaylist = new ArrayList<>();
        numberOfSong = 0;
        genre = false;
        artist = false;
        duration = false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < songs.size(); i++) {
            sb.append(songs.get(i).toString());
            if (i != songs.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    public void addSong(Song song) throws SongAlreadyExistsException {
        for (Song existingSong : songs) {
            if (existingSong.equals(song)) {
                throw new SongAlreadyExistsException("Cannot add the song!");
            }
        }
        song.setPlaceInPlaylist(numberOfSong+1);
        songs.add(song);
        numberOfSong ++;
    }

    public boolean removeSong(Song song) {
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).equals(song)) {
                songs.remove(i);
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
            copyPlaylist.numberOfSong = numberOfSong;
            for (Song song : songs){
                Song songCopy = song.clone();
                copyPlaylist.songs.set(index, songCopy);
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
            for (Song song1 : songs){
                for (Song song2 : otherPlaylist.songs){
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

    public void checkIfFirstTime(){
        if (genre == false && duration == false && artist==false){
            for (Song song : songs){
                originalPlaylist.add(song);
            }
        }
    }
    public ArrayList<Song> cloneArraylist(){
        ArrayList<Song> copyArraylist = (ArrayList<Song>) originalPlaylist.clone();
        for (Song song : originalPlaylist){
            copyArraylist.add(song.clone());
        }
        return copyArraylist;
    }
    public void alreadyFiltered(){
        if(genre == true && duration == true && artist==true){
            songs = cloneArraylist();
            genre = false;
            duration = false;
            artist = false;
        }

    }
    @Override
    public void filterArtist(String artistName){
        checkIfFirstTime();
        alreadyFiltered();
        if (artistName == null){
            return;
        }
        for (int i = 0; i<songs.size(); i++){
            if (!(artistName.equals(songs.get(i).getArtist()))){
                removeSong(songs.get(i));
                i = -1;
            }
        }
        artist = true;
    }

    @Override
    public void filterGenre(Song.Genre genre){
        checkIfFirstTime();
        alreadyFiltered();
        if (genre == null){
            return;
        }
        for (int i = 0; i < songs.size(); i++){
            if (!(genre.equals(songs.get(i).getGenre()))){
                removeSong(songs.get(i));
                i = -1;
            }
        }
        this.genre = true;
    }

    @Override
    public void filterDuration(int maxDuration){
        checkIfFirstTime();
        alreadyFiltered();
        for (int i = 0; i<songs.size(); i++){
            if (songs.get(i).getDuration() > maxDuration){
                removeSong(songs.get(i));
                i = -1;
            }
        }
        duration = true;
    }

    @Override
    public void setScanningOrder(ScanningOrder order){
        this.scanningOrder = order;
        if (order.equals(ScanningOrder.NAME)) {
            Comparator<Song> byArtist = Comparator.comparing(Song::getName).thenComparing(Song::getArtist);
            songs.sort(byArtist);
        } else if (order.equals(ScanningOrder.ADDING)) {
            Comparator<Song> byOrder = Comparator.comparing(Song::getPlaceInPlaylist);
            songs.sort(byOrder);
        } else if (order.equals(ScanningOrder.DURATION)) {
            Comparator<Song> byTime = Comparator.comparing(Song::getDuration).thenComparing(Song::getName).thenComparing(Song::getArtist);
            songs.sort(byTime);
        }
    }

    @Override
    public Iterator<Song> iterator() {
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
