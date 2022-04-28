package Common;

import java.io.File;
import java.io.IOException;
import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;

public class VoiceRecord extends Activity{
    String recordfile;
    MediaRecorder Callrecorder;
    MediaRecorder mRecorder;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
 
    }
    
    public MediaRecorder StartRecording(String FileName, int DurationSecond)
    {
    	mRecorder = new MediaRecorder();
	   	final File sdcard = Environment.getExternalStorageDirectory();
	   	recordfile = sdcard.getAbsolutePath() + File.separator + ProjectSetting.DatabaseFolder + File.separator + FileName +".amr";

        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(recordfile);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setMaxDuration(DurationSecond * 1000);
        
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            //Log.e(LOG_TAG, "prepare() failed");
        }
        
        return mRecorder;
    }
    
    public void StopRecording(MediaRecorder mr)
    {
    	mr.stop();
	    mr.release();
	    mr = null;	
    }
}
