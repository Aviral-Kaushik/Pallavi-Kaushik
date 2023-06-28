package com.pallavikaushik.Utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Spacing extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;
    private final int column;

    public Spacing(int verticalSpaceHeight, int column) {
        this.verticalSpaceHeight = verticalSpaceHeight;
        this.column = column;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);

        if (position < column) {
            outRect.top = verticalSpaceHeight;
        }
    }
}