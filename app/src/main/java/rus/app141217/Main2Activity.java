package rus.app141217;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main2Activity extends AppCompatActivity {

Button ButSubmit;
TextView TVnm, TVname, TVgender, TVmodel, TVnewPas, TVcurPas;
EditText ETnm, ETname, ETmodel, ETnewPas, ETcurPas;
RadioButton RBman, RBfemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ButSubmit = (Button) findViewById(R.id.ButSubmit);

        TVnm = (TextView) findViewById(R.id.TVnm);
        TVname = (TextView) findViewById(R.id.TVname);
        TVgender = (TextView) findViewById(R.id.TVgender);
        TVmodel = (TextView) findViewById(R.id.TVmodel);
        TVnewPas = (TextView) findViewById(R.id.TVnewPas);
        TVcurPas = (TextView) findViewById(R.id.TVcurPas);

        ETnm = (EditText) findViewById(R.id.ETnm);
        ETname = (EditText) findViewById(R.id.ETname);
        ETmodel = (EditText) findViewById(R.id.ETmodel);
        ETnewPas = (EditText) findViewById(R.id.ETnewPas);
        ETcurPas = (EditText) findViewById(R.id.ETcurPas);

        RBman = (RadioButton) findViewById(R.id.RBman);
        RBman.setChecked(true);
        RBfemale = (RadioButton) findViewById(R.id.RBfemale);

    }



    public void onButSubmit(View view)
    {
        SendLoginData sld = new SendLoginData();
        sld.execute();
    }



    class SendLoginData extends AsyncTask<Void, Void, Void> {

        String resultString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String myURL = "http://h120138.s21.test-hf.su/game.php?";
                //String parammetrs = "varPost=45";
                String parammetrs = "name=" + ETname.getText().toString();
                parammetrs += "&" + "marka=" + ETmodel.getText().toString();
                parammetrs += "&" + "pas=" + ETnewPas.getText().toString();
                parammetrs += "&" + "id=" + ETcurPas.getText().toString();
                parammetrs += "&" + "nm=" + ETnm.getText().toString();

                if(RBman.isChecked()) {parammetrs += "&" + "gender=1";}
                else if(RBfemale.isChecked()){parammetrs += "&" + "gender=0";}

                byte[] data = null;
                InputStream is = null;

                try {
                    URL url = new URL(myURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    conn.setRequestProperty("Content-Length", "" + Integer.toString(parammetrs.getBytes().length));
                    OutputStream os = conn.getOutputStream();
                    data = parammetrs.getBytes("UTF-8");
                    os.write(data);
                    data = null;

                    conn.connect();
                    int responseCode= conn.getResponseCode();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    if (responseCode == 200) {
                        is = conn.getInputStream();

                        byte[] buffer = new byte[8192]; // Такого вот размера буфер
                        // Далее, например, вот так читаем ответ
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            baos.write(buffer, 0, bytesRead);
                        }
                        data = baos.toByteArray();
                        resultString = new String(data, "UTF-8");

                    } else {
                    }



                } catch (MalformedURLException e) {

                    //resultString = "MalformedURLException:" + e.getMessage();
                } catch (IOException e) {

                    //resultString = "IOException:" + e.getMessage();
                } catch (Exception e) {

                    //resultString = "Exception:" + e.getMessage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(resultString != null) {
                Toast toast = Toast.makeText(getApplicationContext(), resultString, Toast.LENGTH_SHORT);
                toast.show();

            }

        }
    }






}
