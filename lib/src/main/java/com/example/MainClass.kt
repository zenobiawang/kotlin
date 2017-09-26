package com.example

import java.io.File

/**
 * Created by wanghui on 2017/9/21.
 */
class MainClass{
    companion object {
        @JvmStatic fun main(agr: Array<String>) {
            val creat = CreateNetPrefs()
            creat.getClassifyMap(creat.getFileContent(File(creat.fileFilePath)))
        }
    }

}