```java
// File: src/com/example/unitconv/MeasurementType.java
package com.example.unitconv;

public enum MeasurementType {
    LENGTH,
    MASS,
    TEMPERATURE,
    VOLUME,
    TIME
}
```

```java
// File: src/com/example/unitconv/Unit.java
package com.example.unitconv;

import java.util.EnumSet;
import java.util.Set;

public enum Unit {
    // Length
    METER(MeasurementType.LENGTH, 1.0),
    KILOMETER(MeasurementType.LENGTH, 1000.0),
    CENTIMETER(MeasurementType.LENGTH, 0.01),
    MILLIMETER(MeasurementType.LENGTH, 0.001),
    MICROMETER(MeasurementType.LENGTH, 1e-6),
    NANOMETER(MeasurementType.LENGTH, 1e-9),
    MILE(MeasurementType.LENGTH, 1609.344),
    YARD(MeasurementType.LENGTH, 0.9144),
    FOOT(MeasurementType.LENGTH, 0.3048),
    INCH(MeasurementType.LENGTH, 0.0254),

    // Mass
    KILOGRAM(MeasurementType.MASS, 1.0),
    GRAM(MeasurementType.MASS, 0.001),
    MILLIGRAM(MeasurementType.MASS, 1e-6),
    MICROGRAM(MeasurementType.MASS, 1e-9),
    TONNE(MeasurementType.MASS, 1000.0),
    POUND(MeasurementType.MASS, 0.45359237),
    OUNCE(MeasurementType.MASS, 0.028349523125),

    // Temperature (base unit: Kelvin)
    KELVIN(MeasurementType.TEMPERATURE, 1.0),
    CELSIUS(MeasurementType.TEMPERATURE, 1.0),
    FAHRENHEIT(MeasurementType.TEMPERATURE, 1.0),

    // Volume
    LITER(MeasurementType.VOLUME, 1.0),
    MILLILITER(MeasurementType.VOLUME, 0.001),
    CUBIC_METER(MeasurementType.VOLUME, 1000.0),
    GALLON_US(MeasurementType.VOLUME, 3.785411784),
    QUART_US(MeasurementType.VOLUME, 0.946352946),
    PINT_US(MeasurementType.VOLUME, 0.473176473),

    // Time
    SECOND(MeasurementType.TIME, 1.0),
    MILLISECOND(MeasurementType.TIME, 0.001),
    MICROSECOND(MeasurementType.TIME, 1e-6),
    MINUTE(MeasurementType.TIME, 60.0),
    HOUR(MeasurementType.TIME, 3600.0),
    DAY(MeasurementType.TIME, 86400.0);

    private final MeasurementType type;
    private final double toBaseFactor; // factor to convert to base unit (except temperature)

    Unit(MeasurementType type, double toBaseFactor) {
        this.type = type;
        this.toBaseFactor = toBaseFactor;
    }

    public MeasurementType getType() {
        return type;
    }

    public double getToBaseFactor() {
        return toBaseFactor;
    }

    public static Set<Unit> ofType(MeasurementType type) {
        EnumSet<Unit> set = EnumSet.noneOf(Unit.class);
        for (Unit u : values()) {
            if (u.type == type) set.add(u);
        }
        return set;
    }
}
```

```java
// File: src/com/example/unitconv/ConversionException.java
package com.example.unitconv;

public class ConversionException extends RuntimeException {
    public ConversionException(String message) {
        super(message);
    }
}
```

