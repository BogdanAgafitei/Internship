package com.seedon.SeedOnTanda.user.state;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminState implements StateType {
    private boolean SuperUser;
    private boolean MfaEnabled;
    private boolean BackofficeCompliant;

}
