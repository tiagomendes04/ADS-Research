```java
package com.example.unitconverter;

/* -------------------------------------------------
 * MeasurementType.java
 * ------------------------------------------------- */
public enum MeasurementType {
    LENGTH,
    MASS,
    TEMPERATURE,
    VOLUME,
    TIME
}

/* -------------------------------------------------
 * Unit.java
 * ------------------------------------------------- */
public interface Unit {
    String getName();
    String getSymbol();
    MeasurementType getMeasurementType();

    /** Convert a value expressed in this unit to the base unit of its type */
    double toBase(double value);

    /** Convert a value expressed in the base unit of its type to this unit */
    double fromBase(double baseValue);
}

/* -------------------------------------------------
 * LengthUnit.java
 * ------------------------------------------------- */
public enum LengthUnit implements Unit {
    METER("Meter", "m", 1.0),
    KILOMETER("Kilometer", "km", 1000.0),
    CENTIMETER("Centimeter", "cm", 0.01),
    MILLIMETER("Millimeter", "mm", 0.001),
    MICROMETER("Micrometer", "µm", 1e-6),
    NANOMETER("Nanometer", "nm", 1e-9),
    MILE("Mile", "mi", 1609.344),
    YARD("Yard", "yd", 0.9144),
    FOOT("Foot", "ft", 0.3048),
    INCH("Inch", "in", 0.0254);

    private final String name;
    private final String symbol;
    private final double toMeterFactor; // multiply by this to get meters

    LengthUnit(String name, String symbol, double toMeterFactor) {
        this.name = name;
        this.symbol = symbol;
        this.toMeterFactor = toMeterFactor;
    }

    @Override public String getName() { return name; }
    @Override public String getSymbol() { return symbol; }
    @Override public MeasurementType getMeasurementType() { return MeasurementType.LENGTH; }

    @Override public double toBase(double value) { return value * toMeterFactor; }
    @Override public double fromBase(double baseValue) { return baseValue / toMeterFactor; }
}

/* -------------------------------------------------
 * MassUnit.java
 * ------------------------------------------------- */
public enum MassUnit implements Unit {
    KILOGRAM("Kilogram", "kg", 1.0),
    GRAM("Gram", "g", 0.001),
    MILLIGRAM("Milligram", "mg", 1e-6),
    MICROGRAM("Microgram", "µg", 1e-9),
    TONNE("Tonne", "t", 1000.0),
    POUND("Pound", "lb", 0.45359237),
    OUNCE("Ounce", "oz", 0.028349523125);

    private final String name;
    private final String symbol;
    private final double toKilogramFactor; // multiply by this to get kilograms

    MassUnit(String name, String symbol, double toKilogramFactor) {
        this.name = name;
        this.symbol = symbol;
        this.toKilogramFactor = toKilogramFactor;
    }

    @Override public String getName() { return name; }
    @Override public String getSymbol() { return symbol; }
    @Override public MeasurementType getMeasurementType() { return MeasurementType.MASS; }

    @Override public double toBase(double value) { return value * toKilogramFactor; }
    @Override public double fromBase(double baseValue) { return baseValue / toKilogramFactor; }
}

/* -------------------------------------------------
 * TemperatureUnit.java
 * ------------------------------------------------- */
public enum TemperatureUnit implements Unit {
    KELVIN("Kelvin", "K") {
        @Override public double toBase(double v) { return v; }
        @Override public double fromBase(double v) { return v; }
    },
    CELSIUS("Celsius", "°C") {
        @Override public double toBase(double v) { return v + 273.15; }
        @Override public double fromBase(double v) { return v - 273.15; }
    },
    FAHRENHEIT("Fahrenheit", "°F") {
        @Override public double toBase(double v) { return (v - 32) * 5.0/9.0 + 273.15; }
        @Override public double fromBase(double v) { return (v - 273.15) * 9.0/5.0 + 32; }
    },
    RANKINE("Rankine", "°R") {
        @Override public double toBase(double v) { return v * 5.0/9.0; }
        @Override public double fromBase(double v) { return v * 9.0/5.0; }
    };

    private final String name;
    private final String symbol;

    TemperatureUnit(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    @Override public String getName() { return name; }
    @Override public String getSymbol() { return symbol; }
    @Override public MeasurementType getMeasurementType() { return MeasurementType.TEMPERATURE; }

    @Override public abstract double toBase(double value);
    @Override public abstract double fromBase(double baseValue);
}

/* -------------------------------------------------
 * VolumeUnit.java
 * ------------------------------------------------- */
public enum VolumeUnit implements Unit {
    CUBIC_METER("Cubic Meter", "m³", 1.0),
    LITER("Liter", "L", 0.001),
    MILLILITER("Milliliter", "mL", 1e-6),
    GALLON_US("US Gallon", "gal", 0.003785411784),
    QUART_US("US Quart", "qt", 0.000946352946),
    PINT_US("US Pint", "pt", 0.000473176473),
    CUP_US("US Cup", "cup", 0.0002365882365),
    FLUID_OUNCE_US("US Fluid Ounce", "fl oz", 2.957352956e-5);

    private final String name;
    private final String symbol;
    private final double toCubicMeterFactor;

    VolumeUnit(String name, String symbol, double toCubicMeterFactor) {
        this.name = name;
        this.symbol = symbol;
        this.toCubicMeterFactor = toCubicMeterFactor;
    }

    @Override public String getName() { return name; }
    @Override public String getSymbol() { return symbol; }
    @Override public MeasurementType getMeasurementType() { return MeasurementType.VOLUME; }

    @Override public double toBase(double value) { return value * toCubicMeterFactor; }
    @Override public double fromBase(double baseValue) { return baseValue / toCubicMeterFactor; }
}

/* -------------------------------------------------
 * TimeUnit.java
 * ------------------------------------------------- */
public enum TimeUnit implements Unit {
    SECOND("Second", "s", 1.0),
    MILLISECOND("Millisecond", "ms", 0.001),
    MICROSECOND("Microsecond", "µs", 1e-6),
    NANOSECOND("Nanosecond", "ns", 1e-9),
    MINUTE("Minute", "min", 60.0),
    HOUR("Hour", "h", 3600.0),
    DAY("Day", "d", 86400.0);

    private final String name;
    private final String symbol;
    private final double toSecondFactor;

    TimeUnit(String name, String symbol, double toSecondFactor) {
        this.name = name;
        this.symbol = symbol;
        this.toSecondFactor = toSecondFactor;
    }

    @Override public String getName() { return name; }
    @Override public String getSymbol() { return symbol; }
    @Override public MeasurementType getMeasurementType() { return MeasurementType.TIME; }

    @Override public double toBase(double value) { return value * toSecondFactor; }
    @Override public double fromBase(double baseValue) { return baseValue / toSecondFactor; }
}

/* -------------------------------------------------
 * UnitConverter.java
 * ------------------------------------------------- */
public final class UnitConverter {

    private UnitConverter() { /* utility class */ }

    /**
     * Convert a numeric value from one unit to another.
     *
     * @param value the numeric value expressed in {@code fromUnit}
     * @param fromUnit the source unit
     * @param toUnit the target unit
     * @return the value expressed in {@code toUnit}
     * @throws IllegalArgumentException if the units belong to different measurement types
     */
    public static double convert(double value, Unit fromUnit, Unit toUnit) {
        if (fromUnit.getMeasurementType() != toUnit.getMeasurementType()) {
            throw new IllegalArgumentException(
                "Cannot convert between different measurement types: " +
                fromUnit.getMeasurementType() + " -> " + toUnit.getMeasurementType()
            );
        }
        double base = fromUnit.toBase(value);
        return toUnit.fromBase(base);
    }
}

/* -------------------------------------------------
 * Example usage (optional, remove if not needed)
 * ------------------------------------------------- */
// public class Demo {
//     public static void main(String[] args) {
//         double miles = 5.0;
//         double km = UnitConverter.convert(miles, LengthUnit.MILE, LengthUnit.KILOMETER);
//         System.out.println(miles + " mi = " + km + " km");
//
//         double fahrenheit = 98.6;
//         double celsius = UnitConverter.convert(fahrenheit, TemperatureUnit.FAHRENHEIT, TemperatureUnit.CELSIUS);
//         System.out.println(fahrenheit + " °F = " + celsius + " °C");
//
//         double gallons = 3.0;
//         double liters = UnitConverter.convert(gallons, VolumeUnit.GALLON_US, VolumeUnit.LITER);
//         System.out.println(gallons + " gal = " + liters + " L");
//     }
// }
```