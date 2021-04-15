-- noinspection SqlResolveForFile
-- H2 Dialect

insert into employee (id, name, `password`, username)
values (1, 'Test', 'senha', 'test');

insert into priority (id, color, text_color, name, `value`)
values (1, '#ffffff', '#ffffff', 'Baixa', 1),
       (2, '#ffffff', '#ffffff', 'Média', 2),
       (3, '#ffffff', '#ffffff', 'Alta', 3);