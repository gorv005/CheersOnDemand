package com.cheersondemand.util;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by gaurav.garg on 12-07-2018.
 */

public class FlipAnimation extends Animation {

    private Camera camera;
    public static final int DIRECTION_X = 1, DIRECTION_Y = 2, DIRECTION_Z = 3;

    private View fromView;
    private View toView;

    private float centerX;
    private float centerY;
    private int rotationDirection = DIRECTION_Y;

    private int translateDirection = DIRECTION_X;
    private boolean forward = true;

    /**
     * Creates a 3D flip animation between two views.
     *
     * @param fromView First view in the transition.
     * @param toView   Second view in the transition.
     */
    public FlipAnimation(View fromView, View toView) {
        this.fromView = fromView;
        this.toView = toView;

        setDuration(500);
        setFillAfter(false);
        setInterpolator(new AccelerateDecelerateInterpolator());
    }

    public void reverse() {
        forward = false;
        View switchView = toView;
        toView = fromView;
        fromView = switchView;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        centerX = width / 2;
        centerY = height / 2;
        camera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        // Angle around the y-axis of the rotation at the given time
        // calculated both in radians and degrees.
        final double radians = Math.PI * interpolatedTime;
        float degrees = (float) (180.0 * radians / Math.PI);

        // Once we reach the midpoint in the animation, we need to hide the
        // source view and show the destination view. We also need to change
        // the angle by 180 degrees so that the destination does not come in
        // flipped around
        if (interpolatedTime >= 0.5f) {
            degrees -= 180.f;
            fromView.setVisibility(View.GONE);
            toView.setVisibility(View.VISIBLE);
        }

        if (forward)
            degrees = -degrees; //determines direction of rotation when flip begins

        final Matrix matrix = t.getMatrix();
        camera.save();
        if (translateDirection == DIRECTION_Z) {
            camera.translate(0.0f, 0.0f, (float) (150.0 * Math.sin(radians)));
        } else if (translateDirection == DIRECTION_Y) {
            camera.translate(0.0f, (float) (150.0 * Math.sin(radians)), 0.0f);
        } else {
            camera.translate((float) (150.0 * Math.sin(radians)), 0.0f, 0.0f);
        }

        if (rotationDirection == DIRECTION_Z) {
            camera.rotateZ(degrees);
        } else if (rotationDirection == DIRECTION_Y) {
            camera.rotateY(degrees);
        } else {
            camera.rotateX(degrees);
        }
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
