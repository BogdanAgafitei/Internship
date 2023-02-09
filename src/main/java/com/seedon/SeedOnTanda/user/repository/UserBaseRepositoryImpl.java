package com.seedon.SeedOnTanda.user.repository;

import com.seedon.SeedOnTanda.common.Encryption;
import com.seedon.SeedOnTanda.common.pagination.Pageable;
import com.seedon.SeedOnTanda.common.pagination.SeedOnPage;
import com.seedon.SeedOnTanda.common.pagination.Sort;
import com.seedon.SeedOnTanda.common.pagination.UserMapper;
import com.seedon.SeedOnTanda.user.dto.UserDTO;
import com.seedon.SeedOnTanda.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;

public class UserBaseRepositoryImpl implements UserBaseRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SeedOnPage<UserDTO> findRequestedData(int page, int size, String[] sortBy, String direction) {
        final var session = entityManager.unwrap(Session.class);
        SeedOnPage<UserDTO> seedOnPage = new SeedOnPage<>();
        Pageable pageable = new Pageable();
        Sort sort = new Sort();
        Query query = entityManager.createNativeQuery("select * from users u join(select ur.user_id as user_id, array_agg(r.role_name) from users_roles ur " +
                "join roles r on r.id = ur.role_id group by user_id) as nw " +
                "on u.id = nw.user_id " +
                "ORDER BY ?1 OFFSET ?2 ROWS FETCH NEXT ?3 ROWS ONLY  ");
        query.setParameter(1, sortBy);
        query.setParameter(2, page * size);
        query.setParameter(3, size);
        List<Object[]> users = query.getResultList();
        Query queryTotal = entityManager.createNativeQuery("SELECT COUNT(*) FROM users ");
        final var countResult = (long) queryTotal.getSingleResult();
        final var usersDto = users.stream()
                .map(this::mappingToUser)
                .map(Encryption::userToDtoMapper)
                .toList();


        seedOnPage.setContent(usersDto);
        sort.setSorted(sortBy != null);
        if (users.isEmpty()) {
            sort.setEmpty(true);
            seedOnPage.setEmpty(true);
        }
        pageable.setSort(sort);
        seedOnPage.setSort(sort);
        pageable.setOffset(page * size);
        pageable.setPageNumber(page);
        pageable.setPageSize(size);
        if (page > -1 && size > 0)
            pageable.setPaged(true);

        seedOnPage.setPageable(pageable);
        if (page == (int) Math.ceil((double) countResult / size)) {
            seedOnPage.setLast(true);
        }
        if (page == 0)
            seedOnPage.setFirst(true);
        seedOnPage.setSize(size);
        seedOnPage.setNumber(page);

        seedOnPage.setNumberOfElements(size);
        seedOnPage.setTotalElements(countResult);
        seedOnPage.setTotalPages((int) Math.ceil((double) countResult / size));
        return seedOnPage;
    }

    private User mappingToUser(Object[] objects) {
        User user = new User();
        Arrays.stream(UserMapper.values())
                .forEach(userMapper -> userMapper.mapping(user, objects));
        return user;
    }


}
