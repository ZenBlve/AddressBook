import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ContactStorage {
    private final String fileName;

    public ContactStorage(String fileName) {
        this.fileName = fileName;
    }

    public void save(AddressBook book) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Contact contact : book.getContacts()) {
                writer.println(
                        contact.getType() + "," +
                        contact.getName() + "," +
                        contact.getEmail() + "," +
                        contact.getPhone() + "," +
                        contact.getCity() + "," +
                        contact.getGroup() + "," +
                        String.join(";", contact.getTags())
                );
            }
        }
    }

    public void load(AddressBook book) throws IOException {
        File file = new File(fileName);

        if (!file.exists()) {
            throw new IOException("File not found");
        }

        book.clear();

        try (Scanner fileReader = new Scanner(file)) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(",", -1);

                if (parts.length >= 7) {
                    Contact contact = new Contact();
                    contact.setType(parts[0]);
                    contact.setName(parts[1]);
                    contact.setEmail(parts[2]);
                    contact.setPhone(parts[3]);
                    contact.setCity(parts[4]);
                    contact.setGroup(parts[5]);

                    if (!parts[6].isBlank()) {
                        for (String tag : parts[6].split(";")) {
                            contact.getTags().add(tag);
                        }
                    }

                    book.add(contact);
                }
            }
        }
    }
}
