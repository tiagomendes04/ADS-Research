```java
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;

/**
 * CSV parsing utility with validation and detailed error reporting.
 */
public class CSVParser {

    /** Result of a parsing operation. */
    public static class Result {
        private final List<String[]> records = new ArrayList<>();
        private final List<ParseError> errors = new ArrayList<>();

        public List<String[]> getRecords() { return records; }
        public List<ParseError> getErrors() { return errors; }

        public boolean hasErrors() { return !errors.isEmpty(); }
    }

    /** Validation error for a specific line. */
    public static class ParseError {
        private final long lineNumber;
        private final String message;
        private final String rawLine;

        public ParseError(long lineNumber, String message, String rawLine) {
            this.lineNumber = lineNumber;
            this.message = message;
            this.rawLine = rawLine;
        }

        public long getLineNumber() { return lineNumber; }
        public String getMessage() { return message; }
        public String getRawLine() { return rawLine; }

        @Override
        public String toString() {
            return String.format("Line %d: %s (raw: \"%s\")", lineNumber, message, rawLine);
        }
    }

    /** Simple column validator. */
    @FunctionalInterface
    public interface ColumnValidator {
        /**
         * Validates a column value.
         *
         * @param columnIndex zero‑based index of the column
         * @param value       raw string value
         * @return null if valid, otherwise an error message
         */
        String validate(int columnIndex, String value);
    }

    /**
     * Parses a CSV file.
     *
     * @param file               CSV file to read
     * @param delimiter          delimiter character (e.g. ',')
     * @param quoteChar          quote character (e.g. '"')
     * @param expectedColumns    expected number of columns per row (use -1 to skip check)
     * @param columnValidators   optional per‑column validators (size may be less than expectedColumns)
     * @return parsing result containing records and errors
     * @throws IOException if file cannot be read
     */
    public Result parse(File file,
                        char delimiter,
                        char quoteChar,
                        int expectedColumns,
                        List<ColumnValidator> columnValidators) throws IOException {

        Result result = new Result();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            String line;
            long lineNumber = 0;
            StringBuilder pending = new StringBuilder();
            boolean insideQuote = false;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                // Handle multiline quoted fields
                if (insideQuote) {
                    pending.append('\n').append(line);
                    if (endsQuote(line, quoteChar)) {
                        insideQuote = false;
                        line = pending.toString();
                        pending.setLength(0);
                    } else {
                        continue; // keep reading
                    }
                } else if (startsQuote(line, quoteChar) && !endsQuote(line, quoteChar)) {
                    insideQuote = true;
                    pending.append(line);
                    continue;
                }

                // Normal processing
                List<String> fields = splitLine(line, delimiter, quoteChar);
                if (expectedColumns >= 0 && fields.size() != expectedColumns) {
                    result.errors.add(new ParseError(lineNumber,
                            String.format("Expected %d columns but got %d", expectedColumns, fields.size()),
                            line));
                    continue;
                }

                // Validation
                if (columnValidators != null) {
                    for (int i = 0; i < columnValidators.size() && i < fields.size(); i++) {
                        ColumnValidator validator = columnValidators.get(i);
                        if (validator != null) {
                            String err = validator.validate(i, fields.get(i));
                            if (err != null) {
                                result.errors.add(new ParseError(lineNumber,
                                        String.format("Column %d validation failed: %s", i + 1, err),
                                        line));
                            }
                        }
                    }
                }

                result.records.add(fields.toArray(new String[0]));
            }

            if (insideQuote) {
                result.errors.add(new ParseError(lineNumber,
                        "Unterminated quoted field at end of file", pending.toString()));
            }
        }
        return result;
    }

    /** Splits a CSV line respecting quotes. */
    private List<String> splitLine(String line, char delimiter, char quoteChar) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == quoteChar) {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == quoteChar) {
                    // escaped quote
                    sb.append(quoteChar);
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == delimiter && !inQuotes) {
                tokens.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        tokens.add(sb.toString());
        return tokens;
    }

    private boolean startsQuote(String s, char quoteChar) {
        return !s.isEmpty() && s.charAt(0) == quoteChar;
    }

    private boolean endsQuote(String s, char quoteChar) {
        int len = s.length();
        if (len == 0) return false;
        int back = len - 1;
        // ignore escaped quotes at the end
        while (back > 0 && s.charAt(back) == quoteChar && s.charAt(back - 1) == quoteChar) {
            back -= 2;
        }
        return s.charAt(back) == quoteChar;
    }

    /** Convenience factory for common validators. */
    public static class Validators {
        public static ColumnValidator integer() {
            return (idx, val) -> {
                try {
                    Integer.parseInt(val.trim());
                    return null;
                } catch (NumberFormatException e) {
                    return "not an integer";
                }
            };
        }

        public static ColumnValidator decimal() {
            return (idx, val) -> {
                try {
                    Double.parseDouble(val.trim());
                    return null;
                } catch (NumberFormatException e) {
                    return "not a decimal number";
                }
            };
        }

        public static ColumnValidator nonEmpty() {
            return (idx, val) -> val == null || val.trim().isEmpty() ? "empty value" : null;
        }

        public static ColumnValidator regex(Pattern pattern, String description) {
            return (idx, val) -> pattern.matcher(val).matches() ? null : "does not match " + description;
        }

        public static ColumnValidator custom(Predicate<String> predicate, String errorMessage) {
            return (idx, val) -> predicate.test(val) ? null : errorMessage;
        }
    }

    /** Example usage (remove or adapt for production). */
    public static void main(String[] args) throws Exception {
        File csv = new File("sample.csv");
        CSVParser parser = new CSVParser();

        List<ColumnValidator> validators = Arrays.asList(
                Validators.nonEmpty(),          // column 1 must not be empty
                Validators.integer(),           // column 2 must be integer
                Validators.decimal()            // column 3 must be decimal
        );

        Result res = parser.parse(csv, ',', '"', 3, validators);

        System.out.println("Parsed records:");
        for (String[] rec : res.getRecords()) {
            System.out.println(Arrays.toString(rec));
        }

        if (res.hasErrors()) {
            System.out.println("\nErrors:");
            for (ParseError err : res.getErrors()) {
                System.out.println(err);
            }
        }
    }
}
```