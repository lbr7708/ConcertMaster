package team_f.client.singletons;

import team_f.client.controls.Legende.LegendTable;


/**
 * Created by w7pro on 28.04.2017.
 */
public class LegendSingleton {
    private static LegendTable _legendTable;

    private LegendSingleton() {
    }

    public static LegendTable getInstance() {
        if(_legendTable == null) {
            _legendTable = new LegendTable();
        }

        return _legendTable;
    }
}
