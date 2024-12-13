package me.common.helper;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UserAgentInfo {
    private String device;
    private String os;
    private String osVersion;
}