package co.nexlabs.betterhr.joblanding.util

fun String.isEndWithThese(list: List<String>): Boolean{
    for(prefix in list){
        if(this.endsWith(".$prefix")) return true
    }
    return false
}

fun String.isStartWithThese(list: List<String>): Boolean{
    for(prefix in list){
        if(this.startsWith(prefix)) return true
    }
    return false
}

fun String.isContainWithThese(list: List<String>): Boolean{
    for(prefix in list){
        if(this.contains(".$prefix")) return true
    }
    return false
}