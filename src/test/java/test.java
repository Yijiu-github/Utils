import utils.TsyListUtil;
import utils.TsyListUtil2;
import vo.HoloSecureEvaluationVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class test {
    public static void main(String[] args) {
        // 创建并填充 HoloSecureEvaluationVO 对象的列表
        List<HoloSecureEvaluationVO> list = new ArrayList<>();
        fillListWithRandomData(list, 10); // 填充10万条随机数据

        // 实例化 TsyListUtil 和 TsyListUtil2
        TsyListUtil<HoloSecureEvaluationVO> util1 = new TsyListUtil<>();
        TsyListUtil2<HoloSecureEvaluationVO> util2 = new TsyListUtil2<>();

        // 测试 TsyListUtil
        long startTime = System.currentTimeMillis();
      /*  util1.sortList(list, "occurrenceTime", true);*/
        long endTime = System.currentTimeMillis();
        System.out.println("TsyListUtil - 排序耗时: " + (endTime - startTime) + " ms");

        // 重置列表并测试 TsyListUtil2（串行排序）
        startTime = System.currentTimeMillis();
        util2.sortList(list, "occurrenceTime", false);
        endTime = System.currentTimeMillis();
        System.out.println("TsyListUtil2 - 排序耗时: " + (endTime - startTime) + " ms");

        System.out.println(list);
    }

    private static void fillListWithRandomData(List<HoloSecureEvaluationVO> list, int count) {
        list.clear();
        Random random = new Random();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < count; i++) {
            Date randomDate = new Date(Math.abs(System.currentTimeMillis() - random.nextInt(1000000000)));
            int randomDuration = random.nextInt(100);
            list.add(new HoloSecureEvaluationVO(dateFormat.format(randomDate), randomDuration));
        }
    }
}
