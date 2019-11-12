package com.jointcorp.jcdata.config;

import com.jointcorp.common.util.MessageResult;
import com.jointcorp.global.base.Msg;
import com.jointcorp.global.base.MsgInterpreter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Configuration
@ControllerAdvice
public class MvcConfig {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public MessageResult sysErrorMsg(Exception e) throws Exception{
        e.printStackTrace();
//		if(e instanceof SignIllegalException) {
//			print(response, JsonUtils.objectToJson(MsgInterpreter.error(Msg.SIGN_ERROR)));
//			return;
//		}
//		else if(e instanceof RequestTimeoutException) {
//			print(response, JsonUtils.objectToJson(MsgInterpreter.error(Msg.REQUEST_ERROR)));
//			return;
//		}
        return MsgInterpreter.error(Msg.ERROR);
    }
}
