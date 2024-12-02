import java.util.Scanner;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;

interface Alarm {
    void setAlarm(String time);
    void showAlarm();
}

abstract class Weekday implements Alarm {
    String time;

    @Override
    public void setAlarm(String time) {
        this.time = time;
    }

    @Override
    public abstract void showAlarm();
}

public class Monday extends Weekday {

    @Override
    public void showAlarm() {
        try {
            LocalTime alarm = LocalTime.parse(time);
            
            System.out.println("\nSystem Time Zone: " + ZoneId.systemDefault());
            
            ZoneId zoneId = ZoneId.of("Asia/Manila");
            ZonedDateTime zonedNow = ZonedDateTime.now(zoneId);
            LocalTime now = zonedNow.toLocalTime();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
            System.out.println("Current time: " + now.format(formatter));
            System.out.println("Alarm set for: " + alarm.format(formatter));
            
            if (alarm.isBefore(now)) {
                System.out.println("\nAlarm is set for tomorrow!");
            } else {
                System.out.println("\nI'll wake you up later!");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format! Please enter time in HH:MM format.\n");
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String alarmTime;
            
            while (true) {
                System.out.print("Enter time for alarm in this format (HH:MM): ");
                alarmTime = scanner.nextLine();
                
                if (alarmTime.matches("\\d{2}:\\d{2}")) {
                    String[] timeParts = alarmTime.split(":");
                    int hours = Integer.parseInt(timeParts[0]);
                    int minutes = Integer.parseInt(timeParts[1]);
                    if (hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59) {
                        break;
                    } else {
                        System.out.println("Invalid time input! Hours should be between 00 and 23, and minutes between 00 and 59.\n");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid time in HH:MM format.\n");
                }
            }
            
            Monday monday = new Monday();
            monday.setAlarm(alarmTime);
            monday.showAlarm();
        }
    }
}