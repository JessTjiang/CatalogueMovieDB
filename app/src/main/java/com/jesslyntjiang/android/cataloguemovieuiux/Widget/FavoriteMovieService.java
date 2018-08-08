package com.jesslyntjiang.android.cataloguemovieuiux.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class FavoriteMovieService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
