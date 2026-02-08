package io.github.some_example_name.assets;

public class Tobias extends Killer {

    public Tobias(int idle) {
        super(AssetsManager.killerTobias, idle, "Tobias");
        pistes = pistesTobias;
        dialogs = dialogsTobias;
    }

    public static final String[] dialogsTobias = {
        "No sóc de parlar molt, però sempre intento estar on més se'm necessiti.",
        "Cadascú ho viu a la seva manera; jo només vaig intentar ajudar en el que vaig poder."
    };

    public static final String[] pistesTobias = {
        "Fa molt de temps que són amics; malgrat això, hem arribat a aquesta situació. Va estar a prop de la zona.",
        "Prefereix el segon pla, però és qui mai no falla. La seva lleialtat és ferma encara que molts no se n’adonin.",
        "Observa en silenci i recorda més del que deixa veure.",
        "Quan és necessari, mostra que la seva força i constància sempre han estat presents, encara que ningú no l’esperés."
    };
}
