package com.murrayde.animekingandroid.epoxy.models;

import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.murrayde.animekingandroid.R;

@EpoxyModelClass(layout = R.layout.loading_model)
public abstract class LoadingModel extends EpoxyModelWithHolder<LoadingModel.LoadingHolder> {

    @Override
    public void bind(@NonNull LoadingHolder holder) {

    }

    static class LoadingHolder extends EpoxyHolder {

        ProgressBar progressBar;

        @Override
        protected void bindView(@NonNull View itemView) {

            progressBar = itemView.findViewById(R.id.progress_bar);

        }
    }

}
