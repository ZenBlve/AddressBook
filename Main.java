import java.io.IOException;
import java.util.Scanner;

public class Main {
    private final AddressBook book = new AddressBook();
    private final ContactStorage storage = new ContactStorage("contacts.csv");
    private final ContactSearch search = new ContactSearch(book);
    private final ReportService reports = new ReportService(book);
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
                case "8" -> showReports();
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

        contact.setType(chooseType());
        contact.setName(promptRequired("Name: "));
        contact.setEmail(prompt("Email: "));
        contact.setPhone(prompt("Phone: "));
        contact.setCity(prompt("City: "));
        contact.setGroup(prompt("Group: "));
        contact.getTags().addAll(AddressBook.parseTags(prompt("Tags (comma separated): ")));

        book.add(contact);
        System.out.println("Contact added.");
    }

    private void editContact() {
        Contact contact = selectContact("edit");
        if (contact == null) {
            return;
        }

        System.out.println("Press Enter to keep the current value.");

        String type = prompt("Type (" + contact.getType() + "): ");
        if (!type.isBlank()) {
            contact.setType(AddressBook.normalizeType(type));
        }

        contact.setName(updateField("Name", contact.getName()));
        contact.setEmail(updateField("Email", contact.getEmail()));
        contact.setPhone(updateField("Phone", contact.getPhone()));
        contact.setCity(updateField("City", contact.getCity()));
        contact.setGroup(updateField("Group", contact.getGroup()));

        String tagsInput = prompt("Tags (" + contact.getTags() + "): ");
        if (!tagsInput.isBlank()) {
            contact.getTags().clear();
            contact.getTags().addAll(AddressBook.parseTags(tagsInput));
        }

        System.out.println("Contact updated.");
    }

    private void deleteContact() {
        Contact contact = selectContact("delete");
        if (contact == null) {
            return;
        }

        book.remove(contact);
        System.out.println("Contact deleted.");
    }

    private void viewContact() {
        Contact contact = selectContact("view");
        if (contact != null) {
            System.out.println(contact.details());
        }
    }

    private void listContacts() {
        if (book.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        for (int i = 0; i < book.size(); i++) {
            Contact contact = book.get(i);
            System.out.printf("%d. %s [%s]%n", i + 1, contact.getName(), contact.getType());
        }
    }

    private Contact selectContact(String action) {
        if (book.isEmpty()) {
            System.out.println("No contacts available.");
            return null;
        }

        listContacts();
        int index = parseIndex(prompt("Enter contact number to " + action + ": "));
        if (index < 0 || index >= book.size()) {
            System.out.println("Invalid contact number.");
            return null;
        }

        return book.get(index);
    }

    private String chooseType() {
        System.out.println("Types: Person, Business, Vendor, Emergency");
        return AddressBook.normalizeType(promptRequired("Type: "));
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
        search.searchByKeyword(prompt("Search by name/email/phone: "));
    }

    private void filterContacts() {
        System.out.println("Filter by:");
        System.out.println("1. Type");
        System.out.println("2. City");
        System.out.println("3. Tag");
        System.out.println("4. Group");

        switch (prompt("Choose: ")) {
            case "1" -> search.filterByType(prompt("Type: "));
            case "2" -> search.filterByCity(prompt("City: "));
            case "3" -> search.filterByTag(prompt("Tag: "));
            case "4" -> search.filterByGroup(prompt("Group: "));
            default -> System.out.println("Invalid filter.");
        }
    }

    private void showReports() {
        System.out.println("Reports:");
        System.out.println("1. List contacts by type");
        System.out.println("2. Show contacts missing email or phone");
        System.out.println("3. Display group summaries");

        switch (prompt("Choose: ")) {
            case "1" -> reports.reportByType();
            case "2" -> reports.reportMissingInfo();
            case "3" -> reports.reportGroups();
            default -> System.out.println("Invalid report.");
        }
    }

    private void saveToFile() {
        try {
            storage.save(book);
            System.out.println("Contacts saved.");
        } catch (IOException e) {
            System.out.println("Could not save contacts.");
        }
    }

    private void loadFromFile() {
        try {
            storage.load(book);
            System.out.println("Contacts loaded.");
        } catch (IOException e) {
            System.out.println("No saved file found.");
        }
    }
}
