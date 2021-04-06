package controller.base;

public enum Tables {
    goods("GOODS", "/windows/insertWindows/goods_insert_window.fxml"),
    tradePoints("TRADE_POINTS", "/windows/insertWindows/points_insert_window.fxml"),
    tradeTypes("TRADE_TYPES", "/windows/insertWindows/types_insert_window.fxml"),
    tradeRoom("TRADE_ROOM", "/windows/insertWindows/room_insert_window.fxml");

    private String name;
    private String windowName;

    Tables(String name, String windowName) {
        this.name = name;
        this.windowName = windowName;
    }

    public String getWindowName() {
        return windowName;
    }

    String getName() {
        return name;
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
