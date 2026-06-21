package software.ulpgc.kata7.application;

import com.google.gson.Gson;
import software.ulpgc.kata7.architecture.viewmodel.Histogram;

import java.util.HashMap;
import java.util.Map;

public class HistogramAdapter {
    public static String toJson(Histogram histogram){
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