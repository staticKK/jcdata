package com.jointcorp.global.base;

import com.jointcorp.common.util.MessageResult;

/**
 *  枚举对象解释器
 *
 * @author Xiao
 * @create 2017-10-18 15:59
 **/
public final class MsgInterpreter {

    private MsgInterpreter() {
    }

    public static MessageResult build(Msg msg) {
        return new MessageResult(msg.getCode(), msg.getInfo(), null);
    }

    public static MessageResult build(Msg msg, Object data) {
        return new MessageResult(msg.getCode(), msg.getInfo(), data);
    }

    public static MessageResult success(Object data) {
        return new MessageResult(data);
    }

    public static MessageResult success() {
        return new MessageResult(Msg.SUCCESS.getCode(),Msg.SUCCESS.getInfo());
    }

    public static MessageResult error() {
        return new MessageResult(Msg.ERROR.getCode(),Msg.ERROR.getInfo());
    }

    public static MessageResult error(Msg msg) {
        return new MessageResult(msg.getCode(),msg.getInfo());
    }

}
