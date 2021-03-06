package Murdak;

import aic2022.user.*;

public class Data {

    UnitController uc;
    Tools tools;

    //GENERAL INFO
    Direction[] dirs = Direction.values();
    int ID;                     UnitType type;
    Location allyBase, enemyBase; int baseCode = 100; //the first position where we store our map
    Team allyTeam;              Team enemyTeam;
    int currentRound;           int spawnRound;                 int turnsAlive;

    int x;                      int y;                          int z;

    //CHARACTER SPECIFIC INFO
    Direction prefDir;

    //UNITS CHANNELS
    int unitCh,             unitReportCh,           unitResetCh;            // Ch 0, 1, 2
    int scoutCh,            scoutReportCh,          scoutResetCh;           // Ch 3, 4, 5
    int barbarianCh,        barbarianReportCh,      barbarianResetCh;       // Ch 6, 7, 8
    int rangerCh,           rangerReportCh,         rangerResetCh;          // Ch 9, 10, 11
    int mageCh,             mageReportCh,           mageResetCh;            // Ch 12, 13, 14
    int knightCh,           knightReportCh,         knightResetCh;          // Ch 15, 16, 17
    int assassinCh,         assassinReportCh,       assassinResetCh;        // Ch 18, 19, 20
    int clericCh,           clericReportCh,         clericResetCh;          // Ch 21, 22, 23

    // Unit Count Info
    int nUnits,     nScouts,      nBarbarians,     nRangers;
    int nMages,     nKnights,     nAssassins,      nClerics;

    // Map info
    public class Tile{
        boolean negativecoords;
        TileType tileType;

    }
    // Shrines info
    int[] shrineCh = {24,44};

    // Dungeons info
    int[] dungeonCh = {45, 85};


    public Data (UnitController _uc) {
        uc = _uc;
        tools = new Tools(uc, this);
        ID = uc.getInfo().getID();
        type = uc.getType();
        allyTeam = uc.getTeam();
        enemyTeam = uc.getOpponent();
        currentRound = uc.getRound();

        //Explorer initialization
        prefDir = tools.randomDir();
    }

    // This function is called once per turn
    public void update() {

        // General updates
        updateGeneral();
        updateChannels();
        updateUnitCountInfo();

        // Resource updates
        //updateShrines();

        // Army updates
        //updateEnemyIntel();

        // Class specific updates
        //updateBase();
    }

    void updateGeneral() {
        currentRound = uc.getRound();
        turnsAlive = currentRound - spawnRound;
        //Rw = allyTeam.getVictoryPoints();
        x = currentRound%3;
        y = (currentRound+1)%3;
        z = (currentRound+2)%3;
    }

    void updateChannels() {
        unitReportCh     = x;       scoutReportCh         = 3 + x;    barbarianReportCh       = 6 + x;
        unitResetCh      = y;       scoutResetCh          = 3 + y;    barbarianResetCh        = 6 + y;
        unitCh           = z;       scoutCh               = 3 + z;    barbarianCh             = 6 + z;

        rangerReportCh   = 9 + x;   mageReportCh         = 12 + x;    knightReportCh         = 15 + x;
        rangerResetCh    = 9 + y;   mageResetCh          = 12 + y;    knightResetCh          = 15 + y;
        rangerCh         = 9 + z;   mageCh               = 12 + z;    knightCh               = 15 + z;

        assassinReportCh = 18 + x;  clericReportCh       = 21 + x;
        assassinResetCh  = 18 + y;  clericResetCh        = 21 + y;
        assassinCh       = 21 + z;  clericCh             = 24 + z;
    }

    void updateUnitCountInfo() {
        nUnits                   = uc.readOnSharedArray(unitCh);
        nScouts                  = uc.readOnSharedArray(scoutCh);
        nBarbarians              = uc.readOnSharedArray(barbarianCh);
        nRangers                 = uc.readOnSharedArray(rangerCh);
        nMages                   = uc.readOnSharedArray(mageCh);
        nKnights                 = uc.readOnSharedArray(knightCh);
        nAssassins               = uc.readOnSharedArray(assassinCh);
        nClerics                 = uc.readOnSharedArray(clericCh);
    }

    // Assuming coordinates have less than 3 digits
    int locationCode(Location loc) {
        int code = loc.y - allyBase.y + (loc.x - allyBase.x) * 100 ;   // between -6400 and 6400, "xxyy"
        if (code < 0) code = 6400 - code;
        return code + baseCode;
    }
    Location getLocation (int code){
        code = code - baseCode;
        // input should be "xxyy", after baseCode subtraction
        int relative_y = (code) % 100;
        int relative_x = (code) / 100;
        return allyBase.add(relative_x,relative_y);


    }

    int saveShrine(ShrineInfo s) {
        Location loc = s.getLocation();
        int distance = Math.abs(allyBase.x - loc.x) + Math.abs(allyBase.y - loc.y);

        //TODO
        return distance;


    }
    int saveShrine(Location loc){
        //TODO
        return 0;
    }

    void saveChest (ChestInfo c){
        //TODO
    }
    void saveDungeon (Location loc){
        //TODO
    }

}
