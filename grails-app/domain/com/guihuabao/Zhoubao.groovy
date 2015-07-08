package com.guihuabao

class Zhoubao {
    String year
    String mouth
    String week
    String performance
    String xinde
    String plan
    String cooperate
    Date dateCreate
    static constraints = {
        year(nullable: true)
        mouth(nullable: true)
        week(nullable: true)
        performance(nullable: true)
        xinde(nullable: true)
        plan(nullable: true)
        cooperate(nullable: true)
        dateCreate(nullable: true)
    }

}