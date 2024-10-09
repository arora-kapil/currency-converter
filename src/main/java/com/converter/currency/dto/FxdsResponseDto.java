package com.converter.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FxdsResponseDto {
    private List<FxdsCurrencyDataDto> response;

    public List<FxdsCurrencyDataDto> getResponse() {
        return null == response ? Collections.emptyList() : response;
    }

}
