package ua.com.alevel.hw2.service.reader;

import ua.com.alevel.hw2.model.ManufacturingMaterial;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JSONReader extends MyFileReader {
    private static JSONReader instance;

    private JSONReader() {
    }

    public static JSONReader getInstance(){
        if(instance == null){
            instance = new JSONReader();
        }

        return instance;
    }

    @Override
    protected Map<String, Object> parser(List<String> lines) {
        Map<String, Object> result = new HashMap<>();
        Pattern regex = Pattern.compile("\"([^\"]+)\":[\"]*([^,^}^\"])[\"]*([^,^}^\"]+)");

        lines.removeIf(s -> s.trim().length() <= 2);

        for (int i = 0; i < lines.size(); i++) {
            Matcher matcher = regex.matcher(lines.get(i));

            if (lines.get(i).lastIndexOf("created") != -1) {
                if (matcher.find()) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    try {
                        Date date = formatter.parse(matcher.group(3));
                        result.put("created", date);
                    }
                    catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (lines.get(i).lastIndexOf("manufacturingMaterial") != -1) {
                int start = i;
                i++;
                Map<String, String> mapForClass = new HashMap<>();
                for (; i != start + 3; i++) {
                    Matcher matcher2 = regex.matcher(lines.get(i));
                    if (matcher2.find()) {
                        mapForClass.put(matcher2.group(1), matcher2.group(3));
                    }
                }
                i--;
                result.put("manufacturingMaterial", new ManufacturingMaterial(mapForClass.get("material"),
                        mapForClass.get("color")));
            }
            else {
                if (matcher.find()) {
                    result.put(matcher.group(1), matcher.group(3));
                }
            }
        }

        return result;
    }
}
