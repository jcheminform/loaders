sparql alter quad storage virtrdf:PubchemQuadStorage
{
    drop map:gene
}.;

--------------------------------------------------------------------------------

sparql drop iri class iri:gene .;

--------------------------------------------------------------------------------

drop table gene_references;
drop table gene_alternatives;
drop table gene_biosystems;
drop table gene_bases;
