package com.tianxiabuyi.txutils.adapter.animation;

import android.animation.Animator;
import android.view.View;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
@Deprecated
public interface BaseAnimation {

    Animator[] getAnimators(View view);

}
