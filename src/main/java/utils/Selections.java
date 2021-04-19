package utils;

public enum Selections {
    // 1. Получить перечень и общее число поставщиков, поставляющих указанный вид
    //товара, либо некоторый товар в объеме, не менее заданного за весь период
    //сотрудничества, либо за указанный период.
    suppliers(new Select(
            "Get list and the total number of suppliers supplying the specified species goods,\n" +
                    "or some goods in a volume not less than specified for the entire period cooperation,\n" +
                    "or for a specified period",
            "/windows/select/supplies_sel_window.fxml")),

    // 3. Получить номенклатуру и объем товаров в указанной торговой точке.
    nomenclature(new Select(
            "Get the nomenclature and volume of goods at the specified outlet",
            "/windows/select/nomenclature_sel_window.fxml")),

    // 5. Получить данные о выработке на одного продавца за указанный период по всем торговым точкам, по торговым точкам заданного типа.
    //(если учитываем определенный тип)
    sellers(new Select("" +
            "Get data on the output per one seller for a specified period for all outlets, \n" +
            "for outlets of a given type",
            "/windows/select/sellers_sel_window.fxml"));

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
