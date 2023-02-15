package com.seedon.SeedOnTanda.user.state;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserState implements StateType {
    private boolean phoneNumberValidated;
    private boolean fromRiskJurisdiction;
    private boolean googleTfaEnabled;
    private boolean smsOtpEnabled;

}
