package ua.com.alevel.hw2.service.reader;

import ua.com.alevel.hw2.model.manufacturingmaterial.ManufacturingMaterial;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class XMLReader extends MyFileReader {
    private static XMLReader instance;

    private XMLReader() {
    }

    public static XMLReader getInstance() {
        if (instance == null) {
            instance = new XMLReader();
        }

        return instance;
    }

    @Override
    protected Map<String, Object> parser(List<String> lines) {
        Map<String, Object> result = new HashMap<>();
        Pattern regexForAttributes = Pattern.compile("<(.*?)>");
        Pattern regexForValues = Pattern.compile("(<.+>(.*?)<.*>)");
        lines.remove(0);
        lines.remove(0);
        lines.remove(lines.size() - 1);

        for (int i = 0; i < lines.size(); i++) {
            Matcher matcherForAttribute = regexForAttributes.matcher(lines.get(i));
            Matcher matcherForValue = regexForValues.matcher(lines.get(i));

            if (lines.get(i).lastIndexOf("currency") != -1) {
                if (matcherForAttribute.find() && matcherForValue.find()) {
                    String[] splitStr = matcherForAttribute.group(1).split("=\"");
                    String currencyValue = splitStr[1];
                    currencyValue = currencyValue.substring(0, currencyValue.length() - 1);

                    result.put("currency", currencyValue);
                    result.put("price", Integer.parseInt(matcherForValue.group(2)));
                }
            }
            else if (lines.get(i).lastIndexOf("created") != -1) {
                if (matcherForValue.find()) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    try {
                        Date date = formatter.parse(matcherForValue.group(2));
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
                    Matcher matcherForAttribute2 = regexForAttributes.matcher(lines.get(i));
                    Matcher matcherForValue2 = regexForValues.matcher(lines.get(i));
                    if (matcherForAttribute2.find() && matcherForValue2.find()) {
                        mapForClass.put(matcherForAttribute2.group(1), matcherForValue2.group(2));
                    }
                }
                result.put("manufacturingMaterial", new ManufacturingMaterial(mapForClass.get("material"),
                        mapForClass.get("color")));
            }
            else {
                if (matcherForAttribute.find() && matcherForValue.find()) {
                    result.put(matcherForAttribute.group(1), matcherForValue.group(2));
                }
            }
        }

        return result;
    }
}
