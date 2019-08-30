package me.archen.owtranspiler.scriptsdk.types;

import me.archen.owtranspiler.scriptsdk.constant.*;
import me.archen.owtranspiler.scriptsdk.impl.Constants;
import me.archen.owtranspiler.scriptsdk.impl.MethodKey;
import me.archen.owtranspiler.scriptsdk.impl.MethodReturnValue;

public interface AbstractPlayer {

    @MethodKey(Constants.PLAYER_APPLY_IMPULSE)
    void applyImpulse(Vector direction, double speed, MovementType movementType, ImpulseType impulseType);

    @MethodKey(Constants.PLAYER_SET_FACING)
    void setFacing(Vector direction, MovementType movementType);

    @MethodKey(Constants.PLAYER_TELEPORT)
    void teleport(Vector location);

    @MethodKey(Constants.PLAYER_SET_MOVE_SPEED)
    void setMoveSpeed(int moveSpeedPercent);

    @MethodKey(Constants.PLAYER_SET_GRAVITY)
    void setGravity(int gravityPercent);

    @MethodKey(Constants.PLAYER_SET_PROJECTILE_GRAVITY)
    void setProjectileGravity(int projectileGravityPercent);

    @MethodKey(Constants.PLAYER_SET_SCORE)
    void setPlayerScore(int playerScore);

    @MethodKey(Constants.PLAYER_FORCE_PLAYER_HERO)
    void forcePlayerHero(Hero hero);

    @MethodKey(Constants.PLAYER_STOP_FORCING_PLAYER_HERO)
    void stopForcingPlayerHero();

    @MethodKey(Constants.PLAYER_SET_ALLOWED_HEROES)
    void setAllowedHeroes(Hero[] allowedHeroes);

    @MethodKey(Constants.PLAYER_RESET_ALLOWED_HEROES)
    void resetAllowedHeroes();

    @MethodKey(Constants.PLAYER_COMMUNICATE)
    void communicate(CommunicationType type);

    @MethodKey(Constants.PLAYER_DAMAGE)
    void damage(int damageAmount, Player damager);

    @MethodKey(Constants.PLAYER_HEAL)
    void heal(int healAmount, Player healer);

    @MethodKey(Constants.PLAYER_KILL)
    void kill(Player killer);

    @MethodKey(Constants.PLAYER_START_DAMAGE_OVER_TIME)
    @MethodReturnValue(Constants.PLAYER_LAST_DAMAGE_OVER_TIME_ID)
    int startDamageOverTime(int damagePerSecond, int duration, Player damager);

    @MethodKey(Constants.PLAYER_START_HEAL_OVER_TIME)
    @MethodReturnValue(Constants.PLAYER_LAST_HEAL_OVER_TIME_ID)
    int startHealOverTime(int healPerSecond, int duration, Player healer);

    @MethodKey(Constants.PLAYER_SET_MAX_HEALTH)
    void setMaxHealth(int maxHealth);

    @MethodKey(Constants.PLAYER_CLEAR_STATUS)
    void clearStatus(Status status);

    @MethodKey(Constants.PLAYER_SET_STATUS)
    void setStatus(Status status, double duration, Player assister);

    @MethodKey(Constants.PLAYER_RESURRECT)
    void resurrect();

    @MethodKey(Constants.PLAYER_RESPAWN)
    void respawn();

    @MethodKey(Constants.PLAYER_SET_RESPAWN_TIME)
    void setMaxRespawnTime(int maxRespawnTime);

    @MethodKey(Constants.PLAYER_SET_DAMAGE_DEALT)
    void setDamageDealt(double damageDealtModifier);

    @MethodKey(Constants.PLAYER_SET_DAMAGE_RECEIVED)
    void setDamageReceived(double damageReceivedModifier);

    @MethodKey(Constants.PLAYER_SET_HEALING_DEALT)
    void setHealingDealt(double healingDealtModifier);

    @MethodKey(Constants.PLAYER_SET_HEALING_RECEIVED)
    void setHealingReceived(double healingReceivedModifier);

    @MethodKey(Constants.PLAYER_ALLOW_BUTTON)
    void setButtonAllowed(Button button);

    @MethodKey(Constants.PLAYER_DISALLOW_BUTTON)
    void setButtonDisabled(Button button);

    @MethodKey(Constants.PLAYER_PRESS_BUTTON)
    void pressButton(Button button);

    @MethodKey(Constants.PLAYER_START_HOLDING_BUTTON)
    void startHoldingButton(Button button);

    @MethodKey(Constants.PLAYER_STOP_HOLDING_BUTTON)
    void stopHoldingButton(Button button);

    @MethodKey(Constants.PLAYER_SET_ULTIMATE_CHARGE)
    void setUltimateAbilityCharge(int chargePercent);

    @MethodKey(Constants.PLAYER_SET_PRIMARY_FIRE_ENABLED)
    void setPrimaryFireEnabled(boolean primaryFireEnabled);

    @MethodKey(Constants.PLAYER_SET_SECONDARY_FIRE_ENABLED)
    void setSecondaryFireEnabled(boolean secondaryFireEnabled);

    @MethodKey(Constants.PLAYER_SET_ABILITY_1_ENABLED)
    void setFirstAbilityEnabled(boolean firstAbilityEnabled);

    @MethodKey(Constants.PLAYER_SET_ABILITY_2_ENABLED)
    void setSecondAbilityEnabled(boolean secondAbilityEnabled);

    @MethodKey(Constants.PLAYER_SET_ULTIMATE_ABILITY_ENABLED)
    void setUltimateAbilityEnabled(boolean ultimateAbilityEnabled);

}
