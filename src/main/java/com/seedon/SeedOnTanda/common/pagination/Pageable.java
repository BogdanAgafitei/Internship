package com.seedon.SeedOnTanda.common.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pageable {
    private Sort sort;
    private int offset;
    private int pageNumber;
    private int pageSize;
    private boolean paged;

    public boolean isUnpaged() {
        return paged ^= true;
    }
}
