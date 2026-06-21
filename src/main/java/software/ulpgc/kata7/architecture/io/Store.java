package software.ulpgc.kata7.architecture.io;

import software.ulpgc.kata7.architecture.model.Movie;

import java.util.stream.Stream;

public interface Store {
    Stream<Movie> movies();
}
