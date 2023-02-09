package com.seedon.SeedOnTanda.common.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeedOnRequestParameters {
    private int page;
    private int size;
    private String sort_by;
    private String direction;
}
