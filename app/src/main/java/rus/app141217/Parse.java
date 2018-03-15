package rus.app141217;

import java.util.ArrayList;

public class Parse
{

    int j;
    ArrayList<String> list = new ArrayList<>();

    public void par(String data, int i)
    {
        String str1 = "<br>";
        char[] buf = new char[4];

        for(int j = i; j < 700; j++)
        {
            data.getChars(j, j+4, buf, 0);
            String str2 = new String(buf);

            if(str1.equals(str2))
            {
                char[] num = new char[2];
                data.getChars(j+4, j+6, num, 0);
                String num2 = new String(num);
                int nm = Integer.parseInt(num2);

                String txt = "";
                for(int k = j+6; k < 700; k++)
                {
                    if(data.charAt(k) != '<')
                    {
                        txt = txt + Character.toString(data.charAt(k));
                    }
                    else
                    {
                        list.set(nm, txt);//Здесь пихаем в массив данные из txt
                        this.j = k;
                        break;
                    }
                }
            }
        }
    }




    public String fun(String data, int i)
    {
        String txt = "";
        String str1 = "<br>";
        char[] buf = new char[4];
        data.getChars(i, i+4, buf, 0);
        String str2 = new String(buf);
        if(str1.equals(str2))
        {
            char[] num = new char[2];
            data.getChars(i+4, i+6, num, 0);
            String num2 = new String(num);
            int nm = Integer.parseInt(num2);

            while(data.charAt(i) != '<')
            {
                txt = txt + Character.toString(data.charAt(i));
                i++;
            }
                    //txt = "qwerty";
                    list.add(txt);//Здесь пихаем в массив данные из txt
                    //list.set(nm, txt);//Здесь пихаем в массив данные из txt
        }
        return txt;
    }
}
