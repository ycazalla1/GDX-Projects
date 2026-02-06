package io.github.some_example_name.assets;

public class Victor extends Killer {

    public Victor(int idle) {
        super(AssetsManager.killerVictor, idle, "Victor");
        pistes = pistesVictor;

    }

    public static final String[] pistesVictor = {
        "Fa molt de temps que són amics; malgrat això, hem arribat a aquesta situació. Va estar a prop de la zona.",
        "Quan apareix el perill, no s’amaga. El seu cor decideix abans que la seva ment i la seva valentia de vegades el fica en problemes.",
        "Viu tot amb intensitat: les emocions, les decisions i fins i tot els errors.",
        "Prefereix actuar encara que s’equivoqui abans que quedar-se de braços plegats."
    };
}
