package com.seedon.SeedOnTanda.user.repository;

import com.seedon.SeedOnTanda.common.pagination.SeedOnPage;
import com.seedon.SeedOnTanda.user.dto.UserDTO;

public interface UserBaseRepository {
    SeedOnPage<UserDTO> findRequestedData(int page, int size, String[] sortBy, String direction);
}
