package com.anil.erp.common;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErpsystemResponse {
 private Map<String, Object> erpSystemResponse = new HashMap<>();
}
