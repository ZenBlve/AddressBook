import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private final List<Contact> contacts = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        boolean running = true;

        while (running) {
            printMenu();
            switch (prompt("Choose an option: ")) {
                case "1" -> addContact();
                case "2" -> editContact();
                case "3" -> deleteContact();
                case "4" -> viewContact();
                case "5" -> listContacts();
                case "6" -> searchContacts();
                case "7" -> filterContacts();
                case "8" -> reports();
                case "9" -> saveToFile();
                case "10" -> loadFromFile();
                case "0" -> running = false;
                default -> System.out.println("Invalid choice.");
}
        }

        System.out.println("Goodbye.");
    }

    private void printMenu() {
    System.out.println();
    System.out.println("=== Address Book ===");
    System.out.println("1. Add contact");
    System.out.println("2. Edit contact");
    System.out.println("3. Delete contact");
    System.out.println("4. View contact");
    System.out.println("5. List all contacts");
    System.out.println("6. Search contacts");
    System.out.println("7. Filter contacts");
    System.out.println("8. Reports");
    System.out.println("9. Save to file");
    System.out.println("10. Load from file");
    System.out.println("0. Exit");
}

    private void addContact() {
    Contact contact = new Contact();

    contact.type = chooseType();
    contact.name = promptRequired("Name: ");
    contact.email = prompt("Email: ");
    contact.phone = prompt("Phone: ");
    contact.city = prompt("City: ");

    String tagsInput = prompt("Tags (comma separated): ");
    if (!tagsInput.isBlank()) {
        contact.tags.addAll(List.of(tagsInput.split(",")));
        }

    contacts.add(contact);

    System.out.println("Contact added.");
    }

    private void editContact() {
        Contact contact = selectContact("edit");
        if (contact == null) {
            return;
        }

        System.out.println("Press Enter to keep the current value.");

        String type = prompt("Type (" + contact.type + "): ");
        if (!type.isBlank()) {
            contact.type = normalizeType(type);
        }

        contact.name = updateField("Name", contact.name);
        contact.email = updateField("Email", contact.email);
        contact.phone = updateField("Phone", contact.phone);
        contact.city = updateField("City", contact.city);

        System.out.println("Contact updated.");
    }

    private void deleteContact() {
        Contact contact = selectContact("delete");
        if (contact == null) {
            return;
        }

        contacts.remove(contact);
        System.out.println("Contact deleted.");
    }

    private void viewContact() {
        Contact contact = selectContact("view");
        if (contact != null) {
            System.out.println(contact.details());
        }
    }

    private void listContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            System.out.printf("%d. %s [%s]%n", i + 1, contact.name, contact.type);
        }
    }

    private Contact selectContact(String action) {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return null;
        }

        listContacts();
        int index = parseIndex(prompt("Enter contact number to " + action + ": "));
        if (index < 0 || index >= contacts.size()) {
            System.out.println("Invalid contact number.");
            return null;
        }

        return contacts.get(index);
    }

    private String chooseType() {
        System.out.println("Types: Person, Business, Vendor, Emergency");
        return normalizeType(promptRequired("Type: "));
    }

    private String normalizeType(String value) {
        return switch (value.trim().toLowerCase(Locale.ROOT)) {
            case "business" -> "Business";
            case "vendor" -> "Vendor";
            case "emergency" -> "Emergency";
            default -> "Person";
        };
    }

    private String updateField(String label, String currentValue) {
        String newValue = prompt(label + " (" + currentValue + "): ");
        return newValue.isBlank() ? currentValue : newValue;
    }

    private int parseIndex(String value) {
        try {
            return Integer.parseInt(value.trim()) - 1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private String promptRequired(String label) {
        String value;
        do {
            value = prompt(label);
        } while (value.isBlank());

        return value;
    }

    private String prompt(String label) {
        System.out.print(label);
        return scanner.nextLine().trim();
    }

    private void searchContacts() {

        String keyword = prompt("Search by name/email/phone: ")
            .toLowerCase();

        boolean found = false;

        for (Contact contact : contacts) {

            if (contact.name.toLowerCase().contains(keyword)
                || contact.email.toLowerCase().contains(keyword)
                || contact.phone.toLowerCase().contains(keyword)) {

            System.out.println(contact.details());
            found = true;
            }
        }

        if (!found) {
            System.out.println("No matching contacts.");
        }
    }

    private void filterContacts() {

        System.out.println("Filter by:");
        System.out.println("1. Type");
        System.out.println("2. City");
        System.out.println("3. Tag");

        String choice = prompt("Choose: ");

        boolean found = false;

        switch (choice) {

            case "1" -> {
                String type = prompt("Type: ");

                for (Contact c : contacts) {
                    if (c.type.equalsIgnoreCase(type)) {
                        System.out.println(c.details());
                        found = true;
                    }
                }
            }

            case "2" -> {
                String city = prompt("City: ");

                for (Contact c : contacts) {
                    if (c.city.equalsIgnoreCase(city)) {
                        System.out.println(c.details());
                        found = true;
                    }
                }
            }

            case "3" -> {
                String tag = prompt("Tag: ");

                for (Contact c : contacts) {
                    for (String t : c.tags) {

                        if (t.trim().equalsIgnoreCase(tag)) {
                            System.out.println(c.details());
                            found = true;
                        }
                    }
                }
            }

            default -> System.out.println("Invalid filter.");
        }

        if (!found) {
            System.out.println("No matching contacts.");
        }
    }

    private static class Contact {
    String type = "Person";
    String name = "";
    String email = "";
    String phone = "";
    String city = "";

    List<String> tags = new ArrayList<>();

    String details() {
        return """
                Name: %s
                Type: %s
                Email: %s
                Phone: %s
                City: %s
                Tags: %s
                """.formatted(
                name,
                type,
                email,
                phone,
                city,
                tags
            );
        }
    }
}
