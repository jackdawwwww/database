package utils;

import java.util.HashMap;
import java.util.Map;

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
    provider(new Role(
            "provider",
            "/windows/roles/provider_main_window.fxml")),
    reqManager(new Role(
            "req_manager",
            "/windows/roles/manager_main_window.fxml"));
//    seller(new Role(
//            "seller",
//            "/windows/roles/seller_main_window.fxml")),
//    sellerManager(new Role(
//            "seller_manager",
//            "/windows/roles/sellers_manager_main_window.fxml"));

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

    public static Map<String, Roles> getRoles() {
        Map<String, Roles> roles = new HashMap<>();
        roles.put("Бухгалтер", Roles.accounting);
        roles.put("Поставщик", Roles.provider);
        roles.put("Клиент", Roles.client);
        roles.put("Админ", Roles.admin);
    //    roles.put("Продавец", Roles.seller);
        roles.put("Менеджер по поставкам", Roles.reqManager);
    //    roles.put("Менеджер по персоналу", Roles.sellerManager);
        return roles;
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