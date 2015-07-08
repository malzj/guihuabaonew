package com.guihuabao

class CompanyUser {
    String username
    String password
    String cid
    String rid
    String pid
    String name
    String img
    String sex
    String phone
    String bid
    Date dateCreat

    static constraints = {
        username(nullable: true)
        password(nullable: true)
        cid(nullable: true)
        rid(nullable: true)
        pid(nullable: true)
        name(nullable: true)
        img(nullable: true)
        sex(nullable: true)
        phone(nullable: true)
        bid(nullable: true)
        dateCreat(nullable: true)
    }
}
