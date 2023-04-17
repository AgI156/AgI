package com.hotel.model;


import java.time.LocalDate;

/**
 * 用于封装客户查询
 */
public class ClientSearchBean extends Client {

    private Integer ageFrom;
    private Integer ageTo;

    private LocalDate birthdayFrom;
    private LocalDate birthdayTo;


    public Integer getAgeFrom() {
        return ageFrom;
    }

    public void setAgeFrom(Integer ageFrom) {
        this.ageFrom = ageFrom;
    }

    public Integer getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(Integer ageTo) {
        this.ageTo = ageTo;
    }

    public LocalDate getBirthdayFrom() {
        return birthdayFrom;
    }

    public void setBirthdayFrom(LocalDate birthdayFrom) {
        this.birthdayFrom = birthdayFrom;
    }

    public LocalDate getBirthdayTo() {
        return birthdayTo;
    }

    public void setBirthdayTo(LocalDate birthdayTo) {
        this.birthdayTo = birthdayTo;
    }


}
