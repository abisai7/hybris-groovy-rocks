// replace "printAllBeans(appContext)" from the example above with the code below
STOREFRONTCONTEXT = "global"

import de.hybris.platform.spring.HybrisContextLoaderListener
import org.springframework.web.context.ContextLoader
import org.springframework.web.context.WebApplicationContext
import de.hybris.platform.catalog.model.CatalogVersionModel
import de.hybris.platform.core.model.c2l.LanguageModel
import de.hybris.platform.catalog.model.CatalogModel
import org.springframework.validation.support.*
import de.hybris.platform.commercefacades.user.data.CustomerData
import de.hybris.platform.commercefacades.storesession.data.*
import org.springframework.web.servlet.mvc.support.*

// Set a application context
def f = ContextLoader.getDeclaredField("currentContextPerThread")
f.setAccessible(true)
appContext = null
Map<ClassLoader, WebApplicationContext> contexts = f.get(HybrisContextLoaderListener)

for (loader in contexts) {
    contextName = loader.getKey().getContextName()
    // println("contextName: " + contextName);
    if (contextName == STOREFRONTCONTEXT) {
        return loader.getValue()
    }
}

if (appContext == null) {
    println("context is not found. Please set up STOREFRONTCONTEXT (for example, 'trainingstorefront') ")
    return
}

// Set up the catalog
catalog = "electronicsContentCatalog"
catalogVersion = "Online"
siteUid = "electronics"
currentUser = new CustomerData() //set up the customer if needed ...
currentCurrency = new CurrencyData() //set up the currency if needed ...
currentLanguage = new LanguageData() //set up the language if needed
currencies = new ArrayList()
currencies.add(currentCurrency)
languages = new ArrayList()
languages.add(currentLanguage)

CatalogVersionModel catalogVersionModel = catalogVersionService.getCatalogVersion(catalog, catalogVersion)
CatalogModel catalogModel = catalogService.getCatalogForId(catalog)
LanguageModel languageModel = i18NService.getLanguage("en")
Collection<CatalogVersionModel> catalogVersions = new ArrayList<CatalogVersionModel>()
catalogVersions.add(catalogVersionModel)

sessionService.setAttribute("currentCatalogVersion", catalogVersionModel)
sessionService.setAttribute("catalogversions", catalogVersions)
sessionService.setAttribute("language", languageModel)
baseSiteService = spring.getBean("baseSiteService")
currentSite = baseSiteService.getBaseSiteForUID(siteUid)
sessionService.setAttribute("currentSite", currentSite)
locale = new Locale("en")
sessionService.setAttribute("locale", locale)

homePageController = appContext.getBean("homePageController")
model = new BindingAwareModelMap()
model.put("user", currentUser)
model.put("siteUid", siteUid)
model.put("siteName", siteUid)
model.put("currentCurrency", currentCurrency)
model.put("currentLanguage", currentLanguage)
model.put("currencies", currencies)
model.put("languages", languages)
redirectModel = new RedirectAttributesModelMap()
template = homePageController.home(false, model, redirectModel)
println "template = $template"
for (modelItem in model) {
    println modelItem
}