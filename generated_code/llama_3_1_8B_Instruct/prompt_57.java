```java
public class UnitConverter {

    // Length conversions
    public static double convertLength(double value, String fromUnit, String toUnit) {
        switch (fromUnit.toLowerCase()) {
            case "meters":
                switch (toUnit.toLowerCase()) {
                    case "meters":
                        return value;
                    case "kilometers":
                        return value / 1000;
                    case "centimeters":
                        return value * 100;
                    case "millimeters":
                        return value * 1000;
                    case "inches":
                        return value * 39.37;
                    case "feet":
                        return value * 3.28084;
                    case "yards":
                        return value * 1.09361;
                    case "miles":
                        return value / 1609.34;
                }
            case "kilometers":
                switch (toUnit.toLowerCase()) {
                    case "kilometers":
                        return value;
                    case "meters":
                        return value * 1000;
                    case "centimeters":
                        return value * 100000;
                    case "millimeters":
                        return value * 1000000;
                    case "inches":
                        return value * 39370;
                    case "feet":
                        return value * 3280.84;
                    case "yards":
                        return value * 1093.61;
                    case "miles":
                        return value / 1.60934;
                }
            case "centimeters":
                switch (toUnit.toLowerCase()) {
                    case "centimeters":
                        return value;
                    case "meters":
                        return value / 100;
                    case "kilometers":
                        return value / 100000;
                    case "millimeters":
                        return value / 10;
                    case "inches":
                        return value * 0.393701;
                    case "feet":
                        return value * 0.0328084;
                    case "yards":
                        return value * 0.0109361;
                    case "miles":
                        return value / 160934;
                }
            case "millimeters":
                switch (toUnit.toLowerCase()) {
                    case "millimeters":
                        return value;
                    case "meters":
                        return value / 1000;
                    case "kilometers":
                        return value / 1000000;
                    case "centimeters":
                        return value * 10;
                    case "inches":
                        return value * 0.0393701;
                    case "feet":
                        return value * 0.00328084;
                    case "yards":
                        return value * 0.00109361;
                    case "miles":
                        return value / 1609340;
                }
            case "inches":
                switch (toUnit.toLowerCase()) {
                    case "inches":
                        return value;
                    case "meters":
                        return value / 39.37;
                    case "kilometers":
                        return value / 39370;
                    case "centimeters":
                        return value / 0.393701;
                    case "millimeters":
                        return value / 0.0393701;
                    case "feet":
                        return value * 0.0833333;
                    case "yards":
                        return value * 0.0277778;
                    case "miles":
                        return value / 63360;
                }
            case "feet":
                switch (toUnit.toLowerCase()) {
                    case "feet":
                        return value;
                    case "meters":
                        return value / 3.28084;
                    case "kilometers":
                        return value / 3280.84;
                    case "centimeters":
                        return value / 0.0328084;
                    case "millimeters":
                        return value / 0.00328084;
                    case "inches":
                        return value * 12;
                    case "yards":
                        return value * 0.333333;
                    case "miles":
                        return value / 5280;
                }
            case "yards":
                switch (toUnit.toLowerCase()) {
                    case "yards":
                        return value;
                    case "meters":
                        return value / 1.09361;
                    case "kilometers":
                        return value / 1093.61;
                    case "centimeters":
                        return value / 0.0109361;
                    case "millimeters":
                        return value / 0.00109361;
                    case "inches":
                        return value * 36;
                    case "feet":
                        return value * 3;
                    case "miles":
                        return value / 1760;
                }
            case "miles":
                switch (toUnit.toLowerCase()) {
                    case "miles":
                        return value;
                    case "meters":
                        return value * 1609.34;
                    case "kilometers":
                        return value * 1.60934;
                    case "centimeters":
                        return value * 160934;
                    case "millimeters":
                        return value * 1609340;
                    case "inches":
                        return value * 63360;
                    case "feet":
                        return value * 5280;
                    case "yards":
                        return value * 1760;
                }
        }
        throw new IllegalArgumentException("Unsupported unit");
    }

    // Mass conversions
    public static double convertMass(double value, String fromUnit, String toUnit) {
        switch (fromUnit.toLowerCase()) {
            case "grams":
                switch (toUnit.toLowerCase()) {
                    case "grams":
                        return value;
                    case "kilograms":
                        return value / 1000;
                    case "milligrams":
                        return value * 1000;
                    case "micrograms":
                        return value * 1000000;
                    case "tons":
                        return value / 1000000;
                }
            case "kilograms":
                switch (toUnit.toLowerCase()) {
                    case "kilograms":
                        return value;
                    case "grams":
                        return value * 1000;
                    case "milligrams":
                        return value * 1000000;
                    case "micrograms":
                        return value * 1000000000;
                    case "tons":
                        return value / 1000;
                }
            case "milligrams":
                switch (toUnit.toLowerCase()) {
                    case "milligrams":
                        return value;
                    case "grams":
                        return value / 1000;
                    case "kilograms":
                        return value / 1000000;
                    case "micrograms":
                        return value / 1000;
                    case "tons":
                        return value / 1000000000;
                }
            case "micrograms":
                switch (toUnit.toLowerCase()) {
                    case "micrograms":
                        return value;
                    case "grams":
                        return value / 1000000;
                    case "kilograms":
                        return value / 1000000000;
                    case "milligrams":
                        return value * 1000;
                    case "tons":
                        return value / 1000000000000;
                }
            case "tons":
                switch (toUnit.toLowerCase()) {
                    case "tons":
                        return value;
                    case "grams":
                        return value * 1000000;
                    case "kilograms":
                        return value * 1000;
                    case "milligrams":
                        return value * 1000000000;
                    case "micrograms":
                        return value * 1000000000000;
                }
        }
        throw new IllegalArgumentException("Unsupported unit");
    }

    // Temperature conversions
    public static double convertTemperature(double value, String fromUnit, String toUnit) {
        switch (fromUnit.toLowerCase()) {
            case "celsius":
                switch (toUnit.toLowerCase()) {
                    case "celsius":
                        return value;
                    case "fahrenheit":
                        return (value * 9 / 5) + 32;
                    case "kelvin":
                        return value + 273.15;
                }
            case "fahrenheit":
                switch (toUnit.toLowerCase()) {
                    case "fahrenheit":
                        return value;
                    case "celsius":
                        return (value - 32) * 5 / 9;
                    case "kelvin":
                        return (value - 32) * 5 / 9 + 273.15;
                }
            case "kelvin":
                switch (toUnit.toLowerCase()) {
                    case "kelvin":
                        return value;
                    case "celsius":
                        return value - 273.15;
                    case "fahrenheit":
                        return (value - 273.15) * 9 / 5 + 32;
                }
        }
        throw new IllegalArgumentException("Unsupported unit");
    }

    // Time conversions
    public static double convertTime(double value, String fromUnit, String toUnit) {
        switch (fromUnit.toLowerCase()) {
            case "seconds":
                switch (toUnit.toLowerCase()) {
                    case "seconds":
                        return value;
                    case "minutes":
                        return value / 60;
                    case "hours":
                        return value / 3600;
                    case "days":
                        return value / 86400;
                    case "weeks":
                        return value / 604800;
                    case "years":
                        return value / 31536000;
                }
            case "minutes":
                switch (toUnit.toLowerCase()) {
                    case "minutes":
                        return value;
                    case "seconds":
                        return value * 60;
                    case "hours":
                        return value / 60;
                    case "days":
                        return value / 1440;
                    case "weeks":
                        return value / 10080;
                    case "years":
                        return value / 525600;
                }
            case "hours":
                switch (toUnit.toLowerCase()) {
                    case "hours":
                        return value;
                    case "seconds":
                        return value * 3600;
                    case "minutes":
                        return value * 60;
                    case "days":
                        return value / 24;
                    case "weeks":
                        return value / 168;
                    case "years":
                        return value / 8760;
                }
            case "days":
                switch (toUnit.toLowerCase()) {
                    case "days":
                        return value;
                    case "seconds":
                        return value * 86400;
                    case "minutes":
                        return value * 1440;
                    case "hours":
                        return value * 24;
                    case "weeks":
                        return value / 7;
                    case "years":
                        return value / 365;
                }
            case "weeks":
                switch (toUnit.toLowerCase()) {
                    case "weeks":
                        return value;
                    case "seconds":
                        return value * 604800;
                    case "minutes":
                        return value * 10080;
                    case "hours":
                        return value * 168;
                    case "days":
                        return value * 7;
                    case "years":
                        return value / 52.18;
                }
            case "years":
                switch (toUnit.toLowerCase()) {
                    case "years":
                        return value;
                    case "seconds":
                        return value * 31536000;
                    case "minutes":
                        return value * 525600;
                    case "hours":
                        return value * 8760;
                    case "days":
                        return value * 365;
                    case "weeks":
                        return value * 52.18;
                }
        }
        throw new IllegalArgumentException("Unsupported unit");
    }

    public static void main(String[] args) {
        System.out.println(UnitConverter.convertLength(100, "meters", "kilometers")); // 0.1
        System.out.println(UnitConverter.convertMass(100, "grams", "kilograms")); // 0.1
        System.out.println(UnitConverter.convertTemperature(100, "celsius", "fahrenheit")); // 212.0
        System.out.println(UnitConverter.convertTime(100, "seconds", "minutes")); // 1.6666666666666667
    }
}
```