package com.cs151.helpfulhints;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Interpolator;

/**
 * @author ben holman
 */

public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private boolean mIsAnimatingOut = false;
    private float mOriginalY = -1;
    private int mScreenHeight = 0;

    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        super();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(dm);
        mScreenHeight = dm.heightPixels;
    }

    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                                       final View directTargetChild, final View target, final int nestedScrollAxes) {
        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
            || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                               final View target, final int dxConsumed, final int dyConsumed,
                               final int dxUnconsumed, final int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0 && !this.mIsAnimatingOut && child.getVisibility() == View.VISIBLE) {
            // User scrolled down and the FAB is currently visible -> hide the FAB
            animateOut(child);
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            animateIn(child);
        }
    }

    // Same animation that FloatingActionButton.Behavior uses to hide the FAB when the AppBarLayout exits
    private void animateOut(final FloatingActionButton button) {
        if(ScrollAwareFABBehavior.this.mOriginalY < 0) {
            ScrollAwareFABBehavior.this.mOriginalY = button.getY();
        }
        ViewCompat.animate(button).y(mScreenHeight).setInterpolator(INTERPOLATOR).withLayer()
            .setListener(new ViewPropertyAnimatorListener() {
                public void onAnimationStart(View view) {
                    ScrollAwareFABBehavior.this.mIsAnimatingOut = true;
                }

                public void onAnimationCancel(View view) {
                    ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
                }

                public void onAnimationEnd(View view) {
                    ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
                    view.setVisibility(View.GONE);
                }
            }).start();
    }

    // Same animation that FloatingActionButton.Behavior uses to show the FAB when the AppBarLayout enters
    private void animateIn(FloatingActionButton button) {
        button.setVisibility(View.VISIBLE);
            ViewCompat.animate(button).y(ScrollAwareFABBehavior.this.mOriginalY)
                .setInterpolator(INTERPOLATOR).withLayer().setListener(null)
                .start();
    }
}
