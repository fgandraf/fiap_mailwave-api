INSERT INTO
    TBL_USERS (
        USER_ID,
        EMAIL,
        PASSWORD_HASH,
        ACTIVE,
        ROLE,
        CREATED_AT
        )
VALUES (
        1,
        'admin@admin.com',
        '$2a$10$r51z/IXyqVk8waRpYuAK4uzJWKLeeWZXOr3PXOAaySv46JCihp.a2',
        true,
        'ADMIN',
        '1726623692920'
)