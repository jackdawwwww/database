package utils;

public enum Tables {
    goods(new Table(
            "GOODS",
            "/windows/insertWindows/goods_insert_window.fxml")),
    tradePoints(new Table(
            "TRADE_POINTS",
            "/windows/insertWindows/points_insert_window.fxml")),
    lotki(new Table(
            "Лотки",
            "/windows/insertWindows/lotki_insert_window.fxml")),
    kioski(new Table(
            "Киоски",
            "/windows/insertWindows/kioski_insert_window.fxml")),
    shops(new Table(
            "Магазины",
            "/windows/insertWindows/shops_insert_window.fxml")),
    univermags(new Table(
            "Универмаги",
            "/windows/insertWindows/univermags_insert_window.fxml")),
    tradeTypes(new Table(
            "TRADE_TYPES",
            "/windows/insertWindows/types_insert_window.fxml")),
    tradeRoom(new Table(
            "TRADE_ROOM",
            "/windows/insertWindows/room_insert_window.fxml"));

    private Table table;

    Tables(Table table) {
        this.table = table;
    }

    public String getWindowName() {
        return table.getWindowName();
    }

    String getName() {
        return table.getName();
    }

    public static Tables getTableByName(String name) {
        for (Tables table: values()) {
            if (table.getName().equals(name)) {
                return table;
            }
        }
        throw new IllegalArgumentException("No enum found with name: [" + name + "]");
    }
}

class Table {

    private final String name;
    private final String windowName;

    Table(String name, String windowName) {
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