package vo;

import lombok.Data;

@Data
public class HoloSecureEvaluationVO{

    private static final long serialVersionUID = 1L;


    private String occurrenceTime;

    private Integer duration;


    public HoloSecureEvaluationVO( String s, Integer number3) {

        this.occurrenceTime = s;
        this.duration = number3;

    }
}
