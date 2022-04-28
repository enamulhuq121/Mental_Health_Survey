package DataSync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.icddrb.standard_v3.DataSyncService;

import Common.Connection;

import static DataSync.Log.logInfo;

/**
 * Created by TanvirHossain on 08/03/2015.
 */

public class SyncBroadcastReceiver extends BroadcastReceiver {
    Connection C;
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(getApplicationContext(), "msg msg", Toast.LENGTH_SHORT).show();
        logInfo("Sync alarm triggered. Trying to Sync.");

        Intent syncService = new Intent(context, DataSyncService.class);
        context.startService(syncService);
    }
}