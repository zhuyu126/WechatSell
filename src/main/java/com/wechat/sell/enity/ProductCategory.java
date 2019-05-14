package com.wechat.sell.enity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
public class ProductCategory implements Serializable {

    private static final long serialVersionUID= -3063050997551563398L;

    @Id
    @GeneratedValue
    /**类目id*/
     private Integer categoryId;
    /**类目名字*/
     private String categoryName;
    /**类目编号*/
     private Integer categoryType;

    private Date createTime;
    private Date updateTime;


    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
