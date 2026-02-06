package io.github.some_example_name.assets;

public class Vera extends Killer{

    public Vera(int idle) {
        super(AssetsManager.killerVera, idle, "Vera");
        pistes = pistesVera;

    }

    public static final String[] pistesVera = {
        "Fa molt de temps que són amics; malgrat això, hem arribat a aquesta situació. Va estar a prop de la zona.",
        "No actua sense pensar. Cada pas està calculat i sempre avalua els riscos abans de decidir.",
        "Parla poc i observa molt. Cada moviment té un propòsit i la seva ambició avança sense cridar l’atenció.",
        "Sovint guarda informació per a ella mateixa. Mai no és clar què sap realment ni quines són les seves veritables intencions."
    };

}
