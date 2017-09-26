package com.example

import java.io.File


/**
 * Created by wanghui on 2017/9/21.
 */
class CreateNetPrefs{
    val fileCommonPath = ""
    val fileFilePath = ""

    fun getFileContent(file : File) : String{
        val stringBuilder = StringBuilder()
        file.readLines().forEach {
            stringBuilder.append("$it\n")
        }
        return stringBuilder.toString()
    }

    fun getClassifyMap(resource : String) : Map<String, List<String>>{
        val map : MutableMap<String, MutableList<String>> = mutableMapOf()
        val matcher = Regex("--+(.+?)---+").toPattern().matcher(resource)
        while (matcher.find()){
            val key = matcher.group(1)
            val matchers = Regex("$key((.|\\n)+?)//").toPattern().matcher(resource)
            while (matchers.find()){

            }
        }

        Regex("--+(.+?)---+").findAll(resource).forEach {
            val key = it.value
//            System.out.println("$key---" + it.groupValues.toString())
            Regex("$key((.|\\n)+?)//").findAll(resource).forEach {
                System.out.println(it.value + "\n")
                val actions = it.value
                "val (.+?) =".toRegex().findAll(actions).map { it.value }.forEach {
                    System.out.println(it)
                }

            }
        }
        return map
    }
}