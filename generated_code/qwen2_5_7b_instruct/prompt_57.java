```java
import java.util.HashMap;
import java.util.Map;

public class UnitConverter {

    private static final Map<String, Map<String, Double>> conversionFactors = new HashMap<>();

    static {
        initConversionFactors();
    }

    private static void initConversionFactors() {
        Map<String, Double> lengthFactors = new HashMap<>();
        lengthFactors.put("m", 1.0);
        lengthFactors.put("cm", 100.0);
        lengthFactors.put("mm", 1000.0);
        lengthFactors.put("in", 0.0254);
        lengthFactors.put("ft", 0.3048);
        lengthFactors.put("yd", 0.9144);
        lengthFactors.put("mi", 1609.34);
        conversionFactors.put("length", lengthFactors);

        Map<String, Double> massFactors = new HashMap<>();
        massFactors.put("kg", 1.0);
        massFactors.put("g", 1000.0);
        massFactors.put("mg", 1000000.0);
        massFactors.put("lb", 2.20462);
        massFactors.put("oz", 35.274);
        conversionFactors.put("mass", massFactors);

        Map<String, Double> temperatureFactors = new HashMap<>();
        temperatureFactors.put("C", 1.0);
        temperatureFactors.put("F", 1.8);
        temperatureFactors.put("K", 273.15);
        conversionFactors.put("temperature", temperatureFactors);
    }

    public static double convert(String fromUnit, String toUnit, double value) {
        if (!conversionFactors.containsKey(fromUnit.split(" ")[0]) || !conversionFactors.containsKey(toUnit.split(" ")[0])) {
            throw new IllegalArgumentException("Unsupported unit type");
        }

        String fromType = fromUnit.split(" ")[0];
        String toType = toUnit.split(" ")[0];
        double fromFactor = conversionFactors.get(fromType).get(fromUnit.split(" ")[1]);
        double toFactor = conversionFactors.get(toType).get(toUnit.split(" ")[1]);

        return value * (toFactor / fromFactor);
    }
}
```