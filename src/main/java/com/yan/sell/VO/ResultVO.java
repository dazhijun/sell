package com.yan.sell.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVO<T> implements Serializable{

    private static final long serialVersionUID = -8203555717855703733L;

    private Integer code;

    private String msg;

    private T data;
}
