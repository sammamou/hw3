public interface OrderedSongIterable extends Iterable<Song> {
    void setScanningOrder(ScanningOrder order);
}