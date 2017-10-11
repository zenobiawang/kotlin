package cz.sample.kotlin

import java.io.File

/**
 * Created by cz on 2017/5/27.
 */
fun main(args: Array<String>) {
//    val p2:Person=Person("abc",12)
//    println(p1==p2)
//    println(Person("abc",12))
//    println(Person("abc"))
//    val list1 = listOf(1, 2, 3)
//    val array1= arrayOf(1, 2, 3)
//
//    for(it in list1){
//        println("${it}Hello")
//    }
//
//    for(it in 1..2){
//        println(it)
//    }
//
    val value:String?="abc"



//    println("Hello".padEnd(10,'a'))

//    list1.map { it.toString() }.map { "${it}Hello" }.reduce { acc, s -> acc+s }.forEach { print(it) }

//    var count:Int=10
//    testClosure{ count += it }
//    println("count:$count")
//    val list = listOf("Hello", "World","egg")
//    val (a,b,c)=list
//    println("$a $b $c")
//
//    list.flatMap { it.map { it } }.
//            map { it.toString() }.
//            reduce { acc, s -> if(s in acc) acc else s+acc }.
//            forEach { print(it+" ") }

//    0.downTo(-10).forEach {
//        println("$it"+" "+getRealPosition(it))
//    }
//    val list1 = listOf(1, 2, 3, 4)
//    list1.filterNot { it<3 }.forEach(::println)


//    val animal = Animal("a")
//    if(animal.age.isNotEmpty()){
//        animal.age="11"
//    }
//    println(animal.age)
//    val list1 = listOf("a","b","c")
//    println(String.format("%s %s %s",list1.toTypedArray()))


    val file= File("/Users/cz/Documents/master/JavaSeSample/config/net.txt")
    //抽取分类action
    //------------------userinfo 个人信息相关-------------
    //val USER_CENTER_PERSONAL_DETAILS_INFO = "user_center_personal_details_info"
    val actionItems=mutableMapOf<String,MutableList<String>>()
    var key=""
    val pattern1="\\s+//-+([^-]+)-+".toRegex().toPattern()
    val pattern2="\\s+val\\s(\\w+)\\s+=\\s\"(\\w+)\"".toRegex().toPattern()
    file.readLines().forEach {
        val matcher1=pattern1.matcher(it)
        val matcher2=pattern2.matcher(it)
        if(matcher1.find()){
            key=matcher1.group(1)
            actionItems.put(key, mutableListOf<String>())
        } else if(matcher2.find()){
            val items=actionItems[key]
            items?.add(matcher2.group(1))
        }
    }
    //抽取item条目
    //action = FUEL_PAY
    val text=file.readText()
    val items="(?s)(item\\s[^}]+})+?".toRegex().findAll(text).map { it.value }
    val pattern3="\\s+action\\s+=\\s(\\w+)".toRegex().toPattern()
    val textItems= mutableMapOf<String,String>()
    items.forEach {
        val matcher=pattern3.matcher(it)
        if(matcher.find()){
            textItems.put(matcher.group(1),it)
        }
    }

    //合并分类
    val result=mutableMapOf<String,MutableMap<String,String?>>()
    actionItems.forEach {
        val resultItem=result.getOrPut(it.key){ mutableMapOf()}
        it.value.forEach {
            resultItem.put(it,textItems[it])
        }
    }
    //生成文件
    val pattern4="(\\w+)\\s(.+)".toRegex().toPattern()
    val pattern5="(?<=action\\s=\\s)".toPattern()
    val packageName="com.financial.quantgroup.v2.net.model"
    /**
     * Created by cz on 2017/7/19.
     */
    val classNameItems= mutableListOf<String>()
    result.forEach {
        //userinfo 个人信息相关
        val matcher=pattern4.matcher(it.key)
        var className:String?=null
        var classInfo:String?=null
        if(matcher.find()){
            className=matcher.group(1)
            classInfo=matcher.group(2)
            className=className[0].toUpperCase()+className.substring(1)+"PrefsItem"
        }
        if(null!=className){
            classNameItems.add(className)
            val out=StringBuilder()
            out.append("package $packageName\n\n")
            out.append("import com.financial.quantgroup.v2.net.NetPrefs\n")
            out.append("import cz.netlibrary.model.NetPrefsItem\n")
            out.append("import cz.netlibrary.model.RequestMethod\n")
            out.append("\n")
            out.append("/**\n")
            out.append(" * Created by cz on 2017/7/19.\n")
            out.append(" * $classInfo\n")
            out.append(" */\n")
            out.append("class $className : NetPrefsItem(){\n")
            out.append("\tinit {\n")
            //插入条目
            it.value.forEach {
                //这里采用环视插入
                val itemText=pattern5.matcher(it.value).replaceAll("NetPrefs.")
                itemText.lines().forEachIndexed { index,item->
                    if(0==index){
                        out.append("\t\t$item\n")
                    } else {
                        out.append("\t$item\n")
                    }
                }
            }
//            out.append("\t}\n")
//            out.append("}\n")
//            val file=File("/Users/cz/Documents/master/xyqb-android/app/src/main/java/com/financial/quantgroup/v2/net/model/$className.kt")
//            val writer=file.outputStream().bufferedWriter()
//            writer.write(out.toString())
//            writer.close()
        }
    }
    println("输出注册model导包信息")
    var out=StringBuilder()
    classNameItems.forEach {
        out.append("import $packageName.$it\n")
    }
    println(out.toString())
    println("输出注册信息")
    out=StringBuilder()
    classNameItems.forEach {
        out.append("Configuration.register($it())\n")
    }
    println(out.toString())

//    val text= "item {\n" +
//            "        action = USER_CENTER_PERSONAL_DETAILS_INFO\n" +
//            "        info = \"个人资料选项列表\"\n" +
//            "        url = \"api/tab/my/user-center/options?\"\n" +
//            "        params = arrayOf(\"reqParam\")\n" +
//            "    }"
//
//    //采用环视为每个条目的action 插入NetPrefs.
//    println("(?<=action\\s=\\s)".toPattern().matcher(text).replaceAll("NetPrefs."))



}


