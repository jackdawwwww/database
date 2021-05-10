package utils;

public enum Roles {
    accounting(new Role(
            "accounting",
            "/windows/roles/accounting_main_window.fxml")),
    admin(new Role(
            "admin",
            "/windows/roles/admin_main_window.fxml")),
    client(new Role(
            "client",
            "/windows/roles/client_main_window.fxml")),
    manager(new Role(
            "manager",
            "/windows/roles/manager_main_window.fxml")),
    seller(new Role(
            "manager",
            "/windows/roles/seller_main_window.fxml")),
    sellerManager(new Role(
            "manager",
            "/windows/roles/sellers_manager_main_window.fxml"));

    private Role role;

    Roles(Role role) {
        this.role = role;
    }

    public String getWindowName() {
        return role.getWindowName();
    }

    String getName() {
        return role.getName();
    }

    public static Roles getRoleByName(String name) {
        for (Roles role: values()) {
            if (role.getName().equals(name)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No enum found with name: [" + name + "]");
    }
}

class Role {

    private final String name;
    private final String windowName;

    Role(String name, String windowName) {
        this.name = name;
        this.windowName = windowName;
    }

    String getName() {
        return name;
    }

    String getWindowName() {
        return windowName;
    }
}