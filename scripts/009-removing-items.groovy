flexibleSearchService = spring.getBean("flexibleSearchService")
modelService = spring.getBean("modelService")

// Add where clause, the script is just an example
flexibleSearchService.search("select {pk} from {product} where ....").result.each {
    modelService.remove(it)
}