STOREFRONTCONTEXT = "trainingstorefront"

import de.hybris.platform.spring.HybrisContextLoaderListener
import org.springframework.web.context.ContextLoader
import org.springframework.web.context.WebApplicationContext

def f = ContextLoader.getDeclaredField("currentContextPerThread")
f.setAccessible(true)
appContext = null
Map<ClassLoader, WebApplicationContext> contexts = f.get(HybrisContextLoaderListener)

for (loader in contexts) {
    contextName = loader.getKey().getContextName()
    // println("contextName: " + contextName);
    if (contextName == STOREFRONTCONTEXT) {
        appContext = loader.getValue()
    }
}

if (appContext == null) {
    println("context is not found. Please set up STOREFRONTCONTEXT (for example, 'trainingstorefront') ")
    return
}

printAllBeans(appContext)

void printAllBeans(context) {
    if (context == null) {
        return
    }
    beanFactory = context.getAutowireCapableBeanFactory()
    for (String beanName : beanFactory.getBeanDefinitionNames()) {
        println beanName
    }
}