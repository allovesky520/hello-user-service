package com.springboot.demo.constant;

import lombok.Data;

import java.io.Serializable;


@Data
public class EmptyData implements Serializable {

    private transient String empty;
}
