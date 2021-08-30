
begin;

DELETE
FROM "Articles"
WHERE id <= 4;

DELETE
FROM "Article_Categories"
WHERE id <= 7;

DELETE
FROM "Users"
where id = 1;

commit;


