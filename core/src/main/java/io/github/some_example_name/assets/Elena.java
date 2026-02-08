package io.github.some_example_name.assets;

public class Elena extends Killer {

    public Elena(int idle) {
        super(AssetsManager.killerElena, idle, "Elena");
        pistes = pistesElena;
        dialogs = dialogsElena;
    }

    public static final String[] dialogsElena = {
        "Hi ha molts detalls solts; suposo que amb el temps tot s'entendrà millor.",
        "Hi ha detalls que ningú mira dues vegades, però solen ser els que més diuen."
    };

    public static final String[] pistesElena = {
        "Fa molt de temps que són amics; malgrat això, hem arribat a aquesta situació. Va estar a prop de la zona.",
        "Sempre està fent preguntes. Examina cada detall i gaudeix resolent el que altres passen per alt.",
        "La seva curiositat la porta a investigar encara que això pugui crear problemes.",
        "Sap connectar pistes i treure conclusions de manera ràpida i lògica."
    };
}
