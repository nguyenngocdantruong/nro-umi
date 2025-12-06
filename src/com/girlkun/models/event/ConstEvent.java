/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.girlkun.models.event;

/**
 *
 * @author nguyenngocdantruong
 */
public enum ConstEvent {
    DEBUG(-2),
    MAC_DINH(-1),
    TRUNG_THU (0),
    GIANG_SINH(1),
    HALLOWEEN(2);
    
    public final int value;
    
    ConstEvent(int value){
        this.value = value;
    }
}
