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
public class apiDownloadJSONData extends AsyncTask<String, Void, String> {
    private Context context;
    final String USER_AGENT = apiProjectSetting.USER_AGENT_WebAPI;
    HttpURLConnection down_conn = null;
    final String url = apiProjectSetting.URL_WebAPI;
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
                down_conn = (HttpURLConnection) obj.openConnection();
                down_conn.setReadTimeout(150000); //milliseconds
                down_conn.setConnectTimeout(15000); // milliseconds

                //add reuqest header
                down_conn.setRequestMethod("POST");
                down_conn.setRequestProperty("User-Agent", USER_AGENT);
                down_conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                down_conn.setRequestProperty("Content-Type", "application/json");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                down_conn.disconnect();
                e.printStackTrace();
            }

            // Send post request
            down_conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(down_conn.getOutputStream());
            wr.writeBytes(urls[0]);
            wr.flush();
            wr.close();

            // getting post response
            int responseCode = down_conn.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(down_conn.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (Exception e) {
            down_conn.disconnect();
            this.exception = e;
            return null;
        }finally {
            down_conn.disconnect();
        }
        return response.toString();
    }

    protected void onPostExecute(String feed) {
    }
}