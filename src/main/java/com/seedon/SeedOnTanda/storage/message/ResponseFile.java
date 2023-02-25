package com.seedon.SeedOnTanda.storage.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseFile {
    private String id;
    private String name;
    private String url;
    private String type;
    private int size;

}
