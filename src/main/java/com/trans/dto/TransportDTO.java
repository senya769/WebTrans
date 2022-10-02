package com.trans.dto;

import com.trans.model.enums.TypeTransport;
import com.trans.model.User;
import lombok.Data;

@Data
public class TransportDTO {
    private double maxCapacityLoad;
    private boolean isFree = true;
    private TypeTransport typeTransport;
    private User user;
}
