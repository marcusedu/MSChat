package info.marcussoftware.mschat.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;

public class AnimateUtil {
    public static void animateShowView(final View viewAExibir) {
        viewAExibir.post(() -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP && viewAExibir.isAttachedToWindow()) {
                int cx = (viewAExibir.getLeft() + viewAExibir.getRight()) / 2;
                int cy = (viewAExibir.getTop() + viewAExibir.getBottom()) / 2;
                int finalRadius = Math.max(viewAExibir.getWidth(), viewAExibir.getHeight());
                Animator anim = ViewAnimationUtils.createCircularReveal(viewAExibir, cx, cy, 0, finalRadius);
                anim.start();
            }
            viewAExibir.setVisibility(View.VISIBLE);
        });
    }

    public static void animateHideView(final View viewAOcultar) {
        viewAOcultar.post(() -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP && viewAOcultar.isAttachedToWindow()) {
                int cx = (viewAOcultar.getLeft() + viewAOcultar.getRight()) / 2;
                int cy = (viewAOcultar.getTop() + viewAOcultar.getBottom()) / 2;
                int initialRadius = viewAOcultar.getWidth();
                Animator anim = ViewAnimationUtils.createCircularReveal(viewAOcultar, cx, cy, initialRadius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        viewAOcultar.setVisibility(View.GONE);
                    }
                });
                anim.start();
            } else {
                viewAOcultar.setVisibility(View.GONE);
            }
        });
    }

    public static void editTextLikeTextView(EditText editText) {
        editText.setCursorVisible(false);
        editText.setLongClickable(false);
        editText.setClickable(false);
        editText.setFocusable(false);
        editText.setSelected(false);
        editText.setKeyListener(null);
        editText.setBackgroundResource(android.R.color.transparent);
    }
}