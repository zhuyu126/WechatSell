package com.wechat.sell.VO;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

/**
 * Http请求返回的最外层对象
 */
@Data
public class ResultVO<T>  {

    //private static final long serialVersionUID= 5099264180706582104L;
    /** 错误码. */
    private Integer code;
    /** 提示信息. */
    private String msg;
    /** 具体内容. */
    private T data;

}
