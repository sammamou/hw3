public class EmptyStackException  extends StackException{
    public static String message = "EmptyStackException";
    public EmptyStackException() {
        super(message);
    }
}
