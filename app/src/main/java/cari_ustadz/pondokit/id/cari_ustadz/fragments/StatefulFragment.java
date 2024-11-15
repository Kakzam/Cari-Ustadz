package cari_ustadz.pondokit.id.cari_ustadz.fragments;

import android.support.v4.app.Fragment;

import com.kennyc.view.MultiStateView;

/**
 * Created by akif on 04/06/16.
 */
public abstract class StatefulFragment extends Fragment {
    protected MultiStateView multiStateViewLayout;

    public static final int RETRY_ACTION_DOWNLOAD = 1;

    abstract protected void changeStateTo(int newState, final int retryAction);
    abstract protected void retry(int action);
}
