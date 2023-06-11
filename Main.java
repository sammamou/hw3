import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

class MyCloneable implements Cloneable {
    private int num;

    public MyCloneable(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "MyCloneable: " + num;
    }

    @Override
    public MyCloneable clone() {
        try {
            return (MyCloneable) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}


public class Main {
    public static void main(String[] args) {
        testPartA();
        //testPartB();
    }

    /**
     * Tests for part A.
     */
    private static void testPartA() {
        try {
            new ArrayStack<>(-25);
        } catch (NegativeCapacityException e) {
            System.out.println("Negative capacity!");
        }

        Stack<MyCloneable> stack1 = new ArrayStack<>(5);
        ArrayStack<MyCloneable> stack2 = (ArrayStack<MyCloneable>) stack1;

        try {
            stack1.peek();
        } catch (EmptyStackException e) {
            System.out.println("The stack is empty");
        }

        try {
            stack2.peek();
        } catch (StackException e) {
            System.out.println("The stack is empty");
        }
        System.out.println();

        iterateStack(stack1, "stack1");

        MyCloneable c1 = new MyCloneable(1);
        stack1.push(c1);
        iterateStack(stack1, "stack1");
        System.out.println("Peek: " + stack1.peek());
        System.out.println("Pop: " + stack1.pop());
        System.out.println();
        iterateStack(stack1, "stack1");

        MyCloneable c2 = new MyCloneable(2);
        stack1.push(c1);
        stack1.push(c2);

        iterateStack(stack2, "stack2");

        ArrayStack<MyCloneable> stack3 = stack2.clone();
        Stack<MyCloneable> stack4 = stack1.clone();

        System.out.println("Is stack1 == stack3? " + (stack1 == stack3));
        System.out.println("Is stack2 == stack3? " + (stack2 == stack3));
        System.out.println("Is stack1 == stack4? " + (stack1 == stack4));
        System.out.println("Is stack2 == stack4? " + (stack2 == stack4));
        System.out.println();
        iterateStack(stack3, "stack3");

        c1.setNum(15);
        iterateStack(stack1, "stack1");
        iterateStack(stack2, "stack2");
        iterateStack(stack3, "stack3");
        iterateStack(stack4, "stack4");

        stack1.push(new MyCloneable(3));
        iterateStack(stack1, "stack1");
        iterateStack(stack2, "stack2");
        iterateStack(stack3, "stack3");
        iterateStack(stack4, "stack4");

        stack1.push(new MyCloneable(4));
        iterateStack(stack1, "stack1");

        stack1.push(new MyCloneable(5));
        iterateStack(stack1, "stack1");
        iterateStack(stack2, "stack2");
        iterateStack(stack3, "stack3");
        iterateStack(stack4, "stack4");

        try {
            stack1.push(c1);
        } catch (StackOverflowException e) {
            System.out.println("The stack reached its full capacity.");
        }

        try {
            stack2.push(new MyCloneable(21));
        } catch (StackException e) {
            System.out.println("The stack reached its full capacity.");
        }

        System.out.println("\nTesting of part A is over!\n\n");

    }


    /**
     * Iterates over a given stack.
     */
    private static void iterateStack(Stack<?> stack, String name) {
        System.out.println(name + " size: " + stack.size());
        System.out.println("Is empty? " + stack.isEmpty());

        System.out.println("Starts iterating " + name + "...");
        for (Cloneable element : stack) {
            System.out.println(element);
        }
        System.out.println("Done iterating " + name + ".");
        System.out.println();
    }
}

    /**
     * Tests for part B.
     */

    /*
    private static void testPartB() {
        Playlist playlist1 = new Playlist();
        for (Song s : playlist1) {
            System.out.println("You should not reach here!");
        }

        Song song1 = new Song("Unicorn", "Noa Kirel", Song.Genre.POP, 171);
        Song song2 = new Song("Born In The U.S.A.", "Bruce Springsteen", Song.Genre.ROCK, 278);
        Song song3 = new Song("Take Five", "Dave Brubeck", Song.Genre.JAZZ, 175);
        Song song4 = new Song("Easter Egg", "Dvir ft. Gal", Song.Genre.HIP_HOP, 230);

        playlist1.addSong(song1);
        playlist1.addSong(song4);
        playlist1.addSong(song3);
        playlist1.addSong(song2);

        System.out.println("playlist1: " + playlist1);

        Playlist playlist2 = playlist1.clone();
        System.out.println("playlist1: " + playlist1);
        System.out.println("playlist2: " + playlist2);

        System.out.println("Is playlist1 == playlist2? " + (playlist1 == playlist2));
        System.out.println("Is playlist1 equal to playlist2? " + playlist1.equals(playlist2));
        System.out.println("Is playlist2 equal to playlist1? " + playlist2.equals(playlist1));
        System.out.println("Is playlist1 equal to null? " + playlist1.equals(null));

        song4.setDuration(437);

        System.out.println("playlist1: " + playlist1);
        System.out.println("playlist2: " + playlist2);

        System.out.println();

        System.out.println("Is playlist1 equal to playlist2? " + playlist1.equals(playlist2));
        System.out.println("Is playlist2 equal to playlist1? " + playlist2.equals(playlist1));


        try {
            playlist1.addSong(new Song("Unicorn", "Noa Kirel", Song.Genre.POP, 180));
        } catch (SongAlreadyExistsException e) {
            System.out.println("Cannot add the song!");
        }
        try {
            playlist1.addSong(new Song("Born In The U.S.A.", "Bruce Springsteen", Song.Genre.ROCK, 278));
        } catch (SongAlreadyExistsException e) {
            System.out.println("Cannot add the song!");
        }
        Song clonedSong1 = song1.clone();
        try {
            playlist1.addSong(clonedSong1);
        } catch (SongAlreadyExistsException e) {
            System.out.println("Cannot add the song!");
        }

        System.out.println("song1: " + song1);
        System.out.println("clonedSong1: " + clonedSong1);
        System.out.println("Is song1 equal to clonedSong1? " + song1.equals(clonedSong1));
        System.out.println("Is clonedSong1 equal to song1? " + clonedSong1.equals(song1));
        System.out.println("Is song1 == clonedSong1? " + (song1 == clonedSong1));
        System.out.println("Is song1.equals(null)? " + song1.equals(null));
        System.out.println();

        Playlist playlist3 = new Playlist();

        System.out.println("Is playlist1 equal to playlist3? " + playlist1.equals(playlist3));
        playlist3.addSong(song2);
        System.out.println("playlist3: " + playlist3);
        System.out.println("Is playlist1 equal to playlist3? " + playlist1.equals(playlist3));

        playlist3.addSong(song4);
        System.out.println("playlist3: " + playlist3);
        System.out.println("Is playlist1 equal to playlist3? " + playlist1.equals(playlist3));

        playlist3.addSong(song1);
        System.out.println("playlist3: " + playlist3);
        System.out.println("Is playlist1 equal to playlist3? " + playlist1.equals(playlist3));

        playlist3.addSong(song3);
        System.out.println("playlist3: " + playlist3);
        System.out.println("Is playlist1 equal to playlist3? " + playlist1.equals(playlist3));

        boolean result = playlist3.removeSong(song3);
        System.out.println("Remove result: " + result);
        System.out.println("playlist3: " + playlist3);
        System.out.println("Is playlist1 equal to playlist3? " + playlist1.equals(playlist3));

        playlist3.addSong(song3);
        System.out.println("playlist3: " + playlist3);
        System.out.println("Is playlist1 equal to playlist3? " + playlist1.equals(playlist3));

        System.out.println();
        Set<Playlist> set = new HashSet<>();
        set.add(playlist1);
        set.add(playlist3);
        System.out.println("Number of playlists in set: " + set.size());

        Song[] songs = {
                new Song("I Walk the Line", "Johnny Cash", Song.Genre.COUNTRY, 166),
                new Song("Shape of You", "Ed Sheeran", Song.Genre.POP, 234),
                new Song("Y.M.C.A.", "Village People", Song.Genre.DISCO, 199),
                new Song("Dancing Queen", "ABBA", Song.Genre.DISCO, 231),
                new Song("Le Freak", "Chic", Song.Genre.DISCO, 307),
                new Song("Counting Stars", "OneRepublic", Song.Genre.POP, 257),
                new Song("Shape of My Heart", "Backstreet Boys", Song.Genre.POP, 230),
                new Song("Another Easter Egg", "Ahmed Jabara", Song.Genre.ROCK, 227),
                new Song("Mamma Mia", "ABBA", Song.Genre.POP, 213),
                new Song("Waterloo", "ABBA", Song.Genre.POP, 162),
                new Song("Take a Chance on Me", "ABBA", Song.Genre.POP, 260),
        };

        for (Song song : songs) {
            playlist1.addSong(song);
        }

        String[] artists = {"ABBA", null, "Backstreet Boys"};
        int[] durations = {-10, 0, 200, 900};

        checkScans(playlist1, artists, durations);
        checkScans(playlist3, artists, durations);

        System.out.println("\nTesting of part B is over!");
    }

    /**
     * Checks numerous scans for a given playlist.
     */

    /*
    private static void checkScans(Playlist playlist, String[] artists, int[] durations) {
        System.out.println("Starts scanning...");
        for (Song song : playlist) {
            System.out.println(song);
            System.out.println("-----------------------------------");
        }

        System.out.println("After initial scanning\n");

        int i = 1;

        for (Song.Genre genre : Song.Genre.values()) {
            for (int duration : durations) {
                for (String artist : artists) {
                    for (ScanningOrder order : ScanningOrder.values()) {
                        playlist.filterArtist(artist);
                        playlist.filterDuration(duration);
                        playlist.filterGenre(genre);
                        playlist.setScanningOrder(order);
                        System.out.println("Starting scan number " + i + " [" + genre + ", " + duration + ", " + artist + ", " + order + "]");
                        for (Song song : playlist) {
                            System.out.println(song);
                        }
                        System.out.println("After scan number " + i++ + ".\n");
                    }
                }
            }
        }

        System.out.println("Done all scanning.");
    }
}
*/