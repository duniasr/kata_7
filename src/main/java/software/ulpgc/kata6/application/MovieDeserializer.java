package software.ulpgc.kata6.application;

import software.ulpgc.kata6.architecture.model.Movie;

public class MovieDeserializer {
    public static Movie fromTsv(String s){
        return fromTsv(s.split("\t"));
    }

    private static Movie fromTsv(String[] split) {
        return new Movie(split[2], toInt(split[5]), toInt(split[7]));
    }

    private static int toInt(String s) {
        if(s.equals("\\N")) return -1;
        return Integer.parseInt(s);
    }
}
