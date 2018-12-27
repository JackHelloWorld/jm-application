package com.jmsoft.wxservice.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="wx_access_token")
public class AccessToken implements Serializable {
  private static final long serialVersionUID = -1241062496069804415L;
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  
  @Column(name="a_access_token")
  private String accessToken;
  
  @Column(name="a_expires_in")
  private Long expiresIn;
  
  @Column(name="create_time")
  private Date createTime;
  
  public boolean staleDated()
  {
    if (this.createTime.getTime() + this.expiresIn.longValue() * 1000L <= System.currentTimeMillis()) {
      return true;
    }
    return false;
  }
 
}
