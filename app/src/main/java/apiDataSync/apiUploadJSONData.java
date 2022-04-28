package apiDataSync;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import Common.apiProjectSetting;

/**
 * Created by TanvirHossain on 02/12/2015.
 */
public class apiUploadJSONData extends AsyncTask<String, Void, String> {
    private Context context;
    final String USER_AGENT = apiProjectSetting.USER_AGENT_WebAPI;
    HttpURLConnection up_conn = null;
    final String url        = apiProjectSetting.URL_WebAPI;
    StringBuffer response;

    public void setContext(Context contextf){
        context = contextf;
    }

    private Exception exception;
    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            URL obj = null;
            try {
                obj = new URL(url);
                up_conn = (HttpURLConnection) obj.openConnection();

                up_conn.setReadTimeout(150000); //milliseconds
                up_conn.setConnectTimeout(15000); // milliseconds

                //add reuqest header
                up_conn.setRequestMethod("POST");
                up_conn.setRequestProperty("User-Agent", USER_AGENT);
                up_conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                up_conn.setRequestProperty("Content-Type", "application/json");

            } catch (MalformedURLException e) {
                up_conn.disconnect();
                e.printStackTrace();
            } catch (IOException e) {
                up_conn.disconnect();
                e.printStackTrace();
            }

            // Send post request
            up_conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(up_conn.getOutputStream());
            wr.writeBytes(urls[0]);
            wr.flush();
            wr.close();

            // getting post response
            int responseCode = up_conn.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(up_conn.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //up_conn.disconnect();
        } catch (Exception e) {
            up_conn.disconnect();
            this.exception = e;
            return null;
        }finally {
            up_conn.disconnect();
        }
        return response.toString();
    }

    protected void onPostExecute(String feed) {
    }
}