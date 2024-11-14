result = spring.getBean("flexibleSearchService").search("select {pk} from {Language}")
// properties of result query
result.properties.each { println "$it.key -> $it.value" }