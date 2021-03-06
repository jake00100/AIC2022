package Murdak_v4;

import aic2022.user.UnitController;
import aic2022.user.UnitInfo;

public class Knight extends CombatUnit {

    public Knight(UnitController _uc) {
        this.uc = _uc;
        this.data = new Data(uc);
        this.tools = new Tools(uc, data);
        this.movement = new Movement(uc, data);
    }

    void run() {

        while (true) {

            data.update();

            report();

            attack();

            move();

            attack();

            getShrine();

            getChest();

            useArtifact();

            //enterDungeon();

            uc.yield();
        }

    }


    void abilityOne(){
        //TODO
    }

}