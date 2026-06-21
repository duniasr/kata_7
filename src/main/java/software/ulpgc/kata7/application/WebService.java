package software.ulpgc.kata7.application;

import io.javalin.Javalin;
import io.javalin.http.Context;
import software.ulpgc.kata7.architecture.io.Store;
import software.ulpgc.kata7.architecture.model.Movie;
import software.ulpgc.kata7.architecture.viewmodel.Histogram;
import software.ulpgc.kata7.architecture.viewmodel.HistogramBuilder;

import java.util.function.Function;

public class WebService {
    private final Store store;
    private final Javalin app;

    public WebService(Store store) {
        this.store = store;
        this.app = Javalin.create();
        this.app.get("/histogram", this::histogram);
    }

    public void start(){
        app.start(8080);
    }

    private void histogram(Context ctx){
        String attribute = ctx.queryParam("attribute");
        Histogram histogram = generateHistogram(attribute);
        ctx.result(HistogramAdapter.toJson(histogram));
    }

    private Histogram generateHistogram(String attribute) {
        return HistogramBuilder.with(store.movies()).title("Movies by " + attribute).x(attribute).y("Count").use(fieldExtractor(attribute));
    }

    private Function<Movie, Integer> fieldExtractor(String attribute) {
        return switch (attribute!= null ? attribute.toLowerCase() : ""){
            case "duration" -> Movie::duration;
            case "year" -> Movie::year;
            default -> Movie::year;
        };
    }
}