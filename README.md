Projeto 3 da disciplina DSW1: Desenvolvimento do site de consultas - Requisitos B

Grupo: 
- Guilherme Salvador Escher - RA 792528
- Leo Rodruigues Aoki - RA: 801926
- Lucas Silva Mendes - RA: 800247

Para executar o projeto será necessário o PostgreeSQL, Java v22 e o EclipseIDE instalados.

Caso tenha ambos instalados, no arquivo application.properties será necessário ajustar os parametros para acessar o banco de dados local:
- spring.datasource.url=jdbc:postgresql://localhost/sistemaConsultas
- spring.datasource.username=postgres
- spring.datasource.password=admin

Nas linhas acima do seu aquivo, coloque o nome do banco, usuário e senha que vc tiver configurado no seu pgAdmin do PostgreeSQL.
Ao inicar a aplicação, o banco será preenchido de acordo com os dados presentos no arquivo ConsultasApplication.java, então não é necessário adicionar dados pelo pgAdmin.

Quando o banco estiver configurado, o proximo passo será executar a aplicação.

Caso deseje executar usando um terminal, navegue ate a pasta do projeto, abra um terminal nela e rode o seguinte comando: 
    mvn spring-boot:run. 
Isso deverá iniciar a aplicação no seu localhost:8080. Garanta que ele está livre para evitar problemas.

Para acessar as rotas REST, utilize o Postman. As urls são:
- http://localhost:8080/clientes
- http://localhost:8080/clientes/{id}

- http://localhost:8080/profissionais
- http://localhost:8080/profissionais/{id}
- http://localhost:8080/profissionais/especialidades/{especialidade}
  
- http://localhost:8080/consultas
- http://localhost:8080/consultas/{id}
- http://localhost:8080/consultas/profissionais/{crm}
- http://localhost:8080/consultas/clientes/{cpf}
