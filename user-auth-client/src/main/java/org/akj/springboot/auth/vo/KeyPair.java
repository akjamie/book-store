package org.akj.springboot.auth.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KeyPair {
    private String kid;
    private String storePass;
    private String storeFilePath;
}
