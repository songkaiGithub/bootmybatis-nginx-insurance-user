package com.po;

import java.io.Serializable;
import java.util.Date;

public class Insurance implements Serializable {
    private Integer id;//注册的编号
    private String usercode;//注册的账号(手机号或则邮箱号)
    private String userpasspword;//注册的密码
    private  Integer userType;//注册的类型(0 管理员 1自注册用户  2 保险销售部门 3 风险合规部)
    private String userName;//注册用户的姓名
    private String weChat;//注册用户的联系方式
    private  String idnumber;//注册用户的身份证
    private Date creationDate;//帐户创建的时间
    private String createdBy;//创建人
    private Date modifyDate;//修改的时间
    private  String modifiedBy;//修改人
    private  Integer activated;//用户注册是否激活(0 未激活，1 已激活 默认是0)

    public Insurance() {
    }

    public Insurance(Integer id, String usercode, String userpasspword, Integer userType, String userName, String weChat, String idnumber, Date creationDate, String createdBy, Date modifyDate, String modifiedBy, Integer activated) {
        this.id = id;
        this.usercode = usercode;
        this.userpasspword = userpasspword;
        this.userType = userType;
        this.userName = userName;
        this.weChat = weChat;
        this.idnumber = idnumber;
        this.creationDate = creationDate;
        this.createdBy = createdBy;
        this.modifyDate = modifyDate;
        this.modifiedBy = modifiedBy;
        this.activated = activated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getUserpasspword() {
        return userpasspword;
    }

    public void setUserpasspword(String userpasspword) {
        this.userpasspword = userpasspword;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Integer getActivated() {
        return activated;
    }

    public void setActivated(Integer activated) {
        this.activated = activated;
    }

    @Override
    public String toString() {
        return "Insurance{" +
                "id=" + id +
                ", usercode='" + usercode + '\'' +
                ", userpasspword='" + userpasspword + '\'' +
                ", userType=" + userType +
                ", userName='" + userName + '\'' +
                ", weChat='" + weChat + '\'' +
                ", idnumber='" + idnumber + '\'' +
                ", creationDate=" + creationDate +
                ", createdBy='" + createdBy + '\'' +
                ", modifyDate=" + modifyDate +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", activated=" + activated +
                '}';
    }
}


