import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yan.sell.dataobject.OrderDetail;
import org.junit.Test;
import sun.util.resources.hr.CalendarData_hr;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class test {
    @Test
    public void equsls(){
        String a=null;
        Object o1=new Object();
        Object o2=new Object();
        System.out.println(o1.equals(o2));//false 比较内存地址
        //a.equals(new String("b"));
    }
    @Test
    public void compare2(){
        String a="hello";
        String b="hello";
        System.out.println(a==b);//true
        String c=new String("hello");
        System.out.println(a==c);//false
        System.out.println(a.equals(c));//true
        String d=c.intern();
        System.out.println(d==a);//true
    }
    @Test
    public void compare(){
        int a=new Integer("");
        if (a>0){
            System.out.println(1);
        }
    }
    @Test
    public void gson(){
        Gson gson=new Gson();
        String s="[{productId: 1,productQuantity: 2},{productId: 123,productQuantity: \"2\"}]";
        List<OrderDetail> orderDetailList=gson.fromJson(s,new TypeToken<List<OrderDetail>>(){}.getType());
        System.out.println(orderDetailList.size());
        for (OrderDetail orderDetail:orderDetailList){
            System.out.println(orderDetail.getProductId());
        }
    }

    @Test
    public void testDate(){
        Date date=new Date(System.currentTimeMillis());

        java.util.Date date1=new java.util.Date();
        System.out.println(date.getTime());
        System.out.println(date1.getTime());
    }

    @Test
    public void transfrom(){
//        Long number=new Long(124435L);
//        char[] zhonwen=new char[]{'零','一','二','三','四','五','六','七','八','九','十'};
//        char[] wei=new char[]{'亿','万'};
//        String a=String.valueOf(number);
//        char[] ca=a.toCharArray();
//        StringBuilder sb=new StringBuilder();
//        for (int i=0;i<ca.length;i++){
//            if (ca.length>8){
//
//            }
//        }
        int i=1;
        int b=i++;
        System.out.println(b);
    }

    public int[] quwei(Long number){
        String a=String.valueOf(number);
        char[] ca=a.toCharArray();
        int[] weishu=new int[a.length()];
        int lenght=a.length()-1;
        while ((number/10)>0||number!=0){
            weishu[lenght]=ca[lenght]-'0';
            lenght--;
            if (lenght<0){
                break;
            }
            number=(number-(ca[lenght]-'0'))/10;
        }
        return weishu;
    }
}
