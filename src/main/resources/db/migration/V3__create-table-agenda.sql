create table agendas(

     id bigint not null auto_increment,
     barbeiro_id bigint not null,
     cliente_id bigint not null,
     date datetime not null,
     cancelamento varchar(100),

      primary key(id),
      constraint fk_agendas_barbeiro_id foreign key(barbeiro_id) references barbeiros(id),
      constraint fk_agendas_cliente_id foreign key(cliente_id) references clientes(id)

);