package cari_ustadz.pondokit.id.cari_ustadz.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cari_ustadz.pondokit.id.cari_ustadz.models.PrayerTimeReminder;

public class BootCompletedBroadcastReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            PrayerTimeReminder.reschedulePrayerTimeReminders(context);
        }
    }
}
