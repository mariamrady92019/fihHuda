package tool.mariam.fihhuda.Base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import tool.mariam.fihhuda.R;
import tool.mariam.fihhuda.quran.viewsModel.ListeningViewModel;

public class NotificationBroadCastReceiver extends BroadcastReceiver {
    public Context context;
    ListeningViewModel listeningViewModel = new ListeningViewModel();
    RemoteViews notificationLayout = new RemoteViews(null, R.layout.custom_notification);

    // listeningViewModel.setContext(context);
    public NotificationBroadCastReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        switch (intent.getAction()) {
            case "cancel": {
                cancelService(context);
                Constants.isPlaying = false;
                NotificationCreator_Helper.cancelNotification();
                break;
            }

            case "prev": {
                playPrevious(context);
                break;
            }
            case "next": {
                playNext(context);
                break;
            }
            case "play_puase": {
                play_puase(context);
                break;
            }


        }
    }

    private void playNext(Context context) {
        Intent intent = new Intent(context, ListeningService.class);
        Constants.position = Constants.position + 1;
        intent.putExtra("position", Constants.position);
        listeningViewModel.setContext(context);

        NotificationCreator_Helper.updateNotification(
                listeningViewModel.getSurahNameByPosition(Constants.position));
        context.startService(intent);
    }

    private void play_puase(Context context) {
        NotificationCreator_Helper.updatePlayButtonNotification();
    }

    private void playPrevious(Context context) {
        Intent intent = new Intent(context, ListeningService.class);
        Constants.position = Constants.position - 1;
        listeningViewModel.setContext(context);

        NotificationCreator_Helper.updateNotification(listeningViewModel.
                getSurahNameByPosition(Constants.position));

        intent.putExtra("position", Constants.position);
        context.startService(intent);
    }

    private void cancelService(Context context) {
        // ListenSrvicesManager.mediaPlayer.release();
        // ListenSrvicesManager.setMediaPlayerNull();
        context.stopService(new Intent(context, ListeningService.class));

        Log.e("broadcast", "cancelclicked");

    }
}
