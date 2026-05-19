import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddressBook {
    private final List<Contact> contacts = new ArrayList<>();

    public List<Contact> getContacts() {
        return contacts;
    }

    public void add(Contact contact) {
        contacts.add(contact);
    }

    public void remove(Contact contact) {
        contacts.remove(contact);
    }

    public void clear() {
        contacts.clear();
    }

    public boolean isEmpty() {
        return contacts.isEmpty();
    }

    public int size() {
        return contacts.size();
    }

    public Contact get(int index) {
        return contacts.get(index);
    }

    public static String normalizeType(String value) {
        return switch (value.trim().toLowerCase(Locale.ROOT)) {
            case "business" -> "Business";
            case "vendor" -> "Vendor";
            case "emergency" -> "Emergency";
            default -> "Person";
        };
    }

    public static List<String> parseTags(String input) {
        List<String> tags = new ArrayList<>();

        if (!input.isBlank()) {
            for (String part : input.split(",")) {
                String tag = part.trim();
                if (!tag.isBlank()) {
                    tags.add(tag);
                }
            }
        }

        return tags;
    }
}
