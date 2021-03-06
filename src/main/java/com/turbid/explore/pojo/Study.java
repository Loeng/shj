package com.turbid.explore.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "study")
@ApiModel(description= "Study")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Study extends BaseEntity {

    @Column(name = "title",   length = 32)
    private String title;

    @Column(name = "price",   length = 32)
    private Integer price;

    @Column(name = "shb",   length = 32)
    private Integer shb;

    @Column(name = "status",   length = 32)
    private Integer status;

    @Column(name = "pricetype",   length = 32)
    private String pricetype;

    @Column(name = "type",   length = 32)
    private String type;

    @Column(name = "indeximgurl",   length = 2000)
    private String indeximgurl;

    @Column(name = "videourl",   length = 2000)
    private String videourl;

    @Column(name = "seecount",  length = 32)
    private Integer seecount;

    @Column(name = "content",  length = 2000)
    private String content;

    @Column(name = "teachername")
    private String teachername;

    @Column(name = "teacherheadurl")
    private String teacherheadurl;

    @Column(name = "teacherdesc",  length = 2000)
    private String teacherdesc;

    public Study(String code, Date create_time, String indeximgurl, Integer seecount, Integer price, String pricetype, Integer shb, String title, String type,String teachername,String teacherheadurl) {
    this.setCode(code);
    this.setCreate_time(create_time);
    this.indeximgurl=indeximgurl;
    this.seecount=seecount;
    this.price=price;
    this.pricetype=pricetype;
    this.shb=shb;
    this.title=title;
    this.type=type;
    this.teachername=teachername;
    this.teacherheadurl=teacherheadurl;
    }

    public Study(String videourl,String code, Date create_time, String indeximgurl, Integer seecount, Integer price, String pricetype, Integer shb, String title, String type) {
       this.videourl=videourl;
        this.setCode(code);
        this.setCreate_time(create_time);
        this.indeximgurl=indeximgurl;
        this.seecount=seecount;
        this.price=price;
        this.pricetype=pricetype;
        this.shb=shb;
        this.title=title;
        this.type=type;
    }

    public Study() {

    }


}
