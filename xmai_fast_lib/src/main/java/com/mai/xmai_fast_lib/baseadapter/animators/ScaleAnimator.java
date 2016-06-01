package com.mai.xmai_fast_lib.baseadapter.animators;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.mai.xmai_fast_lib.baseadapter.listener.RBaseAnimator;

/**
 * Created by mai on 16/6/1.
 */
public class ScaleAnimator implements RBaseAnimator {


    @Override
    public Animator getAnimator(View view) {
        ObjectAnimator scaleAnimatorX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleAnimatorY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setTarget(view);
        animSet.setDuration(300);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.playTogether(scaleAnimatorX, scaleAnimatorY);
        return animSet;
    }
}
