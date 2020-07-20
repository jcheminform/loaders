create index reference_bases__type on reference_bases(type_id);
create index reference_bases__dcdate on reference_bases(dcdate);
grant select on reference_bases to sparql;

--------------------------------------------------------------------------------

create index reference_discusses__reference on reference_discusses(reference);
create index reference_discusses__statement on reference_discusses(statement);
grant select on reference_discusses to sparql;

--------------------------------------------------------------------------------

create index reference_subject_descriptors__reference on reference_subject_descriptors(reference);
create index reference_subject_descriptors__descriptor_qualifier on reference_subject_descriptors(descriptor, qualifier);
grant select on reference_subject_descriptors to sparql;
--------------------------------------------------------------------------------

create index reference_primary_subject_descriptors__reference on reference_primary_subject_descriptors(reference);
create index reference_primary_subject_descriptors__descriptor_qualifier on reference_primary_subject_descriptors(descriptor, qualifier);
grant select on reference_primary_subject_descriptors to sparql;
