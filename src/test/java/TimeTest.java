import com.jointcorp.common.util.DateTimeUnit;
import com.jointcorp.common.util.DateTimeUtil;

public class TimeTest {

    public static void main(String[] args) {

        DateTimeUtil.parseLocalDate("2019-07-01", "yyyy-MM-dd");


        System.out.println(DateTimeUtil.format(DateTimeUtil.parseLocalDate("2019-07-01", "yyyy-MM-dd"),DateTimeUnit.ymdSegDot));;




    }
}
