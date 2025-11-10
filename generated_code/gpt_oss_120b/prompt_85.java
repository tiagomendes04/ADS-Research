```java
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class CalendarApp {

    public static void main(String[] args) {
        CalendarService service = new CalendarService();
        Scanner scanner = new Scanner(System.in);
        printHelp();

        while (true) {
            System.out.print("\n> ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split("\\s+", 2);
            String cmd = parts[0].toLowerCase();

            try {
                switch (cmd) {
                    case "add":
                        service.addEvent(parseEvent(parts[1]));
                        System.out.println("Event added.");
                        break;
                    case "list":
                        List<EventInstance> instances = service.getUpcomingInstances(LocalDate.now(), 30);
                        if (instances.isEmpty()) System.out.println("No upcoming events.");
                        else instances.forEach(System.out::println);
                        break;
                    case "remind":
                        service.checkReminders();
                        break;
                    case "help":
                        printHelp();
                        break;
                    case "exit":
                        System.out.println("Goodbye.");
                        return;
                    default:
                        System.out.println("Unknown command. Type 'help' for commands.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void printHelp() {
        System.out.println("Commands:");
        System.out.println("  add <title>|<yyyy-MM-dd HH:mm>|<durationMinutes>|<reminderMinutesBefore>|<recurrence>");
        System.out.println("       Recurrence format: NONE|DAILY|WEEKLY|MONTHLY|YEARLY[:count|until=yyyy-MM-dd]");
        System.out.println("  list                - show upcoming events (next 30 days)");
        System.out.println("  remind              - show due reminders");
        System.out.println("  help                - this help");
        System.out.println("  exit                - quit");
    }

    private static Event parseEvent(String input) {
        String[] fields = input.split("\\|");
        if (fields.length < 5) throw new IllegalArgumentException("Insufficient fields for event.");

        String title = fields[0].trim();
        LocalDateTime start = LocalDateTime.parse(fields[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        int duration = Integer.parseInt(fields[2].trim());
        int reminderBefore = Integer.parseInt(fields[3].trim());
        Recurrence recurrence = Recurrence.parse(fields[4].trim());

        return new Event(title, start, Duration.ofMinutes(duration), reminderBefore, recurrence);
    }

    // ---------- Service ----------
    static class CalendarService {
        private final List<Event> events = new ArrayList<>();

        void addEvent(Event e) {
            events.add(e);
        }

        List<EventInstance> getUpcomingInstances(LocalDate from, int daysAhead) {
            LocalDate to = from.plusDays(daysAhead);
            List<EventInstance> result = new ArrayList<>();
            for (Event e : events) {
                result.addAll(e.generateInstances(from.atStartOfDay(), to.atTime(LocalTime.MAX)));
            }
            result.sort(Comparator.comparing(i -> i.start));
            return result;
        }

        void checkReminders() {
            LocalDateTime now = LocalDateTime.now();
            List<EventInstance> due = new ArrayList<>();
            for (Event e : events) {
                due.addAll(e.generateInstances(now.minusDays(1), now.plusDays(1)).stream()
                        .filter(inst -> !inst.reminded
                                && !inst.start.isBefore(now)
                                && inst.start.minusMinutes(e.reminderMinutesBefore).isBefore(now))
                        .collect(Collectors.toList()));
            }
            if (due.isEmpty()) {
                System.out.println("No reminders due.");
                return;
            }
            for (EventInstance inst : due) {
                System.out.println("[REMINDER] " + inst.title + " at " + inst.start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                inst.reminded = true;
            }
        }
    }

    // ---------- Domain ----------
    static class Event {
        final String title;
        final LocalDateTime start;
        final Duration duration;
        final int reminderMinutesBefore;
        final Recurrence recurrence;

        Event(String title, LocalDateTime start, Duration duration, int reminderMinutesBefore, Recurrence recurrence) {
            this.title = title;
            this.start = start;
            this.duration = duration;
            this.reminderMinutesBefore = reminderMinutesBefore;
            this.recurrence = recurrence;
        }

        List<EventInstance> generateInstances(LocalDateTime from, LocalDateTime to) {
            List<EventInstance> list = new ArrayList<>();
            LocalDateTime occurrence = start;

            int generated = 0;
            while (!occurrence.isAfter(to)) {
                if (!occurrence.isBefore(from)) {
                    list.add(new EventInstance(title, occurrence, duration));
                }
                generated++;
                if (recurrence.isFinite() && generated >= recurrence.maxOccurrences) break;
                occurrence = recurrence.next(occurrence);
                if (recurrence.until != null && (occurrence.isAfter(recurrence.until))) break;
            }
            return list;
        }
    }

    static class EventInstance {
        final String title;
        final LocalDateTime start;
        final Duration duration;
        boolean reminded = false;

        EventInstance(String title, LocalDateTime start, Duration duration) {
            this.title = title;
            this.start = start;
            this.duration = duration;
        }

        @Override
        public String toString() {
            return String.format("%s | %s | %d min",
                    title,
                    start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    duration.toMinutes());
        }
    }

    // ---------- Recurrence ----------
    static class Recurrence {
        final Frequency frequency;
        final Integer maxOccurrences; // null = infinite unless until set
        final LocalDate until; // null = no end date

        Recurrence(Frequency frequency, Integer maxOccurrences, LocalDate until) {
            this.frequency = frequency;
            this.maxOccurrences = maxOccurrences;
            this.until = until;
        }

        static Recurrence parse(String str) {
            if (str.equalsIgnoreCase("NONE")) return new Recurrence(Frequency.NONE, null, null);
            String[] parts = str.split(":", 2);
            Frequency freq = Frequency.valueOf(parts[0].toUpperCase());
            Integer count = null;
            LocalDate until = null;
            if (parts.length > 1) {
                for (String opt : parts[1].split(",")) {
                    if (opt.startsWith("count=")) count = Integer.parseInt(opt.substring(6));
                    else if (opt.startsWith("until=")) until = LocalDate.parse(opt.substring(6));
                }
            }
            return new Recurrence(freq, count, until);
        }

        boolean isFinite() {
            return maxOccurrences != null || until != null;
        }

        LocalDateTime next(LocalDateTime current) {
            switch (frequency) {
                case DAILY:
                    return current.plusDays(1);
                case WEEKLY:
                    return current.plusWeeks(1);
                case MONTHLY:
                    return current.plusMonths(1);
                case YEARLY:
                    return current.plusYears(1);
                case NONE:
                default:
                    return current.plusYears(1000); // effectively no next
            }
        }

        enum Frequency { NONE, DAILY, WEEKLY, MONTHLY, YEARLY }
    }
}
```