package utils;

public enum Selections {
    suppliers(new Select(
            "Получить перечень и общее число поставщиков, поставляющих указанный вид\n" +
                    "товара, либо некоторый товар в объеме, не менее заданного за весь период\n" +
                    "сотрудничества, либо за указанный период.",
            "/windows/select/supplies_sel_window.fxml")),

    nomenclature(new Select(
            "Получить номенклатуру и объем товаров в указанной торговой точке.",
            "/windows/select/nomenclature_sel_window.fxml")),

    sellers(new Select("" +
            "Получить данные о выработке на одного продавца за указанный период по всем\n" +
            "торговым точкам, по торговым точкам заданного типа.",
            "/windows/select/sellers_sel_window.fxml")),

    prices(new Select("Получить сведения об объеме и ценах на указанный товар по всем торговым точкам, по торговым точкам заданного типа, по конкретной торговой точке",
            "/windows/select/prices_sel_window.fxml")),

    salary(new Select("Получить данные о заработной плате продавцов по всем торговым точкам, по торговым точкам заданного типа, по конкретной торговой точке",
            "/windows/select/salary_sel_window.fxml")),

    clients(new Select("Получить сведения о покупателях указанного товара за обозначенный, либо за весь период, по всем торговым точкам, по торговым точкам указанного типа, по данной торговой точке",
            "/windows/select/clients_sel_window.fxml"));

    private final Select select;

    Selections(Select select) {
        this.select = select;
    }

    public String getWindowName() {
        return select.getWindowName();
    }

    public String getName() {
        return select.getName();
    }

    public static Selections getSelectionByName(String name) {
        for (Selections sel: values()) {
            if (sel.getName().equals(name)) {
                return sel;
            }
        }
        throw new IllegalArgumentException("No enum found with name: [" + name + "]");
    }
}


class Select {

    private final String name;
    private final String windowName;

    Select(String name, String windowName) {
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