```java
// File: src/com/example/unitconv/UnitConverter.java
package com.example.unitconv;

public final class UnitConverter {

    private UnitConverter() { }

    // Generic conversion for all types except temperature
    public static double convert(double value, Unit from, Unit to) {
        if (from.getType() != to.getType()) {
            throw new ConversionException(
                String.format("Cannot convert between different measurement types: %s -> %s",
                    from.getType(), to.getType()));
        }

        if (from.getType() == MeasurementType.TEMPERATURE) {
            return convertTemperature(value, from, to);
        }

        // Convert to base unit then to target
        double inBase = value * from.getToBaseFactor();
        return inBase / to.getToBaseFactor();
    }

    // Temperature conversion (base unit Kelvin)
    private static double convertTemperature(double value, Unit from, Unit to) {
        double kelvin = toKelvin(value, from);
        return fromKelvin(kelvin, to);
    }

    private static double toKelvin(double value, Unit from) {
        switch (from) {
            case KELVIN:
                return value;
            case CELSIUS:
                return value + 273.15;
            case FAHRENHEIT:
                return (value - 32) * 5.0 / 9.0 + 273.15;
            default:
                throw new ConversionException("Unsupported temperature unit: " + from);
        }
    }

    private static double fromKelvin(double kelvin, Unit to) {
        switch (to) {
            case KELVIN:
                return kelvin;
            case CELSIUS:
                return kelvin - 273.15;
            case FAHRENHEIT:
                return (kelvin - 273.15) * 9.0 / 5.0 + 32;
            default:
                throw new ConversionException("Unsupported temperature unit: " + to);
        }
    }

    // Convenience overloads
    public static double convert(double value, String fromSymbol, String toSymbol) {
        Unit from = UnitSymbol.lookup(fromSymbol);
        Unit to = UnitSymbol.lookup(toSymbol);
        return convert(value, from, to);
    }
}
```

```java
// File: src/com/example/unitconv/UnitSymbol.java
package com.example.unitconv;

import java.util.HashMap;
import java.util.Map;

public final class UnitSymbol {

    private static final Map<String, Unit> SYMBOL_MAP = new HashMap<>();

    static {
        // Length
        SYMBOL_MAP.put("m", Unit.METER);
        SYMBOL_MAP.put("km", Unit.KILOMETER);
        SYMBOL_MAP.put("cm", Unit.CENTIMETER);
        SYMBOL_MAP.put("mm", Unit.MILLIMETER);
        SYMBOL_MAP.put("µm", Unit.MICROMETER);
        SYMBOL_MAP.put("nm", Unit.NANOMETER);
        SYMBOL_MAP.put("mi", Unit.MILE);
        SYMBOL_MAP.put("yd", Unit.YARD);
        SYMBOL_MAP.put("ft", Unit.FOOT);
        SYMBOL_MAP.put("in", Unit.INCH);

        // Mass
        SYMBOL_MAP.put("kg", Unit.KILOGRAM);
        SYMBOL_MAP.put("g", Unit.GRAM);
        SYMBOL_MAP.put("mg", Unit.MILLIGRAM);
        SYMBOL_MAP.put("µg", Unit.MICROGRAM);
        SYMBOL_MAP.put("t", Unit.TONNE);
        SYMBOL_MAP.put("lb", Unit.POUND);
        SYMBOL_MAP.put("oz", Unit.OUNCE);

        // Temperature
        SYMBOL_MAP.put("K", Unit.KELVIN);
        SYMBOL_MAP.put("°C", Unit.CELSIUS);
        SYMBOL_MAP.put("C", Unit.CELSIUS);
        SYMBOL_MAP.put("°F", Unit.FAHRENHEIT);
        SYMBOL_MAP.put("F", Unit.FAHRENHEIT);

        // Volume
        SYMBOL_MAP.put("L", Unit.LITER);
        SYMBOL_MAP.put("l", Unit.LITER);
        SYMBOL_MAP.put("mL", Unit.MILLILITER);
        SYMBOL_MAP.put("ml", Unit.MILLILITER);
        SYMBOL_MAP.put("m³", Unit.CUBIC_METER);
        SYMBOL_MAP.put("m3", Unit.CUBIC_METER);
        SYMBOL_MAP.put("gal", Unit.GALLON_US);
        SYMBOL_MAP.put("qt", Unit.QUART_US);
        SYMBOL_MAP.put("pt", Unit.PINT_US);

        // Time
        SYMBOL_MAP.put("s", Unit.SECOND);
        SYMBOL_MAP.put("ms", Unit.MILLISECOND);
        SYMBOL_MAP.put("µs", Unit.MICROSECOND);
        SYMBOL