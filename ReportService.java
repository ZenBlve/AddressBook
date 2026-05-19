import java.util.ArrayList;
import java.util.List;

public class ReportService {
    private final AddressBook book;

    public ReportService(AddressBook book) {
        this.book = book;
    }

    public void reportByType() {
        System.out.println("Person contacts:");
        printContactsOfType("Person");

        System.out.println("Business contacts:");
        printContactsOfType("Business");

        System.out.println("Vendor contacts:");
        printContactsOfType("Vendor");

        System.out.println("Emergency contacts:");
        printContactsOfType("Emergency");
    }

    public void reportMissingInfo() {
        boolean found = false;

        for (Contact contact : book.getContacts()) {
            if (contact.getEmail().isBlank() || contact.getPhone().isBlank()) {
                System.out.println(contact.details());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No contacts missing email or phone.");
        }
    }

    public void reportGroups() {
        List<String> groups = new ArrayList<>();

        for (Contact contact : book.getContacts()) {
            String group = displayGroup(contact.getGroup());
            if (!groups.contains(group)) {
                groups.add(group);
            }
        }

        for (String group : groups) {
            int count = 0;

            for (Contact contact : book.getContacts()) {
                if (displayGroup(contact.getGroup()).equals(group)) {
                    count++;
                }
            }

            System.out.println(group + ": " + count + " contact(s)");
        }
    }

    private void printContactsOfType(String type) {
        boolean found = false;

        for (Contact contact : book.getContacts()) {
            if (contact.getType().equalsIgnoreCase(type)) {
                System.out.println(contact.details());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No contacts found.");
        }
    }

    private static String displayGroup(String group) {
        return group.isBlank() ? "No Group" : group;
    }
}
