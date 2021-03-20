package com.ifpb.view;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.ifpb.model.Pessoa;

import java.util.List;

public class App {

    public static void main(String[] args) {

        Cluster cluster = null;

        try{

            cluster = Cluster.builder().addContactPoint("172.20.0.2").build();
            Session session = cluster.connect("aula");
            MappingManager mappingManager = new MappingManager(session);

            Mapper<Pessoa> pessoaMapper = mappingManager.mapper(Pessoa.class);

            //Salvando uma pessoa
//            pessoaMapper.save(new Pessoa("111.111.111-01",
//                    "João",
//                    LocalDate.fromYearMonthDay(2000,6,8),
//                    List.of("Cassandra", "Java", "NodeJS")));

            //Recuperando pela chave primária
//            System.out.println(pessoaMapper.get("111.111.111-01"));

            //Removendo pela chave primária
//            pessoaMapper.delete("111.111.111-01");

            //Atualizando o nome de uma pessoa
            PreparedStatement preparedStatement = session
                    .prepare("UPDATE pessoa SET nome = ? WHERE cpf = ?");
            BoundStatement boundStatement = preparedStatement.bind("João da Silva", "111.111.111-01");
            session.execute(boundStatement);


        }finally {
            if(cluster != null) cluster.close();
        }

    }

}
