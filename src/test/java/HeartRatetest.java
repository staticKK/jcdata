import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeartRatetest {

    public static void main(String[] args) {

        List<String[]> y = new ArrayList<>();

        String s = "-,-,-,-,12,23,13,34,-,-,-,-,343,-,24,342,53,23,-,-,-,-,-,234,263,642,34,55,-,-,32,88";

        String[] details = s.split(",");


        int lastIndex = 0;
        for(int i = 0; i < details.length;i++) {

            String value = details[i];
            if(!"-".equals(value)) {
                boolean k = continuously(details,i);
                if(k) {
                    if(lastIndex == 0) {
                        //断开
                        String[] values = createStringArraySetValue(i+1);
                        System.arraycopy(details,lastIndex,values,0,i + 1);
                        y.add(values);
                    } else {
                        //断开
                        String[] values = createStringArraySetValue(i+1);
                        System.arraycopy(details,lastIndex+1,values,lastIndex+1,i-lastIndex);
                        y.add(values);
                    }
                    lastIndex = i;
                }
            }
        }
        if(lastIndex == 0) {
            y.add(details);
        }


    }

    private static boolean continuously(String[] details,int index) {

        for(int i = index+1; i < index+4;i++) {
            if(i==32) {
                return true;
            }
            String value = details[i];
            if(!"-".equals(value)) {
                return false;
            }
        }
        return true;
    }

    private static String[] createStringArraySetValue(int size) {
        return Arrays.stream(new String[size]).map(str -> "value").toArray(String[]::new);
    }
}
