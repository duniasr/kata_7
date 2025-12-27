package software.ulpgc.kata6.application;

import software.ulpgc.kata6.architecture.io.Store;
import software.ulpgc.kata6.architecture.model.Movie;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public class RemoteStore implements Store {
    private static final String  RemoteUrl = "https://datasets.imdbws.com/title.basics.tsv.gz";
    private final Function<String, Movie> deserializer;

    public RemoteStore(Function<String, Movie> deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public Stream<Movie> movies() {
        try{
            return loadFrom(new URL(RemoteUrl));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private Stream<Movie> loadFrom(URL url) throws IOException{
        return loadFrom(url.openConnection());
    }

    private Stream<Movie> loadFrom(URLConnection urlConnection) throws IOException{
        return loadFrom(unzip(urlConnection.getInputStream()));
    }

    private InputStream unzip(InputStream inputStream) throws IOException {
        return new GZIPInputStream(new BufferedInputStream(inputStream));
    }

    private Stream<Movie> loadFrom(InputStream inputStream) throws IOException{
        return loadFrom(toReader(inputStream));
    }

    private BufferedReader toReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private Stream<Movie> loadFrom(BufferedReader bufferedReader) throws IOException {
        List<Movie> list = new ArrayList<>();

        return bufferedReader.lines().skip(1).map(deserializer);
    }
}
