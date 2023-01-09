package xyz.amymialee.blackpowder.util;

public interface IPlayerEntityHolding {
    void blackPowder$onPress();
    void blackPowder$setHeld(boolean held);
    boolean blackPowder$getHeld();
    void blackPowder$setHeldTime(int heldTime);
    int blackPowder$getHeldTime();
}