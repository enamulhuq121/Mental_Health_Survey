package DataSync;

/**
 * Created by TanvirHossain on 08/03/2015.
 */
public interface Listener<T> {
    void onEvent(T data);
}

