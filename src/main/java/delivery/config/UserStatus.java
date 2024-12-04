package delivery.config;

public enum UserStatus {
    USER("일반 유저"),
    OWNER("사장님");

    private final String description;

    UserStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
