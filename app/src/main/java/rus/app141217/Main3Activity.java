package rus.app141217;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    TextView TV1, TV2, TV3, TV4;
    Button butSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        TV1 = (TextView) findViewById(R.id.TV1);
        TV2 = (TextView) findViewById(R.id.TV2);
        TV3 = (TextView) findViewById(R.id.TV3);
        TV4 = (TextView) findViewById(R.id.TV4);
        butSend = (Button) findViewById(R.id.butSend);


        Intent intent = getIntent();
        String s1 = intent.getStringExtra("s1");
        String s2 = intent.getStringExtra("s2");
        TV1.setText(s1);
        TV2.setText(s2);
        try {
            if (s1.charAt(10) == 'З')
            {
                TV3.setText("Ты хочешь освободить это место?");
                butSend.setText("ОСВОБОДИТЬ");
            }
            else if (s1.charAt(10) == 'С')
            {
                TV3.setText("Ты хочешь занять это место?");
                butSend.setText("ЗАНЯТЬ");
            }
        }
        catch(Exception e)
        {
            //Toast.makeText(this, "Ошибка: " + e.toString(), Toast.LENGTH_LONG).show();
            TV1.setText("Нет данных!");
            TV2.setText("или данные не верны!");
            TV3.setText("возможно нет интернета");
            TV4.setText("нет доступных действий");
        }





    }

    public void onButSend(View view)
    {
        Spinner spinnerHours = (Spinner) findViewById(R.id.spinnerHours);
        Spinner spinnerMinutes = (Spinner) findViewById(R.id.spinnerMinutes);
        Spinner spinnerDays = (Spinner) findViewById(R.id.spinnerDays);
        Spinner spinnerMounts = (Spinner) findViewById(R.id.spinnerMounts);
        Spinner spinnerYears = (Spinner) findViewById(R.id.spinnerYears);

        TV4.setText("до " + spinnerHours.getSelectedItem() + ":" +
                            spinnerMinutes.getSelectedItem() + "   " +
                            spinnerDays.getSelectedItem() + "." +
                            spinnerMounts.getSelectedItem() + "." +
                            spinnerYears.getSelectedItem());


        /*
        if(spinner.getSelectedItemPosition() == 0) {

            //TV3.setText(spinner.getSelectedItem().toString());
            TV4.setText(TV4.getText() + " " + spinner.getSelectedItem());
        }
        else if(spinner.getSelectedItemPosition() == 1)
        {
            TV3.setText("Второй");
        }
        else if(spinner.getSelectedItemPosition() == 2)
        {
            TV3.setText("Третий");
        }
        else
        {
            TV3.setText("Другой");
        }**/
    }

}
