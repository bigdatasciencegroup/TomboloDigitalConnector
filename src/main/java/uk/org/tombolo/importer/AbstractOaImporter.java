package uk.org.tombolo.importer;

import uk.org.tombolo.importer.ons.OaImporter;
import uk.org.tombolo.recipe.SubjectRecipe;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * AbstractOaImporter is extended by importers that do not define a new subject type but use existing ones,
 * making them dependent on the importers that define the subject types originally.
 */
public abstract class AbstractOaImporter extends AbstractImporter {

    @Override
    public void importDatasource(@Nonnull String datasourceId, List<String> geographyScope, List<String> temporalScope,
                                 List<String> datasourceLocation, @Nonnull List<SubjectRecipe> subjectRecipes, Boolean force)
                                throws Exception {
        OaImporter oaImporter = new OaImporter();
        oaImporter.setDownloadUtils(downloadUtils);
        // Import the subjects defined by OaImporter first
        for (String id : getOaDatasourceIds()) {
            oaImporter.importDatasource(id, Collections.EMPTY_LIST, Collections.EMPTY_LIST, Collections.EMPTY_LIST,
                    subjectRecipes, false);
        }

        super.importDatasource(datasourceId, geographyScope, temporalScope, datasourceLocation, subjectRecipes, force);
    }

    /**
     * @return List of the datasourceIds needed to import the subjects defined in OaImporter so its subject types can
     * be used by the current importer.
     */
    protected abstract List<String> getOaDatasourceIds();
}
