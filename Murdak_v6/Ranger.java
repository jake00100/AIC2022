package Murdak_v6;

import aic2022.user.*;

public class Ranger extends CombatUnit {

    public Ranger (UnitController _uc) {
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

            abilityOne();

            move();

            attack();

            getShrine();

            getChest();

            useArtifact();

            //enterDungeon();

            uc.yield();
        }

    }

    @Override
    void move(){
        if(movement.doMicro() ) return;
        if(seekShrine())        return;
        if(reinforce() )        return;
        if(accumulate() )       return;
        movement.explore();
    }

    @Override
    void reportMyself() {
        // Report to the Comm Channel
        uc.writeOnSharedArray(data.unitReportCh, uc.readOnSharedArray(data.unitReportCh)+1);
        // Reset Next Slot
        uc.writeOnSharedArray(data.unitResetCh, 0);
        // Report to the Comm Channel
        uc.writeOnSharedArray(data.rangerReportCh, uc.readOnSharedArray(data.rangerReportCh)+1);
        // Reset Next Slot
        uc.writeOnSharedArray(data.rangerResetCh, 0);
    }

    @Override
    boolean seekShrine(){
        ShrineInfo[] shrines = uc.senseShrines();
        for (ShrineInfo shrine : shrines) {
            if(shrine.getOwner() == data.allyTeam) continue;;
            Location[] locs = uc.getVisibleLocations(36);
            for ( Location loc : locs ){
                if(!uc.isPassable(loc)) continue;
                if(loc.distanceSquared(shrine.getLocation() ) < uc.getType().getStat(UnitStat.MIN_ATTACK_RANGE)) continue;
                if(loc.distanceSquared(shrine.getLocation() ) > uc.getType().getStat(UnitStat.ATTACK_RANGE))     continue;
                if(!uc.isObstructed(loc, uc.getLocation() ) ) {
                    movement.moveTo(loc);
                    return true;
                }
            }
        }
        return false;
    }

    void abilityOne(){

        UnitInfo[] units = uc.senseUnits(36,data.allyTeam, true);

        for (UnitInfo u : units){
            if (u.getType() == UnitType.BARBARIAN || (u.getType() == UnitType.KNIGHT && u.getLevel() < 2) ){
                if(uc.getInfo().getLevel() < 2){
                    if(uc.canLevelUp() && uc.getReputation() > data.rangerLvlThreshold ) uc.levelUp();
                    return;
                }
                else if (uc.canUseFirstAbility( u.getLocation() ) ){
                    uc.useFirstAbility(u.getLocation());
                    return;
                }

            }

        }

    }






}
