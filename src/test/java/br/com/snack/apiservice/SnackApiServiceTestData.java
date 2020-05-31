package br.com.snack.apiservice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.test.web.servlet.ResultActions;

@Getter
@Setter
@ToString
public class SnackApiServiceTestData {

    private ResultActions resultActions;

    public SnackApiServiceTestData() {
        this.reset();
    }

    public void reset() {
        this.resultActions = null;
    }


}
