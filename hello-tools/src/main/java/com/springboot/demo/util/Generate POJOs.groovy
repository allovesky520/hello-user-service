package com.springboot.demo.util
//
//
//import com.intellij.database.model.DasTable
//import com.intellij.database.model.ObjectKind
//import com.intellij.database.util.Case
//import com.intellij.database.util.DasUtil
//
//import java.text.SimpleDateFormat
//
//config = [
//        impSerializable  : true,
//        extendBaseEntity : true,
//        extendBaseService: false
//
//]
//baseEntityPackage = "com.springboot.demo.domin.base.BaseEntity"
//baseEntityProperties = ["createTime", "createBy", "updateTime", "updateBy","status"]
//typeMapping = [
//        (~/(?i)tinyint|smallint|mediumint/)         : "Integer",
//        (~/(?i)int/)                                : "Long",
//        (~/(?i)bool|bit/)                           : "Boolean",
//        (~/(?i)float|double|decimal|real/)          : "BigDecimal",
//        (~/(?i)datetime|timestamp|date|time/)       : "Date",
//        (~/(?i)blob|binary|bfile|clob|raw|image/)   : "InputStream",
//        (~/(?i)/)                                   : "String"
//]
////baseServicePackage = "com.springboot.demo.domin.base.BaseService"
//
//FILES.chooseDirectoryAndSave("Choose directory", "Choose where to store generated files") { dir ->
//    SELECTION.filter {
//        it instanceof DasTable && it.getKind() == ObjectKind.TABLE
//    }.each {
//        generate(it, dir)
//    }
//}
//def generate(table, dir) {
//    def entityName = javaName(table.getName(), true)
//    //前缀,需要过滤掉的
//    def prefix ='t'
//    if(isNotEmpty(prefix)){ //如果不为空,则需要删除
//        entityName =entityName[prefix.length()..-1]
//    }
//    def  entityPackName =Case.LOWER.apply(entityName)
//    def entityPath = "${dir.toString()}\\${entityPackName}\\entity",
//        servicePath = "${dir.toString()}\\${entityPackName}\\service",
//        repPath = "${dir.toString()}\\${entityPackName}\\repository",
//        serviceImpPath = "${dir.toString()}\\${entityPackName}\\service\\impl",
//        controllerPath = "${dir.toString()}\\${entityPackName}\\controller"
//
//    mkdirs([entityPath, servicePath, repPath, serviceImpPath,controllerPath])
//
//    def fields = calcFields(table)
//    def basePackage = clacBasePackage(dir)
//    basePackage="${basePackage}.${entityPackName}"
//
//    def author="yyzh"
//
//    new File("${entityPath}\\${entityName}Entity.java").withPrintWriter("UTF-8") { out -> genEntity(out, table, entityName, fields, basePackage, author) }
//    new File("${servicePath}\\${entityName}Service.java").withPrintWriter("UTF-8") { out -> genService(out, table, entityName, fields, basePackage) }
//    new File("${repPath}\\${entityName}Repository.java").withPrintWriter("UTF-8") { out -> genRepository(out, table, entityName, fields, basePackage, author) }
//    new File("${serviceImpPath}\\${entityName}ServiceImpl.java").withPrintWriter("UTF-8") { out -> getServiceImpl(out, table, entityName, fields, basePackage) }
//    new File("${controllerPath}\\${entityName}Controller.java").withPrintWriter("UTF-8") { out -> getController(out, table, entityName, fields, basePackage) }
//
//}
//def getTableComment(fields){
//    def tableComment =fields[0].comment
//    if(isNotEmpty(tableComment)){
//        tableComment=tableComment[0..-4]
//    }
//
//}
//def genProperty(out, field) {
//    if (field.annos != "") out.println "  ${field.annos}"
//    if (field.colum != field.name) {
//        out.println "\t@Column(name = \"${field.colum}\")"
//    }
//    // 输出注释
//    if (isNotEmpty(field.comment)) {
//        out.println "\t/**"
//        out.println "\t * ${field.comment}"
//        out.println "\t */"
//    }
//    out.println "\tprivate ${field.type} ${field.name};"
//    out.println ""
//}
//def isNotEmpty(content) {
//    return content != null && content.toString().trim().length() > 0
//}
//
//def genEntity(out, table, entityName, fields, basePackage, author) {
//    def tableComment= getTableComment(fields)
//    out.println "package ${basePackage}.entity;"
//    out.println ""
//    if (config.extendBaseEntity) {
//        out.println "import $baseEntityPackage;"
//    }
//    out.println "import lombok.Data;"
//    out.println "import lombok.AllArgsConstructor;"
//    out.println "import lombok.NoArgsConstructor;"
//    out.println "import lombok.Builder;"
//    Set types = new HashSet()
//
//    fields.each() {
//        types.add(it.type)
//    }
//
//    if (types.contains("Date")) {
//        out.println "import java.util.Date;"
//    }
//
//    if (types.contains("BigDecimal")) {
//        out.println "import java.math.BigDecimal;"
//    }
//
//    if (types.contains("InputStream")) {
//        out.println "import java.io.InputStream;"
//    }
//
//    out.println ""
//    if (config.impSerializable) {
//        out.println "import java.io.Serializable;"
//        out.println ""
//    }
//    out.println "import javax.persistence.*;"
//    out.println "import lombok.EqualsAndHashCode;"
//    out.println ""
//    out.println "/**"
//    out.println "/** ${tableComment} entity "
//    out.println "* @Author ${author}"
//    out.println "* @Date " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " \n"
//    out.println "*/"
//    out.println ""
//    out.println "@Data"
//    out.println "@Entity"
//    out.println "@AllArgsConstructor"
//    out.println "@NoArgsConstructor"
//    out.println "@Builder"
//    out.println "@EqualsAndHashCode(callSuper = false)"
//    out.println "@Table(name = \"${table.getName()}\")"
//    out.println "public class ${entityName}Entity extends BaseEntity implements Serializable {"
//    out.println ""
//    out.println genSerialID()
//    out.println ""
//    if (config.extendBaseEntity) {
//        // 继承父类
//        out.println "\t@Id"
//        out.println "\t@GeneratedValue(strategy = GenerationType.IDENTITY)"
//        fields.each() { if (!isBaseEntityProperty(it.name)) genProperty(out, it) }
//    } else {
//        // 不继承父类
//        out.println "\t@Id"
//        out.println "\t@GeneratedValue(strategy = GenerationType.IDENTITY)"
//        fields.each() { genProperty(out, it) }
//    }
//    out.println "}"
//
//}
////生成序列化的serialVersionUID
//static String genSerialID() {
//    return "\tprivate static final long serialVersionUID =  " + Math.abs(new Random().nextLong()) + "L;"
//}
//
//
//def genRepository(out, table, entityName, fields, basePackage, author) {
//    def tableComment= getTableComment(fields)
//    out.println "package ${basePackage}.repository;"
//    out.println ""
//    out.println "import ${basePackage}.entity.${entityName}Entity;"
//    out.println "import org.springframework.data.jpa.repository.JpaRepository;"
//    out.println "import org.springframework.data.jpa.repository.JpaSpecificationExecutor;"
//    out.println "import org.springframework.stereotype.Repository;"
//    out.println ""
//    out.println "/**"
//    out.println "/** ${tableComment} repository"
//    out.println "* @Author ${author}"
//    out.println "* @Date " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " \n"
//    out.println "*/"
//    out.println ""
//    out.println "@Repository"
//    out.println "public interface ${entityName}Repository extends JpaRepository<${entityName}Entity, ${fields[0].type}>, JpaSpecificationExecutor<${entityName}Entity>{}"
//}
//
//def genService(out, table, entityName, fields, basePackage) {
//    def tableComment= getTableComment(fields)
//    def getByIdLog=new String("根据ID获取{tableComment}唯一数据".getBytes("gbk"),"utf-8")
//    def deleteByIdLog=new String("根据ID删除{tableComment}唯一数据".getBytes("gbk"),"utf-8")
//    def saveOneLog=new String("新增{tableComment} 信息".getBytes("gbk"),"utf-8")
//    def updateLog=new String("修改 {tableComment}信息".getBytes("gbk"),"utf-8")
//    def queryByPageLog=new String("分页查找{tableComment}数据".getBytes("gbk"),"utf-8")
////    getByIdLog=getByIdLog.replace("{tableComment}",tableComment)
////    deleteByIdLog=deleteByIdLog.replace("{tableComment}",tableComment)
////    saveOneLog=saveOneLog.replace("{tableComment}",tableComment)
////    updateLog=updateLog.replace("{tableComment}",tableComment)
////    queryByPageLog=queryByPageLog.replace("{tableComment}",tableComment)
//    out.println "package ${basePackage}.service;"
//    out.println ""
//    out.println "import com.springboot.demo.constant.Result;"
//    out.println "import org.springframework.data.domain.Page;"
//    out.println "import ${basePackage}.entity.${entityName}Entity;"
////    if (config.extendBaseService) {
////        out.println "import ${basePackage}.entity.$entityName;"
////    }
//    out.println ""
//    out.println "/**\n"
//    out.println "/** ${tableComment} service interface"
//    out.println "* @Author yyzh\n"
//    out.println "* @Date " +new java.util.Date().toString()
//    out.println "*/"
//    out.println ""
//    out.println "public interface ${entityName}Service{"
//    out.println ""
//    out.println " /**"
//    out.println "  * ${getByIdLog}"
//    out.println "  */"
//    out.println "   Result getOneById(${fields[0].type} id);"
//    out.println ""
//    out.println " /**"
//    out.println "  *  ${deleteByIdLog}"
//    out.println "  */"
//    out.println "   Result deleteById(${fields[0].type} id);"
//    out.println ""
//    out.println " /**"
//    out.println " *  ${saveOneLog}"
//    out.println " */"
//    out.println "   Result saveOne(${entityName}Entity entity);"
//    out.println ""
//    out.println " /**"
//    out.println " * ${updateLog} "
//    out.println " */"
//    out.println "   Result updateEntity(${entityName}Entity entity);"
//    out.println ""
//    out.println " /**"
//    out.println " * ${queryByPageLog}"
//    out.println " */"
//    out.println "   Result<Page<${entityName}Entity>> getByPage(${entityName}Entity entity, Integer page, Integer pageSize);"
//    out.println "}"
//}
//
//def getServiceImpl(out, table, entityName, fields, basePackage) {
//
//    def tableComment= getTableComment(fields)
//
//    out.println "package ${basePackage}.service.impl;"
//    out.println ""
//    out.println "import org.springframework.stereotype.Service;"
//    out.println "import com.springboot.demo.constant.Result;"
//    out.println "import org.springframework.beans.factory.annotation.Autowired;"
//    out.println "import javax.persistence.criteria.*;"
//    out.println "import javax.transaction.Transactional;"
//    out.println "import org.springframework.data.domain.Page;"
//    out.println "import org.springframework.data.domain.PageRequest;"
//    out.println "import org.springframework.data.jpa.domain.Specification;"
//    out.println "import java.util.ArrayList;"
//    out.println "import java.util.List;"
//    out.println "import org.springframework.data.domain.Sort;"
//
//    out.println "import ${basePackage}.entity.${entityName}Entity;"
//    out.println "import ${basePackage}.service.${entityName}Service;"
//    out.println "import ${basePackage}.repository.${entityName}Repository;"
//    out.println ""
//    out.println "/**\n"
//    out.println "/** ${tableComment} service impl"
//    out.println "* @Author yyzh\n"
//    out.println "* @Date " + new java.util.Date().toString()
//    out.println "*/"
//    out.println ""
//    out.println "@Service"
//    out.println "public class ${entityName}ServiceImpl implements ${entityName}Service {"
//    out.println ""
//    out.println "\t@Autowired"
//    out.println "\tprivate ${entityName}Repository rep;"
//    out.println ""
//    out.println "\t@Override"
//    out.println "\tpublic Result getOneById(${fields[0].type} id){"
//    out.println " \t\t${entityName}Entity one = rep.getOne(id);"
//    out.println "\t\treturn Result.ok(one);"
//    out.println "\t}"
//    out.println ""
//    out.println "\t@Override"
//    out.println "\tpublic Result deleteById(${fields[0].type} id){"
//    out.println " \t\trep.deleteById(id);"
//    out.println "\t\treturn Result.ok();"
//    out.println "\t}"
//    out.println ""
//    out.println ""
//    out.println "\t@Override"
//    out.println "\tpublic Result updateEntity(${entityName}Entity entity){"
//    out.println "\tif(rep.getOne(entity.getId() ) ==null) return Result.failed(0,\"该记录不存在!\");"
//    out.println " \t\trep.saveAndFlush(entity);"
//    out.println "\t\treturn Result.ok();"
//    out.println "\t}"
//    out.println ""
//    out.println "\t@Override"
//    out.println "\t@Transactional(rollbackOn = Exception.class)"
//    out.println "\tpublic Result saveOne(${entityName}Entity entity){"
//    out.println " \t\t${entityName}Entity one = rep.save(entity);"
//    out.println "\t\treturn Result.ok();"
//    out.println "\t}"
//    out.println ""
//    out.println "\t@Override"
//    out.println "\tpublic Result<Page<${entityName}Entity>> getByPage(${entityName}Entity entity, Integer page, Integer pageSize){"
//    out.println "\t\t\tSpecification querySpecif =  new Specification() {\n" +
//            "\t            @Override\n" +
//            "\t\t           public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {\n" +
//            "\t\t                List<Predicate> predicates = new ArrayList<>();\n"
//    fields.each() {getParameter(it,out)}
//    out.println    " \t\t               return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));\n" +
//            "\t\t            }\n" +
//            "\t        };\n" +
//            "\t        Sort sort = new Sort(Sort.Direction.DESC,\"id\");\n" +
//            "\t        page =page==null? 0:page;   "+
//            "\t        pageSize =pageSize==null? 0:pageSize;   "+
//            "\t        PageRequest pageRequest = new PageRequest(page,pageSize, sort);"
//    out.println "\tPage<${entityName}Entity> pages=rep.findAll(querySpecif,pageRequest);"
//    out.println "\treturn Result.ok(pages);"
//    out.println "\t"
//    out.println "\t}"
//    out.println "}"
//}
//def getParameter(field,out) {
//    def fieldName=field.name.capitalize()
//    if(field.type == "String"){
//        out.println  "\t\t\tif(entity.get${fieldName}().isEmpty() ){"
//        out.println  "\t\t\t\tpredicates.add(criteriaBuilder.equal(root.get(\"${field.colum}\"),  entity.get${fieldName}() ));"
//        out.println  "\t\t\t}"
//    }else {
//        out.println  "\t\t\tif( entity.get${fieldName}() != null ){"
//        out.println  "\t\t\t\tpredicates.add(criteriaBuilder.equal(root.get(\"${field.colum}\"),  entity.get${fieldName}() ));"
//        out.println  "\t\t\t}"
//    }
//
//
//}
//
//def getController(out, table, entityName, fields, basePackage) {
//
//    def tableComment= getTableComment(fields)
//    def entityPackName =Case.LOWER.apply(entityName)
//    def getByIdLog=new String("根据ID获取{tableComment}唯一数据".getBytes("gbk"),"utf-8")
//    def deleteByIdLog=new String("根据ID删除{tableComment}唯一数据".getBytes("gbk"),"utf-8")
//    def saveOneLog=new String("新增{tableComment} 信息".getBytes("gbk"),"utf-8")
//    def updateLog=new String("修改 {tableComment}信息".getBytes("gbk"),"utf-8")
//    def queryByPageLog=new String("分页查找{tableComment}数据".getBytes("gbk"),"utf-8")
////    getByIdLog=getByIdLog.replace("{tableComment}",tableComment)
////    deleteByIdLog=deleteByIdLog.replace("{tableComment}",tableComment)
////    saveOneLog=saveOneLog.replace("{tableComment}",tableComment)
////    updateLog=updateLog.replace("{tableComment}",tableComment)
////    queryByPageLog=queryByPageLog.replace("{tableComment}",tableComment)
//
//    out.println "package ${basePackage}.controller;"
//    out.println ""
//    out.println "import com.springboot.demo.constant.Result;"
//    out.println "import com.springboot.demo.annotation.sysLog.aspect.SysLog;"
//    out.println "import com.springboot.demo.domin.base.controller.BaseController;"
//    out.println "import io.swagger.annotations.ApiOperation;"
//    out.println "import io.swagger.annotations.Api;"
//    out.println "import lombok.extern.slf4j.Slf4j;"
//    out.println "import org.springframework.data.domain.Page;"
//    out.println "import org.springframework.web.bind.annotation.*;"
//    out.println "import ${basePackage}.entity.${entityName}Entity;"
//    out.println "import ${basePackage}.service.${entityName}Service;"
//    out.println "import org.springframework.beans.factory.annotation.Autowired;"
//    out.println ""
//    out.println "/**\n"
//    out.println "/** ${tableComment} controller"
//    out.println "* @Author yyzh\n"
//    out.println "* @Date " +new java.util.Date().toString()
//    out.println "*/"
//    out.println ""
//    out.println "@RestController"
//    out.println "@RequestMapping(\"/${entityPackName}\")"
//    out.println "@Api(value = \"${entityPackName}Controller\")"
//    out.println "@Slf4j"
//    out.println "public class ${entityName}Controller extends BaseController {"
//    out.println ""
//    out.println "\t@Autowired"
//    out.println "\tprivate ${entityName}Service service;"
//    out.println ""
//    out.println "\t@SysLog(\"${getByIdLog}\")"
//    out.println "\t@GetMapping(value = \"/getOneById\")"
//    out.println "\t@ApiOperation(value = \"${getByIdLog}\")"
//    out.println "\tpublic Result getOneById(@RequestParam(\"id\") ${fields[0].type} id){"
//    out.println "\t\treturn service.getOneById(id);"
//    out.println "\t}"
//    out.println ""
//    out.println "\t@SysLog(\"${deleteByIdLog}\")"
//    out.println "\t@GetMapping(value = \"/deleteById\")"
//    out.println "\t@ApiOperation(value = \"${deleteByIdLog}\")"
//    out.println "\tpublic Result deleteById(@RequestParam(\"id\") ${fields[0].type} id){"
//    out.println ""
//    out.println "\t\treturn service.deleteById(id);"
//    out.println ""
//    out.println "\t}"
//    out.println ""
//    out.println "\t@SysLog(\"${saveOneLog}\")"
//    out.println "\t@GetMapping(value = \"/saveOne\")"
//    out.println "\t@ApiOperation(value = \"${saveOneLog}\")"
//    out.println "\tpublic Result saveOne(@RequestBody ${entityName}Entity entity){"
//    out.println ""
//    out.println "\t\treturn service.saveOne(entity);"
//    out.println ""
//    out.println "\t}"
//    out.println ""
//    out.println ""
//    out.println "\t@SysLog(\"${updateLog}\")"
//    out.println "\t@GetMapping(value = \"/updateEntity\")"
//    out.println "\t@ApiOperation(value = \"${updateLog}\")"
//    out.println "\tpublic Result updateEntity(@RequestBody ${entityName}Entity entity){"
//    out.println ""
//    out.println "\t\treturn service.updateEntity(entity);"
//    out.println ""
//    out.println "\t}"
//    out.println ""
//    out.println "\t@SysLog(\"${queryByPageLog}\")"
//    out.println "\t@GetMapping(value = \"/getByPage\")"
//    out.println "\t@ApiOperation(value = \"${queryByPageLog}\")"
//    out.println "\tpublic Result<Page<${entityName}Entity>> getByPage(@RequestBody ${entityName}Entity entity,@RequestHeader(\"page\") Integer page,@RequestHeader(\"pageSize\") Integer pageSize){"
//
//    out.println "\t\treturn service.getByPage(entity,page,pageSize);"
//    out.println "\t}"
//    out.println "}"
//}
//
//def mkdirs(dirs) {
//    dirs.forEach {
//        def f = new File(it)
//        if (!f.exists()) {
//            f.mkdirs();
//        }
//    }
//}
//
//def clacBasePackage(dir) {
//    dir.toString()
//            .replaceAll("^.+\\\\src\\\\main\\\\java\\\\", "")
//            .replaceAll("\\\\", ".")
//}
//
//def isBaseEntityProperty(property) {
//    baseEntityProperties.find { it == property } != null
//}
//// 转换类型
//def calcFields(table) {
//    DasUtil.getColumns(table).reduce([]) {
//        fields, col ->
//            def spec = Case.LOWER.apply(col.getDataType().getSpecification())
//            def typeStr = typeMapping.find { p, t -> p.matcher(spec).find() }.value
//            fields += [[
//                               name : javaName(col.getName(), false),
//                               colum: col.getName(),
//                               type : typeStr,
//                               annos: "",
//                               comment: col.getComment(),
//                       ]]
//    }
//}
//
//def javaName(str, capitalize) {
//    def s = str.split(/(?<=[^\p{IsLetter}])/).collect { Case.LOWER.apply(it).capitalize() }
//            .join("").replaceAll(/[^\p{javaJavaIdentifierPart}]/, "_").replaceAll(/_/, "")
//    capitalize || s.length() == 1 ? s : Case.LOWER.apply(s[0]) + s[1..-1]
//}
//
