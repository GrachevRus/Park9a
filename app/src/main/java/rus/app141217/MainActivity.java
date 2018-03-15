package rus.app141217;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;




import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "HttpExample";
    TextView TV11, TV12, TV21, TV22, TV31, TV32, TV41, TV42;
    TextView TV51, TV52, TV61, TV62, TV71, TV72, TV81, TV82, TV91, TV92;
    Button ButLogin, ButRefresh, ButExit;
    public static int inc=0;
    public static int a = 0;
    public static ArrayList<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TV11 = (TextView) findViewById(R.id.TV11);
        TV12 = (TextView) findViewById(R.id.TV12);
        TV21 = (TextView) findViewById(R.id.TV21);
        TV22 = (TextView) findViewById(R.id.TV22);
        TV31 = (TextView) findViewById(R.id.TV31);
        TV32 = (TextView) findViewById(R.id.TV32);
        TV41 = (TextView) findViewById(R.id.TV41);
        TV42 = (TextView) findViewById(R.id.TV42);
        TV51 = (TextView) findViewById(R.id.TV51);
        TV52 = (TextView) findViewById(R.id.TV52);
        TV61 = (TextView) findViewById(R.id.TV61);
        TV62 = (TextView) findViewById(R.id.TV62);
        TV71 = (TextView) findViewById(R.id.TV71);
        TV72 = (TextView) findViewById(R.id.TV72);
        TV81 = (TextView) findViewById(R.id.TV81);
        TV82 = (TextView) findViewById(R.id.TV82);
        TV91 = (TextView) findViewById(R.id.TV91);
        TV92 = (TextView) findViewById(R.id.TV92);

        ButLogin = (Button) findViewById(R.id.ButLogin);
        ButRefresh = (Button) findViewById(R.id.ButRefresh);
        ButExit = (Button) findViewById(R.id.ButExit);


    }


    public void onButLogin(View view)
    {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }


    public void onButRefresh(View view)
    {
        String stringUrl = "http://h120138.s21.test-hf.su/getandroid.php?number=0";

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
        {
            ButRefresh.setText("Интернет есть");
            new DownloadWebpageTask().execute(stringUrl);
        }
        else
        {
            ButRefresh.setText("Интернета нет");
        }
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params исходит из вызова execute (): params [0] - это url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Не удалось получить веб-страницу. URL может быть недействительным.";
            }
        }
        // onPostExecute отображает результаты AsyncTask
        @Override
        protected void onPostExecute(String result)
        {
            extract(result);
        }
    }


    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Отображать только первые 700 символов полученного содержимого веб-страницы.
        int len = 700;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000); // установка таймаута перед выполнением - 10 000 миллисекунд
            conn.setConnectTimeout(15000); //милисекунды
            conn.setRequestMethod("GET"); // установка метода получения данных -GET
            conn.setDoInput(true);
            // Запускает запрос
            conn.connect(); // подключаемся к ресурсу
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "Ответ: " + response);
            is = conn.getInputStream();

            // Преобразует InputStream в строку
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Устанавливает, что InputStream закрывается после завершения использования приложения
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }


    //Заполняет поля данными, в зависимости от входных данных
    public void extract(String result)
    {
        a = 0;
        inc = 0;
        while(inc < result.length())
        {
            fun(result, inc, a);
            a++;
        }
        TV11.setText(list.get(0).toString());
        TV12.setText(list.get(1).toString());
        TV21.setText(list.get(2).toString());
        TV22.setText(list.get(3).toString());
        TV31.setText(list.get(4).toString());
        TV32.setText(list.get(5).toString());
        TV41.setText(list.get(6).toString());
        TV42.setText(list.get(7).toString());
        TV51.setText(list.get(8).toString());
        TV52.setText(list.get(9).toString());
        TV61.setText(list.get(10).toString());
        TV62.setText(list.get(11).toString());
        TV71.setText(list.get(12).toString());
        TV72.setText(list.get(13).toString());
        TV81.setText(list.get(14).toString());
        TV82.setText(list.get(15).toString());
        TV91.setText(list.get(16).toString());
        TV92.setText(list.get(17).toString());

        colors();

        list.clear();
        ButRefresh.setText("ОБНОВИТЬ");

    }



    public void fun(String data, int i, int a)
    {
        String txt = "";
        String str1 = "<br>";
        char[] buf = new char[4]; //Создаёт массив "buf" из четырёх символов
        data.getChars(i, i+4, buf, 0); //Заносит в массив последовательно 4 символа
        String str2 = new String(buf); //Помещаются в строковую переменную данные из массива "buf"

        if(str1.equals(str2)) //Сравнивает значения строковых переменных
        {
            char[] num = new char[2]; //Создаёт массив "num" из двух символов
            data.getChars(i+4, i+6, num, 0);//Заносит в массив последовательно 2 символа
            String num2 = new String(num); //Помещаются в строковую переменную данные из массива "num"
            int nm = Integer.parseInt(num2);
            i = i+6;
            while(data.charAt(i) != '<')
            {
                if(data.charAt(i) == '!'){i+=20; break;}
                txt = txt + Character.toString(data.charAt(i));
                i++;
            }
            list.add(txt);//Здесь пихаем в массив данные из txt
            inc = i;
        }
    }





    public void colors() //Цвет ячеек в зависимости от состояния ЗАНЯТО/СВОБОДНО
    {
        if(list.get(0).charAt(0) == 'С')
        {TV11.setBackgroundResource(R.color.colorLabelOwn);
            TV12.setBackgroundResource(R.color.colorLabelOwn);}
        else
        {TV11.setBackgroundResource(R.color.colorLabelAlien);
            TV12.setBackgroundResource(R.color.colorLabelAlien);}

        if(list.get(2).charAt(0) == 'С')
        {TV21.setBackgroundResource(R.color.colorLabelOwn);
            TV22.setBackgroundResource(R.color.colorLabelOwn);}
        else
        {TV21.setBackgroundResource(R.color.colorLabelAlien);
            TV22.setBackgroundResource(R.color.colorLabelAlien);}

        if(list.get(4).charAt(0) == 'С')
        {TV31.setBackgroundResource(R.color.colorLabelOwn);
            TV32.setBackgroundResource(R.color.colorLabelOwn);}
        else
        {TV31.setBackgroundResource(R.color.colorLabelAlien);
            TV32.setBackgroundResource(R.color.colorLabelAlien);}

        if(list.get(6).charAt(0) == 'С')
        {TV41.setBackgroundResource(R.color.colorLabelOwn);
            TV42.setBackgroundResource(R.color.colorLabelOwn);}
        else
        {TV41.setBackgroundResource(R.color.colorLabelAlien);
            TV42.setBackgroundResource(R.color.colorLabelAlien);}

        if(list.get(8).charAt(0) == 'С')
        {TV51.setBackgroundResource(R.color.colorLabelOwn);
            TV52.setBackgroundResource(R.color.colorLabelOwn);}
        else
        {TV51.setBackgroundResource(R.color.colorLabelAlien);
            TV52.setBackgroundResource(R.color.colorLabelAlien);}

        if(list.get(10).charAt(0) == 'С')
        {TV61.setBackgroundResource(R.color.colorLabelOwn);
            TV62.setBackgroundResource(R.color.colorLabelOwn);}
        else
        {TV61.setBackgroundResource(R.color.colorLabelAlien);
            TV62.setBackgroundResource(R.color.colorLabelAlien);}

        if(list.get(12).charAt(0) == 'С')
        {TV71.setBackgroundResource(R.color.colorLabelOwn);
            TV72.setBackgroundResource(R.color.colorLabelOwn);}
        else
        {TV71.setBackgroundResource(R.color.colorLabelAlien);
            TV72.setBackgroundResource(R.color.colorLabelAlien);}

        if(list.get(14).charAt(0) == 'С')
        {TV81.setBackgroundResource(R.color.colorLabelOwn);
            TV82.setBackgroundResource(R.color.colorLabelOwn);}
        else
        {TV81.setBackgroundResource(R.color.colorLabelAlien);
            TV82.setBackgroundResource(R.color.colorLabelAlien);}

        if(list.get(16).charAt(0) == 'С')
        {TV91.setBackgroundResource(R.color.colorLabelOwn);
            TV92.setBackgroundResource(R.color.colorLabelOwn);}
        else
        {TV91.setBackgroundResource(R.color.colorLabelAlien);
            TV92.setBackgroundResource(R.color.colorLabelAlien);}
    }











    // Читает InputStream и преобразует его в String.
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }




    public void onClickTV11(View view)
    {
        String s1 = "МЕСТО 1 - " + TV11.getText().toString();
        String s2 = TV12.getText().toString();
        change(s1, s2);
    }

    public void onClickTV21(View view)
    {
        String s1 = "МЕСТО 2 - " + TV21.getText().toString();
        String s2 = TV22.getText().toString();
        change(s1, s2);
    }

    public void onClickTV31(View view)
    {
        String s1 = "МЕСТО 3 - " + TV31.getText().toString();
        String s2 = TV32.getText().toString();
        change(s1, s2);
    }

    public void onClickTV41(View view)
    {
        String s1 = "МЕСТО 4 - " + TV41.getText().toString();
        String s2 = TV42.getText().toString();
        change(s1, s2);
    }

    public void onClickTV51(View view)
    {
        String s1 = "МЕСТО 5 - " + TV51.getText().toString();
        String s2 = TV52.getText().toString();
        change(s1, s2);
    }

    public void onClickTV61(View view)
    {
        String s1 = "МЕСТО 6 - " + TV61.getText().toString();
        String s2 = TV62.getText().toString();
        change(s1, s2);
    }

    public void onClickTV71(View view)
    {
        String s1 = "МЕСТО 7 - " + TV71.getText().toString();
        String s2 = TV72.getText().toString();
        change(s1, s2);
    }

    public void onClickTV81(View view)
    {
        String s1 = "МЕСТО 8 - " + TV81.getText().toString();
        String s2 = TV82.getText().toString();
        change(s1, s2);
    }

    public void onClickTV91(View view)
    {
        String s1 = "МЕСТО 9 - " + TV91.getText().toString();
        String s2 = TV92.getText().toString();
        change(s1, s2);
    }

    public void change(String s1, String s2)
    {
        Intent intent = new Intent(this,Main3Activity.class);
        intent.putExtra("s1", s1);
        intent.putExtra("s2", s2);
        startActivity(intent);
    }

}
