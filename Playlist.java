import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Playlist implements Iterable<Song> {
    private List<Song> songs;

    public Playlist() {
        songs = new ArrayList<>();
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

        songs.add(song);
    }

    public boolean removeSong(Song song) {
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).equals(song)) {
                songs.remove(i);
                return true;
            }
        }
        return false;
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
