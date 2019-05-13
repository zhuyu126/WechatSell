package com.wechat.sell.enity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class SellerInfo {

  @Id
  private String sellerId;

  private String username;

  private String password;

  private String openid;

  private Date createTime;

  private Date updateTime;

}
