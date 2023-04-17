package com.hotel.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import org.springframework.core.annotation.AliasFor;

import java.time.LocalDate;

public class Client {
    @ExcelProperty("主键")
    private Integer id;
    @ExcelProperty("客户卡号")
    @ColumnWidth(16)
    private String clientId;
    @ExcelProperty("姓名")
    @ColumnWidth(16)
    private String name;
    @ExcelProperty("性别")
    private String sex;
    @ExcelIgnore
    private Integer age;
    @ExcelProperty("出生日期")
    @ColumnWidth(16)
    private LocalDate birthday;
    @ExcelProperty("手机号")
    @ColumnWidth(16)
    private String phone;
    @ExcelProperty("房间主键")
    private Integer roomId;
    @ExcelProperty("房间名")
    @ColumnWidth(10)
    private String roomName;
    @ExcelProperty("备注")
    @ColumnWidth(30)
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
