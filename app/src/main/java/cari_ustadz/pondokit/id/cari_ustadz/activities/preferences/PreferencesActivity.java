package cari_ustadz.pondokit.id.cari_ustadz.activities.preferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cari_ustadz.pondokit.id.cari_ustadz.R;
import cari_ustadz.pondokit.id.cari_ustadz.fragments.preferences.PreferencesFragment;

public class PreferencesActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        setTitle("Setting");

        PreferencesFragment preferencesFragment = new PreferencesFragment();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout_preferencesContainer, preferencesFragment)
                .commit();
    }
}
