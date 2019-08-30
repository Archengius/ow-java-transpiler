package me.archen.owtranspiler.scriptsdk.types;

import me.archen.owtranspiler.scriptsdk.constant.Button;
import me.archen.owtranspiler.scriptsdk.constant.Hero;
import me.archen.owtranspiler.scriptsdk.constant.Status;
import me.archen.owtranspiler.scriptsdk.constant.Team;
import me.archen.owtranspiler.scriptsdk.impl.Constants;
import me.archen.owtranspiler.scriptsdk.impl.MethodKey;

/**
 * Describes player and operations that can be performed with it
 * Each connected player owns one Player instance, either dead or alive
 */
public interface Player extends AbstractPlayer {

    @MethodKey(Constants.PLAYER_POSITION)
    Vector getPosition();

    @MethodKey(Constants.PLAYER_EYE_POSITION)
    Vector getEyePosition();

    @MethodKey(Constants.PLAYER_GET_DIRECTION)
    Vector getLookVec();

    @MethodKey(Constants.PLAYER_IS_IN_VIEW_ANGLE)
    boolean isInViewAngle(Vector position);

    @MethodKey(Constants.PLAYER_HAS_STATUS)
    boolean hasStatus(Status status);

    @MethodKey(Constants.PLAYER_IS_BUTTON_HELD)
    boolean isButtonHeld(Button button);

    @MethodKey(Constants.PLAYER_GET_TEAM)
    Team getTeam();

    @MethodKey(Constants.PLAYER_GET_HERO)
    Hero getHero();

    @MethodKey(Constants.PLAYER_GET_SLOT)
    int getSlotNumber();

    @MethodKey(Constants.PLAYER_HEALTH)
    int getHealth();

    @MethodKey(Constants.PLAYER_HEALTH_PERCENT)
    double getHealthPercent();

    @MethodKey(Constants.PLAYER_MAX_HEALTH)
    int getMaxHealth();

    @MethodKey(Constants.PLAYER_GET_SCORE)
    int getScore();

    @MethodKey(Constants.PLAYER_GET_SPEED)
    double getSpeed();

    @MethodKey(Constants.PLAYER_ULTIMATE_CHARGE_PERCENT)
    int getUltimateChargePercent();

    @MethodKey(Constants.PLAYER_GET_VELOCITY)
    Vector getVelocity();

    @MethodKey(Constants.PLAYER_IS_ON_GROUND)
    boolean isOnGround();

    @MethodKey(Constants.PLAYER_IS_CROUCHING)
    boolean isCrouching();

    @MethodKey(Constants.PLAYER_IS_MOVING)
    boolean isMoving();

    @MethodKey(Constants.PLAYER_IS_ON_WALL)
    boolean isOnWall();

    @MethodKey(Constants.PLAYER_IS_ON_OBJECTIVE)
    boolean isOnObjective();

    @MethodKey(Constants.PLAYER_IS_PORTRAIT_ON_FIRE)
    boolean isPortraitOnFire();

    @MethodKey(Constants.PLAYER_IS_ALIVE)
    boolean isAlive();

    @MethodKey(Constants.PLAYER_IS_CONNECTED)
    boolean isConnected();

    @MethodKey(Constants.PLAYER_HAS_SPAWNED)
    boolean hasPlayerSpawned();

    @MethodKey(Constants.PLAYER_IS_USING_ULTIMATE)
    boolean isUsingUltimate();

    @MethodKey(Constants.PLAYER_IS_USING_ABILITY_1)
    boolean isUsingFirstAbility();

    @MethodKey(Constants.PLAYER_IS_USING_ABILITY_2)
    boolean isUsingSecondAbility();

    @MethodKey(Constants.PLAYER_IS_FIRING_PRIMARY)
    boolean isFiringPrimaryFire();

    @MethodKey(Constants.PLAYER_IS_FIRING_SECONDARY)
    boolean isFiringSecondaryFire();

    @MethodKey(Constants.PLAYER_GET_PLAYERS_IN_VIEW_ANGLE)
    Player[] getPlayersInViewAngle();

    @MethodKey(Constants.PLAYER_GET_HORIZONTAL_ANGLE)
    double getYaw();

    @MethodKey(Constants.PLAYER_GET_VERTICAL_ANGLE)
    double getPitch();

    @MethodKey(Constants.PLAYER_GET_THROTTLE)
    Vector getMoveThrottle();

    @MethodKey(Constants.PLAYER_GET_VERTICAL_SPEED)
    double getVerticalSpeed();

    @MethodKey(Constants.PLAYER_GET_HORIZONTAL_SPEED)
    double getHorizontalSpeed();

    @MethodKey(Constants.PLAYER_GET_ALLOWED_HEROES)
    Hero[] getAllowedHeroes();

}
