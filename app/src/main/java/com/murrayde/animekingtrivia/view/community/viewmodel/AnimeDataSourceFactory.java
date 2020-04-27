package com.murrayde.animekingtrivia.view.community.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.murrayde.animekingtrivia.network.community.api.AnimeData;
import com.murrayde.animekingtrivia.network.community.AnimeApiEndpoint;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.CompositeDisposable;

public class AnimeDataSourceFactory extends DataSource.Factory<String, AnimeData> {

    @SuppressWarnings("FieldCanBeLocal")
    private AnimeDataSource animeDataSource;
    private MutableLiveData<AnimeDataSource> animeSourceLiveData = new MutableLiveData<>();


    @SuppressWarnings("WeakerAccess")
    AnimeApiEndpoint animeApiEndpoint;
        private CompositeDisposable compositeDisposable;

        AnimeDataSourceFactory(AnimeApiEndpoint animeApiEndpoint, CompositeDisposable compositeDisposable) {
            this.animeApiEndpoint = animeApiEndpoint;
        this.compositeDisposable = compositeDisposable;
    }

    @NotNull
    @Override
    public DataSource<String, AnimeData> create() {
        animeDataSource = new AnimeDataSource(animeApiEndpoint, compositeDisposable);
        animeSourceLiveData.postValue(animeDataSource);
        return animeDataSource;
    }
}