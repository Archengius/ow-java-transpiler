package me.archen.owtranspiler.scriptsdk.types;

import me.archen.owtranspiler.scriptsdk.impl.Constants;
import me.archen.owtranspiler.scriptsdk.impl.DummyProvider;
import me.archen.owtranspiler.scriptsdk.impl.MethodKey;

/**
 * Describes vector and operations that can be performed with it
 * Vector instances are immutable and each call results in creation of new vector
 * Instances can initialized via Vector#create and Vector#createFromAngles
 * Implementation of operations is Overwatch-dependent
 */
public interface Vector {

    @MethodKey(Constants.VECTOR_NEW)
    static Vector create(double x, double y, double z) {
        return DummyProvider.createDummy(x, y, z);
    }

    @MethodKey(Constants.VECTOR_FROM_ANGLES)
    static Vector createFromAngles(double horizontalAngle, double verticalAngle) {
        return DummyProvider.createDummy(horizontalAngle, verticalAngle);
    }

    @MethodKey(Constants.VECTOR_X_COMPONENT)
    double getX();

    @MethodKey(Constants.VECTOR_Y_COMPONENT)
    double getY();

    @MethodKey(Constants.VECTOR_Z_COMPONENT)
    double getZ();

    @MethodKey(Constants.VECTOR_ADD)
    Vector add(Vector other);

    @MethodKey(Constants.VECTOR_SUBTRACT)
    Vector subtract(Vector other);

    @MethodKey(Constants.VECTOR_DIVIDE)
    Vector divide(Vector other);

    @MethodKey(Constants.VECTOR_MULTIPLY)
    Vector multiply(Vector other);

    @MethodKey(Constants.VECTOR_DIVIDE)
    Vector divide(double divider);

    @MethodKey(Constants.VECTOR_MULTIPLY)
    Vector multiply(double multiplier);

    @MethodKey(Constants.VECTOR_NORMALIZE)
    Vector normalize();

    @MethodKey(Constants.VECTOR_DOT_PRODUCT)
    Vector dotProduct(Vector other);

    @MethodKey(Constants.VECTOR_CROSS_PRODUCT)
    Vector crossProduct(Vector other);

    @MethodKey(Constants.VECTOR_DIRECTION_TOWARDS)
    Vector directionTowards(Vector target);

    @MethodKey(Constants.VECTOR_DISTANCE_TO)
    double distanceTo(Vector target);

    @MethodKey(Constants.HORIZONTAL_ANGLE_FROM_DIRECTION)
    double toHorizontalAngle();

    @MethodKey(Constants.VERTICAL_ANGLE_FROM_DIRECTION)
    double toVerticalAngle();

}
