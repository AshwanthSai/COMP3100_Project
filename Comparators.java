import java.util.Comparator;

public abstract class Comparators {

    public static Comparator<Server> core = new Comparator<Server>() {
        @Override
        public int compare(Server s1, Server s2) {
            return s2.core - s1.core;
        }
    };

}

