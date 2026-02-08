package io.github.some_example_name.assets;

public class Victor extends Killer {

    public Victor(int idle) {
        super(AssetsManager.killerVictor, idle, "Victor");
        pistes = pistesVictor;
        dialogs = dialogsVictor;
    }

    public static final String[] dialogsVictor = {
        "Si alguna cosa anava a passar, preferiria estar allà i afrontar-lo, " +
            "que mirar cap a altre costat.",
        "Va ser una situació complicada per a tots, ningú sabia molt bé bé què fer."
    };

    public static final String[] pistesVictor = {
        "Fa molt de temps que són amics; malgrat això, hem arribat a aquesta situació. Va estar a prop de la zona.",
        "Quan apareix el perill, no s’amaga. El seu cor decideix abans que la seva ment i la seva valentia de vegades el fica en problemes.",
        "Viu tot amb intensitat: les emocions, les decisions i fins i tot els errors.",
        "Prefereix actuar encara que s’equivoqui abans que quedar-se de braços plegats."
    };
}
