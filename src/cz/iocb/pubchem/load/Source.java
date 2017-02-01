package cz.iocb.pubchem.load;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.jena.rdf.model.Model;
import cz.iocb.pubchem.load.common.Loader;
import cz.iocb.pubchem.load.common.ModelTableLoader;



public class Source extends Loader
{
    private static Map<String, Short> loadBases(Model model) throws IOException, SQLException
    {
        Map<String, Short> map = new HashMap<String, Short>();

        new ModelTableLoader(model, loadQuery("source/bases.sparql"),
                "insert into source_bases (id, iri, title) values (?,?,?)")
        {
            short nextID = 0;

            @Override
            public void insert() throws SQLException, IOException
            {
                String iri = getIRI("iri");
                map.put(iri, nextID);

                setValue(1, nextID++);
                setValue(2, iri);
                setValue(3, getLiteralValue("title"));
            }
        }.load();

        return map;
    }


    private static Map<String, Short> loadSubjectsReftable(Model model) throws IOException, SQLException
    {
        Map<String, Short> map = new HashMap<String, Short>();

        new ModelTableLoader(model, distinctPatternQuery("[] dcterms:subject ?iri"),
                "insert into source_subjects__reftable (id, iri) values (?,?)")
        {
            short nextID = 0;

            @Override
            public void insert() throws SQLException, IOException
            {
                String iri = getIRI("iri");
                map.put(iri, nextID);

                setValue(1, nextID++);
                setValue(2, iri);
            }
        }.load();

        return map;
    }


    private static void loadSubjects(Model model, Map<String, Short> sources, Map<String, Short> subjects)
            throws IOException, SQLException
    {
        new ModelTableLoader(model, patternQuery("?source dcterms:subject ?subject"),
                "insert into source_subjects (source, subject) values (?,?)")
        {
            @Override
            public void insert() throws SQLException, IOException
            {
                setValue(1, getMapID("source", sources));
                setValue(2, getMapID("subject", subjects));
            }
        }.load();
    }


    private static void loadAlternatives(Model model, Map<String, Short> sources) throws IOException, SQLException
    {
        new ModelTableLoader(model, patternQuery("?source dcterms:alternative ?alternative"),
                "insert into source_alternatives (__, source, alternative) values (?, ?,?)")
        {
            short nextID = 0;

            @Override
            public void insert() throws SQLException, IOException
            {
                setValue(1, nextID++);
                setValue(2, getMapID("source", sources));
                setValue(3, getLiteralValue("alternative"));
            }
        }.load();
    }


    public static void load(String file) throws IOException, SQLException
    {
        Model model = getModel(file);

        check(model, "source/check.sparql");
        Map<String, Short> sources = loadBases(model);
        Map<String, Short> subjects = loadSubjectsReftable(model);
        loadSubjects(model, sources, subjects);
        loadAlternatives(model, sources);

        model.close();
    }


    public static void main(String[] args) throws IOException, SQLException
    {
        load("RDF/source/pc_source.ttl.gz");
    }
}
