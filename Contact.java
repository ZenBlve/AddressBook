import java.util.ArrayList;
import java.util.List;

public class Contact {
    private String type = "Person";
    private String name = "";
    private String email = "";
    private String phone = "";
    private String city = "";
    private String group = "";
    private final List<String> tags = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<String> getTags() {
        return tags;
    }

    public String details() {
        return """
                Name: %s
                Type: %s
                Email: %s
                Phone: %s
                City: %s
                Group: %s
                Tags: %s
                """.formatted(name, type, email, phone, city, group, tags);
    }
}
