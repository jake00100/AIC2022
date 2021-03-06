package Murdak_v7;

import aic2022.user.UnitController;
import aic2022.user.UnitInfo;

public class Barbarian extends Murdak_v7.CombatUnit {

    public Barbarian (UnitController _uc) {
        this.uc = _uc;
        this.data = new Murdak_v7.Data(uc);
        this.tools = new Murdak_v7.Tools(uc, data);
        this.movement = new Murdak_v7.Movement(uc, data);
    }

    void run() {

        while (true) {

            if(uc.getInfo().getCurrentAttackCooldown() >10) uc.println(uc.getType() + " penalty");

            data.update();

            report();

            attack();

            //abilityOne();

            move();

            attack();

            //abilityOne();

            getShrine();

            getChest();

            useArtifact();

            //enterDungeon();

            uc.yield();
        }

    }

    @Override
    void reportMyself() {
        // Report to the Comm Channel
        uc.writeOnSharedArray(data.unitReportCh, uc.readOnSharedArray(data.unitReportCh)+1);
        // Reset Next Slot
        uc.writeOnSharedArray(data.unitResetCh, 0);
        // Report to the Comm Channel
        uc.writeOnSharedArray(data.barbarianReportCh, uc.readOnSharedArray(data.barbarianReportCh)+1);
        // Reset Next Slot
        uc.writeOnSharedArray(data.barbarianResetCh, 0);
    }


    void abilityOne(){

        UnitInfo[] enemies = uc.senseUnits(8,data.allyTeam, true);
        UnitInfo[] allies = uc.senseUnits(8,data.allyTeam);

        if(enemies.length > 3 && allies.length == 0) {
            if (uc.getInfo().getLevel() < 2) {
                if (uc.canLevelUp() && uc.getReputation() > data.barbarianLvlThreshold) uc.levelUp();
                else if (uc.canUseFirstAbility(uc.getLocation())) uc.useFirstAbility(uc.getLocation());

            } else if (uc.canUseFirstAbility(uc.getLocation())) uc.useFirstAbility(uc.getLocation());
        }

    }

}