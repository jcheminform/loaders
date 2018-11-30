create function protein(id in integer) returns varchar language sql as
$$
  select 'http://rdf.ncbi.nlm.nih.gov/pubchem/protein/' || name from protein_bases where protein_bases.id = protein.id;
$$
immutable;


create function protein_inverse(iri in varchar) returns integer language sql as
$$
  select id from protein_bases where name = substring(protein_inverse.iri, 45)::varchar;
$$
immutable;
