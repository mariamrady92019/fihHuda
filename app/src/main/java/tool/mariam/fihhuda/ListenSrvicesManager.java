package tool.mariam.fihhuda;

import android.media.MediaPlayer;

public class ListenSrvicesManager {
    public  static MediaPlayer mediaPlayer;

   public static MediaPlayer getInstance(){

        if (mediaPlayer==null){
       mediaPlayer = new MediaPlayer();
        }
        return mediaPlayer;
    }


    public static void setMediaPlayerNull(){

      mediaPlayer=null ;
    }
}
