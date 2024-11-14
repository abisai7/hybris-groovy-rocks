import de.hybris.platform.hac.data.dto.SqlSearchResultData
import de.hybris.platform.hac.facade.HacFlexibleSearchFacade
import de.hybris.platform.core.Registry

tablePrefix = Registry.getCurrentTenantNoFallback().getDataSource().getTablePrefix()
query = "SELECT item_t0.PK FROM ".plus(tablePrefix).plus("products item_t0")

SqlSearchResultData searchResult
flexibleSearchFacade = new HacFlexibleSearchFacade()
result = flexibleSearchFacade.executeRawSql(query, 10, false)

println result.getHeaders().join("\t")

for (item in result.getResultList()) {
    println(item.join("\t"))
}