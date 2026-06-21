package software.ulpgc.kata7.application.mainBaseDatos;

import software.ulpgc.kata7.application.*;
import software.ulpgc.kata7.architecture.model.Movie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:movies.db");
        connection.setAutoCommit(false);
        importIfNeededInto(connection);
        new WebService(new DatabaseStore(connection)).start();
    }

    private static void importIfNeededInto(Connection connection) throws SQLException {
        boolean tableExists = connection.getMetaData()
                .getTables(null, null, "movies", null)
                .next();

        if (!tableExists) {
            System.out.println("Downloading data...");
            Stream<Movie> movies = new RemoteStore(MovieDeserializer::fromTsv).movies();
            new DatabaseRecorder(connection).record(movies);
            connection.commit();
            System.out.println("Data imported!");
        }
    }
}
