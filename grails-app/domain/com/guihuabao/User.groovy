package com.guihuabao

class User {
    String name
    String city
    String phone
    String cid
    String address
    String username
    String password
    String rid
    Date dateCreat

    static constraints = {
        name(nullable: true)
        city(nullable: true)
        phone(nullable: true)
        cid(nullable: true)
        address(nullable: true)
        username(nullable: true)
        password(nullable: true)
        rid(nullable: true)
        dateCreat(nullable: true)
    }
}
