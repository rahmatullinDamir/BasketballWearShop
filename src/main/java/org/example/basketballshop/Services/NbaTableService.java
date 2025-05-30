package org.example.basketballshop.Services;

import org.example.basketballshop.DTO.NbaTableResponse.NbaTableResponse;

public interface NbaTableService {
    NbaTableResponse[] getNbaTableData(String conference);
}
