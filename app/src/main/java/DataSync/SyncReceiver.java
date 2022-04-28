package DataSync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import DataSync.SyncRebuildDatabase;

/**
 * Created by TanvirHossain on 08/03/2015.
 */

public class SyncReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent syncService = new Intent(context, SyncRebuildDatabase.class);
        context.startService(syncService);
   }
}