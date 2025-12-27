package software.ulpgc.kata6.application;

import com.google.gson.Gson;
import software.ulpgc.kata6.architecture.viewmodel.Histogram;
import software.ulpgc.kata6.architecture.viewmodel.HistogramBuilder;

import java.util.HashMap;
import java.util.Map;

public class HistogramAdapter {
    private static String toJason(Histogram histogram){
        Map<String, Object> response = new HashMap<>();
        response.put("title", histogram.title());
        response.put("x_axis", histogram.x());
        response.put("y_axis", histogram.y());

        Map<String, Integer> data = new HashMap<>();
        for(Integer key:histogram){
            data.put(String.valueOf(key), histogram.count(key));
        }
        response.put("data", data);

        return new Gson().toJson(response);
    }
}