```java
import java.util.HashMap;
import java.util.Map;

class UnitConverter {

    private static final Map<String, Map<String, Double>> unitConversion = new HashMap<>();

    static {
        unitConversion.put("meter", new HashMap<>() {{
            put("kilometers", 1000.0);
            put("feet", 3.28084);
            put("inches", 30.48);
        }});
        unitConversion.put("kilogram", new HashMap<>() {{
            put("grams", 1000.0);
            put("pounds", 2.20462);
            put("ounces", 35.274);
        }});
        unitConversion.put("liter", new HashMap<>() {{
            put("milliliters", 1.0);
            put("gallons", 3.78541);
            put("cubic meters", 0.001);
        }});
    }

    public static double convert(String fromUnit, String toUnit, double value) {
        if (fromUnit.isEmpty() || toUnit.isEmpty()) {
            throw new IllegalArgumentException("Invalid unit");
        }
        Map<String, Double> fromUnits = unitConversion.get(fromUnit);
        if (fromUnits == null) {
            throw new IllegalArgumentException("Invalid unit from");
        }
        Map<String, Double> toUnits = unitConversion.get(toUnit);
        if (toUnits == null) {
            throw new IllegalArgumentException("Invalid unit to");
        }
        double result = value * fromUnits.get(toUnit) / toUnits.get(fromUnit);
        return result;
    }

}
```