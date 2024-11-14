import de.hybris.platform.hac.data.dto.SqlSearchResultData
import de.hybris.platform.hac.facade.HacFlexibleSearchFacade
import de.hybris.platform.core.Registry
import de.hybris.platform.core.PK

tablePrefix = Registry.getCurrentTenantNoFallback().getDataSource().getTablePrefix()
query = "select * from users"

flexibleSearchFacade = new HacFlexibleSearchFacade()
SqlSearchResultData searchResult
result = flexibleSearchFacade.executeRawSql(query, 2, false)

modelService = spring.getBean("modelService")

for (item in result.getResultList()) {
    columnsCounter = 0
    for (fieldValue in item) {
        fieldName = result.getHeaders()[columnsCounter++]
        // println fieldName + "\t"
        if (fieldName == "PK") {
            print modelService.get(new PK(fieldValue as Long)).getName() + "\t"

            if (fieldName == "TypePkString") {
                print modelService.get(new PK(fieldValue as Long)).getName() + "\t"
            }

        }

        print "|\t"
    }
}