package cn.airanthem.aboot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Ports {
    REGISTER_SERVER(6060),
    REGISTER_CLIENT(6061),
    LOGIN_SERVER(6070),
    LOGIN_CLIENT(6071);

    private final Integer value;
}
