import de.hybris.platform.core.PK

modelService = spring.getBean("modelService")

Long examplePK = 8806480510977
object = modelService.get(new PK(examplePK))
print object.getPk()
print ", "
println object.getName()