package com.ugb.controlesbasicos;
import android.os.AsyncTask;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class eliminarDatosServidor extends AsyncTask<String, Void, Boolean> {
    private OnDeleteListener onDeleteListener;

    public eliminarDatosServidor(OnDeleteListener listener) {
        this.onDeleteListener = listener;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String _id = params[0];

//
        try {
            URL url = new URL(utilidades.urlMto);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NOT_FOUND;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (onDeleteListener != null) {
            onDeleteListener.onDeleteComplete(success);
        }
    }

    public interface OnDeleteListener {
        void onDeleteComplete(boolean success);
    }
}