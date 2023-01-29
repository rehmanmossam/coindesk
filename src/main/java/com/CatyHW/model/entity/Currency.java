package com.CatyHW.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="CURRENCY")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CURRENCY_CODE", nullable = false)
    private String currencyCode;

    @Column(name = "CHINESE_NAME", nullable = false)
    private String chineseName;

    @CreationTimestamp
    @Column(name="CREATE_TIME", nullable=false, updatable=false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss",  timezone = "GMT+8")
    private Date createdTime;

    @UpdateTimestamp
    @Column(name="UPDATE_TIME")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss",  timezone = "GMT+8")
    private Date updateTime;
}
