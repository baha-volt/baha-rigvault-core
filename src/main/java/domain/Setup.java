package domain;

public class Setup {
    private Long id;
    private String name;
    private String description;
    private RigUser rigUser;

    public Setup() {
    }

    public Setup(Long id, String name, String description, RigUser rigUser) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rigUser = rigUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RigUser getUser() {
        return rigUser;
    }

    public void setUser(RigUser rigUser) {
        this.rigUser = rigUser;
    }

    @Override
    public String toString() {
        return "Setup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", user=" + rigUser +
                '}';
    }
}
