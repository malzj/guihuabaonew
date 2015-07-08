package com.guihuabao

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.multipart.MultipartFile

class FrontController {

    def index() {
        print("!")
    }

    def login(){
        def username= params.username
        def password = params.password
        def companyUser = CompanyUser.findByUsernameAndPassword(username,password)
        if(!companyUser){
            flash.message = "您的账号密码输入有误"
           redirect(action: "index",id:1,msg:"您的账号密码输入有误")
            return
        }else {
            def date= new Date()
            def company= Company.get(companyUser.cid)
            if (company.dateUse>date){
                session.user=companyUser
                session.company=company
                redirect(action: "companyUserList")
            }else {
                flash.message ="您的公司账号已过期"
                redirect(action: "index",msg:"您的公司账号已过期")
                return
            }
        }

    }
    def frontIndex(){

    }
    def companyUserCreate() {
        def bumenList = Bumen.findAllByCid(session.company.id)
        [companyUserInstance: new CompanyUser(params), bumenList: bumenList]
    }
    def companyUserSave(){
        def companyUserInstance = new CompanyUser(params)
        if(params.pid==1){
            companyUserInstance.pid = 2
        }
        if (!companyUserInstance.save(flush: true)) {
            render(view: "companyUserCreate", model: [companyUserInstance: companyUserInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'companyUser.label', default: 'companyUser'), companyUserInstance.id])
        redirect(action: "companyUserShow", id: companyUserInstance.id)
    }
    def companyUserList(Integer max){
        params.max = Math.min(max ?: 10, 100)
        [companyUserInstanceList: CompanyUser.list(params), companyUserInstanceTotal: CompanyUser.count()]
    }
    def companyUserShow(Long id) {
        def companyUserInstance = CompanyUser.get(id)
        if (!companyUserInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'companyUser.label', default: 'CompanyUser'), id])
            redirect(action: "companyUserList")
            return
        }

        [companyUserInstance: companyUserInstance]
    }
    def companyUserEdit(Long id) {
        def companyUserInstance = CompanyUser.get(id)
        def bumenList = Bumen.findAllByCid(session.company.id)
        if (!companyUserInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'companyUser.label', default: 'CompanyUser'), id])
            redirect(action: "companyUserList")
            return
        }

        [companyUserInstance: companyUserInstance, bumenList: bumenList]
    }
    def companyUserUpdate(Long id, Long version) {
        def companyUserInstance = CompanyUser.get(id)

        if (!companyUserInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'companyUser.label', default: 'CompanyUser'), id])
            redirect(action: "companyUserList")
            return
        }

        if (version != null) {
            if (companyUserInstance.version > version) {
                companyUserInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'companyUser.label', default: 'CompanyUser')] as Object[],
                        "Another user has updated this User while you were editing")
                render(view: "companyUserEdit", model: [companyUserInstance: companyUserInstance])
                return
            }
        }

        companyUserInstance.properties = params

        if (!companyUserInstance.save(flush: true)) {
            render(view: "companyUserEdit", model: [companyUserInstance: companyUserInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'companyUser.label', default: 'CompanyUser'), companyUserInstance.id])
        redirect(action: "companyUserShow", id: companyUserInstance.id)
    }
    def companyUserDelete(Long id) {
        def companyUserInstance = CompanyUser.get(id)
        if (!companyUserInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'companyUser.label', default: 'CompanyUser'), id])
            redirect(action: "companyUserList")
            return
        }

        try {
            companyUserInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'companyUser.label', default: 'CompanyUser'), id])
            redirect(action: "companyUserList")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'companyUser.label', default: 'CompanyUser'), id])
            redirect(action: "companyUserShow", id: id)
        }
    }


    def bumenList(Integer max){
        params.max = Math.min(max ?: 10, 100)
        [bumenInstanceList:Bumen.list(params), bumenInstanceTotal: Bumen.count()]
    }
    def bumenCreate(){
        [bumenInstance: new Bumen(params)]
    }
    def bumenSave(){
        def bumenInstance = new Bumen(params)
        if (!bumenInstance.save(flush: true)) {
            render(view: "bumenCreate", model: [bumenInstance: bumenInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'bumen.label', default: 'companyUser'), bumenInstance.id])
        redirect(action: "bumenShow", id: bumenInstance.id)
    }
    def bumenShow(Long id) {
        def bumenInstance = Bumen.get(id)
        if (!bumenInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bumen.label', default: 'Bumen'), id])
            redirect(action: "bumenList")
            return
        }

        [bumenInstance: bumenInstance]
    }
    def bumenEdit(Long id) {
        def bumenInstance = Bumen.get(id)
        if (!bumenInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bumen.label', default: 'Bumen'), id])
            redirect(action: "bumenList")
            return
        }

        [bumenInstance: bumenInstance]
    }
    def bumenUpdate(Long id, Long version) {
        def bumenInstance = Bumen.get(id)
        if (!bumenInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bumen.label', default: 'Bumen'), id])
            redirect(action: "bumenList")
            return
        }

        if (version != null) {
            if (bumenInstance.version > version) {
                bumenInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'bumen.label', default: 'Bumen')] as Object[],
                        "Another user has updated this User while you were editing")
                render(view: "bumenEdit", model: [bumenInstance: bumenInstance])
                return
            }
        }

        bumenInstance.properties = params

        if (!bumenInstance.save(flush: true)) {
            render(view: "bumenEdit", model: [bumenInstance: bumenInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'bumen.label', default: 'Bumen'), bumenInstance.id])
        redirect(action: "bumenShow", id: bumenInstance.id)
    }
    def bumenDelete(Long id) {
        def bumenInstance = Bumen.get(id)
        if (!bumenInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bumen.label', default: 'Bumen'), id])
            redirect(action: "bumenList")
            return
        }

        try {
            bumenInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'bumen.label', default: 'Bumen'), id])
            redirect(action: "bumenList")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bumen.label', default: 'Bumen'), id])
            redirect(action: "bumenShow", id: id)
        }
    }


    def companyRoleList(Integer max){
        params.max = Math.min(max ?: 10, 100)
        [companyRoleInstanceList: CompanyRole.list(params), companyRoleInstanceTotal: CompanyRole.count()]
    }
    def companyRoleCreate(){
        [companyRoleInstance: new CompanyRole(params)]
    }
    def companyRoleSave(){
        def companyRoleInstance = new CompanyRole(params)
        if(!companyRoleInstance.save(flush: true)){
            render(view: "companyRoleCreate", model: [companyRoleInstance: companyRoleInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'companyRole.label', default: 'companyRole'), companyRoleInstance.id])
        redirect(action: "companyRoleShow", id: companyRoleInstance.id)
    }
    //和许助手
    def hxhelper(Integer max){
        params.max = Math.min(max ?: 10, 100)
        [bookInstanceList: Book.list(params), bookInstanceTotal: Book.count()]
    }
    def book(Integer max,Long id){
        params.max = Math.min(max ?: 2, 100)
        params<<[sort: "id",order: "asc"]
        def offset = 0;
        if (params.offset>0){
            offset =params.offset
        }
        params<<[offset:offset]
        def syll = Syllabus.findAllByBook(Book.get(id),[sort:"id", order:"asc"])
        def bookInstance = Book.get(id)
        def syllabus  = Syllabus.findByBook(Book.get(id))
        def chapter = Chapter.findBySyllabus(syllabus)
        def contentlist = Content.findAllByChapter(chapter,params)
        def contentsize= Content.countByChapter(chapter)
        def content=""
        def content1 =""

        if(contentlist.size()>0){
            content= contentlist.get(0).introduction
            if (contentlist.size()>1){
                content1=contentlist.get(1).introduction
            }
        }
        if(!bookInstance){
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'book.label', default: 'Book'), id])
            redirect(action: "hxhelper")
            return
        }

        [bookInstance: bookInstance,syllabusInstanceList: syll,content:content,content1:content1,contentsize:contentsize,bookId:id,offset: offset,syllabusname:syllabus.syllabusName,chaptername:chapter.chapterName]
    }
    def chapterBook(Integer max,Long id){
        params.max = Math.min(max ?: 2, 100)
        params<<[sort: "id",order: "asc"]
        def offset = 0;
        if (params.offset>0){
            offset =params.offset
        }
        params<<[offset:offset]
        def chapter = Chapter.get(id)
        def syllabus=chapter.syllabus
        def bookId=chapter.syllabus.book.id
        def syllabusInstanceList = Syllabus.findAllByBook(Book.get(bookId),[sort:"id", order:"asc"])
        def bookInstance = Book.get(bookId)
        def contentlist = Content.findAllByChapter(chapter,params)
        def contentsize= Content.countByChapter(chapter)
        def content=""
        def content1 =""

        if(contentlist.size()>0){
            content= contentlist.get(0).introduction
            if (contentlist.size()>1){
                content1=contentlist.get(1).introduction
            }
        }
        [bookInstance: bookInstance,content:content,content1:content1,contentsize:contentsize,syllabusInstanceList:syllabusInstanceList,bookId: chapter.id,offset: offset,syllabusname:syllabus.syllabusName,chaptername:chapter.chapterName]
    }
    //系统设置

    //功能介绍
    def funIntroduction(Long id){
        def funIntroduction = FunIntroduction.get(id)
        if (!funIntroduction) {
//            flash.message = message(code: 'default.not.found.message', args: [message(code: 'company.label', default: 'Company'), id])
            redirect(action: "list")
            return
        }
        [funIntroduction: funIntroduction]
    }
    //系统通知
    def inform(Long id){
        def informInstance = Inform.get(id)
        if (!informInstance) {
//            flash.message = message(code: 'default.not.found.message', args: [message(code: 'company.label', default: 'Company'), id])
            redirect(action: "list")
            return
        }
        [informInstance: informInstance]
    }
    //检查版本
    def banben(Long id){
        def banbenInstance = Banben.get(id)
        if (!banbenInstance) {
//            flash.message = message(code: 'default.not.found.message', args: [message(code: 'company.label', default: 'Company'), id])
            redirect(action: "list")
            return
        }
        [banbenInstance: banbenInstance]
    }
    //使用条款
    def clause(Long id){
        def clauseInstance = Banben.get(id)
        if (!clauseInstance) {
//            flash.message = message(code: 'default.not.found.message', args: [message(code: 'company.label', default: 'Company'), id])
            redirect(action: "list")
            return
        }
        [clauseInstance: clauseInstance]
    }
    //帮助与反馈
    def feedback(String id){
       def a=id
      if (a){
          [msg:"已提交"]
      }else {
         [msg:""]
      }
    }
    def feedbackSave(){

        def feedbackInstance = new Feedback(params)
        feedbackInstance.userId=session.user.id
        feedbackInstance.username=session.user.username
        if (!feedbackInstance.save(flush: true)) {
            render(view: "create", model: [feedbackInstance: feedbackInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'feedback.label', default: 'Feedback'), feedbackInstance.id])
        redirect(action: "feedback", id: feedbackInstance.id,msg:"已提交")
    }
    //个人设置
    def personalSet(){
        def userInstance = CompanyUser.get(session.user.id)
        def bumen = Bumen.findByCidAndId(session.company.id,session.user.bid)
        def role = Role.findById(session.user.pid)
        if(!userInstance){
            redirect(action: "login")
        }
        [userInstance: userInstance,bumen: bumen,role: role]
    }
    def personalUpdate(Long id, Long version){
        def companyUserInstance = CompanyUser.get(id)
        def filePath
        def fileName
        MultipartFile f = request.getFile('file1')
        if(!f.empty) {
            fileName=f.getOriginalFilename()
            filePath="web-app/images/"
            f.transferTo(new File(filePath+fileName))
        }

        if(fileName){
        companyUserInstance.img=fileName
        }
        if (!companyUserInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'companyUser.label', default: 'CompanyUser'), id])
            redirect(action: "personalSet")
            return
        }

        if (version != null) {
            if (companyUserInstance.version > version) {
                companyUserInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'companyUser.label', default: 'CompanyUser')] as Object[],
                        "Another user has updated this User while you were editing")
                render(view: "personalSet", model: [companyUserInstance: companyUserInstance])
                return
            }
        }

        companyUserInstance.properties = params

        if (!companyUserInstance.save(flush: true)) {
            render(view: "personalSet", model: [companyUserInstance: companyUserInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'companyUser.label', default: 'CompanyUser'), companyUserInstance.id])
        redirect(action: "personalSet", id: companyUserInstance.id)
    }
    //修改密码
    def checkPassword(){
        def rs =[:]
        def user = CompanyUser.get(session.user.id)
        def password = params.oldpassword
        if (user.password==password){
            rs.msg=true
        }else {
            rs.msg=false
        }


        if (params.callback) {
            render "${params.callback}(${rs as JSON})"
        } else
            render rs as JSON
    }

    def passwordUpdate(){
        def rs =[:]
        def password = params.oldpassword
        def userInstance = CompanyUser.get(session.user.id)
        def newpassword = params.newpassword
        def version = params.version.toLong()

//        if (!userInstance) {
//            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bumen.label', default: 'Bumen'), id])
//            redirect(action: "personalSet")
//            return
//        }

        if (version != null) {
            if (userInstance.version > version) {
                userInstance.errors.rejectValue("user", "default.optimistic.locking.failure",
                        [message(code: 'user.label', default: 'user')] as Object[],
                        "Another user has updated this User while you were editing")
                render(view: "personalSet", model: [userInstance: userInstance])
                return
            }
        }

        if (userInstance.password==password&&(!newpassword.empty)){
            userInstance.password = newpassword
            if (userInstance.save(flush: true)) {
                rs.msg=true
            }
        }else {
            rs.msg=false
        }

        if (params.callback) {
            render "${params.callback}(${rs as JSON})"
        } else
            render rs as JSON
    }
    //周报
    def zhoubao(){
        print(1)
        def date = new Date()
        def info = Zhoubao.findByYearAndMouthAndWeek()
        [info: info]
    }
}
