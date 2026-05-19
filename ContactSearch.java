public class ContactSearch {
    private final AddressBook book;

    public ContactSearch(AddressBook book) {
        this.book = book;
    }

    public void searchByKeyword(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        boolean found = false;

        for (Contact contact : book.getContacts()) {
            if (contact.getName().toLowerCase().contains(lowerKeyword)
                    || contact.getEmail().toLowerCase().contains(lowerKeyword)
                    || contact.getPhone().toLowerCase().contains(lowerKeyword)) {
                System.out.println(contact.details());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching contacts.");
        }
    }

    public void filterByType(String type) {
        printMatching(contact -> contact.getType().equalsIgnoreCase(type));
    }

    public void filterByCity(String city) {
        printMatching(contact -> contact.getCity().equalsIgnoreCase(city));
    }

    public void filterByTag(String tag) {
        boolean found = false;

        for (Contact contact : book.getContacts()) {
            for (String currentTag : contact.getTags()) {
                if (currentTag.trim().equalsIgnoreCase(tag)) {
                    System.out.println(contact.details());
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("No matching contacts.");
        }
    }

    public void filterByGroup(String group) {
        printMatching(contact -> contact.getGroup().equalsIgnoreCase(group));
    }

    private void printMatching(java.util.function.Predicate<Contact> predicate) {
        boolean found = false;

        for (Contact contact : book.getContacts()) {
            if (predicate.test(contact)) {
                System.out.println(contact.details());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching contacts.");
        }
    }
}
