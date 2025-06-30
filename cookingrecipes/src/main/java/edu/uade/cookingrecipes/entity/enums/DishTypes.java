package edu.uade.cookingrecipes.entity.enums;

public enum DishTypes {
    ENTRADA("Entrada"),
    PLATO_PRINCIPAL("Plato principal"),
    POSTRE("Postre"),
    GUARNICION("Guarnición"),
    ENSALADA("Ensalada"),
    SOPA("Sopa"),
    PAN("Pan"),
    PASTA("Pasta"),
    ARROZ("Arroz"),
    PESCADO("Pescado"),
    MARISCOS("Mariscos"),
    AVES("Aves"),
    CARNE("Carne"),
    VEGETARIANO("Vegetariano"),
    VEGANO("Vegano"),
    CELIACO("Celíaco"),
    DESAYUNO("Desayuno"),
    MERIENDA("Merienda"),
    SNACK("Snack"),
    BEBIDA("Bebida"),
    LICUADO("Licuado"),
    BATIDO("Batido"),
    COCTEL("Cóctel"),
    SALSA("Salsa"),
    ADEREZO("Aderezo"),
    OTRO("Otro"),
    TARTA("Tarta"),
    EMPANADA("Empanada"),
    PIZZA("Pizza"),
    HAMBURGUESA("Hamburguesa"),
    SANDWICH("Sándwich"),
    CEREAL("Cereal"),
    FRUTA("Fruta"),
    HELADO("Helado"),
    INFUSION("Infusión"),
    PICADA("Picada"),
    GUISO("Guiso"),
    ESTOFADO("Estofado"),
    CREMA("Crema"),
    WOK("Wok"),
    TAPAS("Tapas"),
    CURRY("Curry"),
    CROQUETA("Croqueta"),
    MOUSSE("Mousse"),
    BUDIN("Budín"),
    TORTA("Torta"),
    GALLETA("Galleta"),
    BIZCOCHUELO("Bizcochuelo"),
    MILANESA("Milanesa"),
    ROLL("Roll"),
    BROCHETA("Brocheta");

    private final String nombreAmigable;

    DishTypes(String nombreAmigable) {
        this.nombreAmigable = nombreAmigable;
    }

    public String getNombreAmigable() {
        return nombreAmigable;
    }
}
