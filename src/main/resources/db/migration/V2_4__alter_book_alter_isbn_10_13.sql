alter table store.books change isbn isbn10 varchar(255);
alter table store.books add column isbn13 varchar(255);