a = spring.getBean("flexibleSearchService").search("select {pk} from {Language}");
dumpOut a
def dumpOut( clz ) {
    clz.metaClass.methods.each { method ->
        println "${method.returnType.name} ${method.name}( ${method.parameterTypes*.name.join( ', ' )} )"
    }
}