package cari_ustadz.pondokit.id.cari_ustadz.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import cari_ustadz.pondokit.id.cari_ustadz.models.Place;
import cari_ustadz.pondokit.id.cari_ustadz.models.PrayerTimeReminder;
import cari_ustadz.pondokit.id.cari_ustadz.models.PrayerTimesOfDay;
import cari_ustadz.pondokit.id.cari_ustadz.utilities.MuezzinAPI;
import cari_ustadz.pondokit.id.cari_ustadz.utilities.Pref;
import cari_ustadz.pondokit.id.cari_ustadz.widgetproviders.PrayerTimesWidgetBase;
import com.github.mehmetakiftutuncu.toolbelt.Log;
import com.github.mehmetakiftutuncu.toolbelt.Optional;

import java.util.List;

/**
 * Created by akif on 23/05/16.
 */
public class PrayerTimesUpdaterService extends IntentService implements MuezzinAPI.OnPrayerTimesDownloadedListener {
    public PrayerTimesUpdaterService() {
        super("PrayerTimesUpdaterService");
    }

    private Optional<Place> maybeCurrentPlace;

    public static void setUpdater(Context context) {
        Intent intent = new Intent(context, PrayerTimesUpdaterService.class);

        context.startService(intent);
    }

    @Override protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            update();
        }
    }

    private void update() {
        maybeCurrentPlace = Pref.Places.getCurrentPlace(this);

        if (maybeCurrentPlace.isEmpty()) {
            return;
        }

        Place place = maybeCurrentPlace.get();

        Log.debug(getClass(), "Updating prayer times for '%s'...", place);

        MuezzinAPI.get().getPrayerTimes(place, this);
    }

    @Override public void onPrayerTimesDownloaded(@NonNull List<PrayerTimesOfDay> prayerTimes) {
        Place place = maybeCurrentPlace.get();

        if (PrayerTimesOfDay.saveAllPrayerTimes(this, place, prayerTimes)) {
            // Updated prayer times and saved them successfully, now try to reschedule prayer time reminders.
            PrayerTimeReminder.reschedulePrayerTimeReminders(this);
            PrayerTimesWidgetBase.updateAllWidgets(this);
        }
    }

    @Override public void onDownloadPrayerTimesFailed(Exception e) {
        Log.error(getClass(), "Failed to download prayer times for place '%s'!", maybeCurrentPlace.get());
    }
}
