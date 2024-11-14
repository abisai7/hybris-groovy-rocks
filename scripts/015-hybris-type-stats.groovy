import java.util.*
import java.lang.*
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery
import de.hybris.platform.hac.facade.HacFlexibleSearchFacade
import de.hybris.platform.servicelayer.search.SearchResult

flexibleSearch = spring.getBean("flexibleSearchService")
flexibleSearchFacade = new HacFlexibleSearchFacade()
typeService = spring.getBean("typeService")

result = flexibleSearch.search(/select {pk} from {ComposedType}/).getResult()
Tree tree = new Tree()
result.each {
    Node node = new Node(it.getCode(), it.getSuperType()?.getCode())
    type = it.getClass().getSimpleName()
    isabstract = it.getAbstract() ? "<abstract>" : ""
    isjaloonly = it.getJaloonly() ? "<jaloonly>" : ""
    type = type.replace("ComposedTypeModel", "<Composed>")
    type = type.replace("RelationMetaTypeModel", "<Relation>")
    type = type.replace("EnumerationMetaTypeModel", "<ENUM>")
    type = type.replace("TypeModel", "")
    node.setDetails(type + isabstract + isjaloonly)
    tree.getElements().add(node)
}

for (element in tree.getElements()) {
    node1 = tree.find(element.getValue())
    node2 = tree.find(element.getParentValue())
    if (node1 != null) {
        node1.setParent(node2)
    }
    if (node2 != null) {
        node2.addChild(node1)
    }
    if (element.getParentValue() == null) {
        root = node1
    }
}

displaySubTree(tree, 0, root, root.getValue())

void printANode(level, Node item, String history) {
    count = "-"

    if (!item.getNotLeaf() && !item.getDetails().contains("<abstract>")
            && !item.getDetails().contains("<jaloonly>")) {
        count = calculateCount(item.getValue())
    }

    println history + "\t" + item.getValue() + "(" + item.getDetails() + ") \t" + count

}

void displaySubTree(Tree tree, int level, Node node, String history) {
    List<Node> subItems = node.getChildren()
    if (subItems.size() == 0) {
        printANode(level, node, history)
    }
    for (item in subItems) {
        printANode(level + 1, item, history)
        if (item.getChildren().size() != 0) {
            displaySubTree(tree, level + 1, item, history + "=>" + item.getValue())
        }
    }
}

class Tree {
    List<Node> elements

    List<Node> getElements() { return elements }

    Tree() {
        elements = new ArrayList()
    }

    void add(Node element) {
        elements.add(element)
    }

    Node find(String value) {
        for (it in elements) {
            if (it.getValue() == value) {
                return it
            }
        }
    }
}

class Node {
    private Node parent = null
    private List<Node> children = null
    private String value
    private String details = ""
    private String parentValue = ""
    private Boolean notLeaf = false

    Node(String value, String parent) {
        this.children = new ArrayList<>()
        this.value = value
        this.parentValue = parent
    }

    def setNotLeaf(Boolean itIsNotALeaf) {
        notLeaf = itIsNotALeaf
    }

    def getNotLeaf(Boolean itIsNotALeaf) {
        return notLeaf
    }

    List<Node> getChildren() {
        return children
    }

    void addChild(Node child) {
        children.add(child)
        child.addParent(this)
    }

    def addParent(Node parentNode) {
        parent = parentNode
    }

    def getValue() {
        return value
    }

    String getParentValue() {
        return parentValue
    }

    def setParent(Node node) {
        parent = node
    }

    def getParent() {
        return parent
    }

    def setDetails(String nodeDetails) {
        details = nodeDetails
    }

    def getDetails() { return details }
}

String calculateCount(component) {
    query = "select count({pk}) from {" + component + "}"
    FlexibleSearchQuery fquery = new FlexibleSearchQuery(query)
    fquery.setResultClassList(Arrays.asList(String.class))
    fquery.setCount(1)
    SearchResult<String> result = flexibleSearch.search(fquery)
    return (result.getResult()?.get(0))
}
