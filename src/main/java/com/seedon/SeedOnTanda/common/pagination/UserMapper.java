package com.seedon.SeedOnTanda.common.pagination;

import com.seedon.SeedOnTanda.enums.roles.RoleValues;
import com.seedon.SeedOnTanda.role.entity.Role;
import com.seedon.SeedOnTanda.user.entity.User;

import java.util.ArrayList;
import java.util.Arrays;

public enum UserMapper implements EnumInterface {

    USER_ID(0) {
        public void mapping(User user, Object[] arr) {
            if (arr[USER_ID.idx] != null)
                user.setId((String) arr[USER_ID.idx]);
        }
    },
    USER_EMAIL(4) {
        public void mapping(User user, Object[] arr) {
            if (arr[USER_EMAIL.idx] != null)
                user.setEmail((String) arr[USER_EMAIL.idx]);
        }
    },
    USER_FIRSTNAME(5) {
        public void mapping(User user, Object[] arr) {
            if (arr[USER_FIRSTNAME.idx] != null)
                user.setFirstName((String) arr[USER_FIRSTNAME.idx]);
        }
    },
    USER_LASTNAME(6) {
        public void mapping(User user, Object[] arr) {
            if (arr[USER_LASTNAME.idx] != null)
                user.setLastName((String) arr[USER_LASTNAME.idx]);
        }
    },
    USER_PASSWORD(7) {
        public void mapping(User user, Object[] arr) {
            if (arr[USER_PASSWORD.idx] != null)
                user.setPassword((String) arr[USER_PASSWORD.idx]);
        }
    },
    USER_PHONENUMBER(8) {
        public void mapping(User user, Object[] arr) {
            if (arr[USER_PHONENUMBER.idx] != null)
                user.setPhoneNumber((String) arr[USER_PHONENUMBER.idx]);
        }
    },
    USER_USERNAME(9) {
        public void mapping(User user, Object[] arr) {
            if (arr[USER_USERNAME.idx] != null)
                user.setUsername((String) arr[USER_USERNAME.idx]);
        }
    },
    USER_ROLES(11) {
        public void mapping(User user, Object[] arr) {
            final var list = new ArrayList<Role>();
            String[] roles = (String[]) arr[USER_ROLES.idx];
            Arrays.stream(roles)
                    .forEach(s -> list.add(new Role(RoleValues.valueOf(s))));
            user.setRoles(list);
        }
    };
    private final int idx;

    UserMapper(int i) {
        idx = i;
    }
}
