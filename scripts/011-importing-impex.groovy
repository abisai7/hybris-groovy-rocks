import de.hybris.platform.util.CSVConstants
import de.hybris.platform.servicelayer.impex.ImpExResource
import de.hybris.platform.servicelayer.impex.ImportResult
import de.hybris.platform.servicelayer.impex.impl.StreamBasedImpExResource

importImpex("INSERT Language;isocode;active\n;test;true")

def importImpex(String content) {
    final ImpExResource mediaRes = new StreamBasedImpExResource(new ByteArrayInputStream(content.getBytes()), CSVConstants.HYBRIS_ENCODING)
    importService.importData(mediaRes)
}