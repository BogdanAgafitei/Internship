package com.seedon.SeedOnTanda.common.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sort {
    private boolean empty;
    private boolean sorted;

    public boolean isUnsorted() {
        return sorted ^= true;
    }
}
