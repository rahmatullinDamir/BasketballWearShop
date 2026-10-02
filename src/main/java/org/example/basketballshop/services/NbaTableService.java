package org.example.basketballshop.services;

import org.example.basketballshop.dto.nbaTableResponse.NbaTableResponse;

public interface NbaTableService {
    NbaTableResponse[] getNbaTableData(String conference);
}
