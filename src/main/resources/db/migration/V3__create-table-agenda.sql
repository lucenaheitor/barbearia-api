create table agendas(

     id bigint not null auto_increment,
     barbeiro_id bigint not null,
     cliente_id bigint not null,
     date datetime not null,

      primary key(id),
      constraint fk_agendas_barbeiro_id foreign key(barbeiro_id) references barbeiros(id),
      constraint fk_agenndas_cliente_id foreign key(cliente_id) references clientes(id)

);