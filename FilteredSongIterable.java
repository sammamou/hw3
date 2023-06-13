public interface FilteredSongIterable extends Iterable<Song> {
    void filterArtist(String artistName);
    void filterGenre(Song genre);
    void filterDuration(int maxDuration);
}

