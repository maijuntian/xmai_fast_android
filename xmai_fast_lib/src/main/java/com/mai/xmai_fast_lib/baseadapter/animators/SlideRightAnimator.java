package com.mai.xmai_fast_lib.baseadapter.animators;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.mai.xmai_fast_lib.baseadapter.listener.RBaseAnimator;

/**
 * Created by mai on 16/6/1.
 */
public class SlideRightAnimator implements RBaseAnimator {

    @Override
    public Animator getAnimator(View view) {
        ObjectAnimator animtor = ObjectAnimator.ofFloat(view, "translationX", view.getMeasuredWidth(), 0);
        animtor.setDuration(300);
        return animtor;
    }
}
