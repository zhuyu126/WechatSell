package com.wechat.sell.enity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class SellerInfo implements Serializable {

  private static final long serialVersionUID= 3995458585427870144L;

  @Id
  private String sellerId;

  private String username;

  private String password;

  private String openid;

  private Date createTime;

  private Date updateTime;

}
