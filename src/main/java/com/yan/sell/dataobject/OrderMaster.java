package com.yan.sell.dataobject;

import com.yan.sell.enums.OrderStatusEnum;
import com.yan.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@DynamicUpdate
@Data
public class OrderMaster {
    //订单id
    @Id
    private String orderId;

    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    //订单状态，0默认为新下单
    private Integer orderStatus=OrderStatusEnum.NEW.getCode();
    //默认为0未支付
    private Integer payStatus= PayStatusEnum.WAIT.getCode();
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
