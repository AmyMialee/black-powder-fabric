package amymialee.blackpowder;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = BlackPowder.MODID)
public class BlackPowderConfig implements ConfigData {
    public int FlintlockDamage = 16;
    public int FlintlockReloadTime = 50;
    public int FlintlockQuickChargeTime = 5;
    public float FlintlockInaccuracy = 7;
    public int BlunderbussDamage = 4;
    public int BlunderbussReloadTime = 160;
    public int BlunderbussQuickChargeTime = 20;
    public float BlunderbussInaccuracy = 14;
    public int RifleDamage = 22;
    public int RifleReloadTime = 160;
    public int RifleQuickChargeTime = 20;
    public float RifleInaccuracy = 2;
    public int MusketDamage = 26;
    public int MusketReloadTime = 100;
    public int MusketQuickChargeTime = 10;
    public float MusketInaccuracy = 4;

    public int FlintlockCarbineDamage = 12;
    public int FlintlockCarbineReloadTime = 3;
    public int FlintlockCarbineQuickChargeTime = 1;
    public float FlintlockCarbineInaccuracy = 7;
    public int BlunderbehemothDamage = 4;
    public int BlunderbehemothReloadTime = 320;
    public int BlunderbehemothQuickChargeTime = 40;
    public float BlunderbehemothInaccuracy = 28;
    public int ResoluteRifleDamage = 22;
    public int ResoluteRifleReloadTime = 240;
    public int ResoluteRifleQuickChargeTime = 40;
    public float ResoluteRifleInaccuracy = 0;
    public int BoundlessMusketDamage = 318;
    public int BoundlessMusketReloadTime = 200;
    public int BoundlessMusketQuickChargeTime = 20;
    public float BoundlessMusketInaccuracy = 4;

    public boolean funMode = false;
}
