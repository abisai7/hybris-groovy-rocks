// Modifying objects, in this case, search for medias without mime and set it
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery

flexibleSearchService = spring.getBean("flexibleSearchService")
mimeService = spring.getBean("mimeService")
modelService = spring.getBean("modelService")

def findMediasWithoutMime() {
    query = "SELECT {PK} FROM {Media} WHERE {mime} IS NULL LIMIT 10"
    flexibleSearchService.search(query).result
}

findMediasWithoutMime().each {
    it.mime = mimeService.getMimeFromFileExtension(it.realfilename)
    modelService.save(it)
}