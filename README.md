Projeto 2 da disciplina DSW1: Desenvolvimento do site de consultas - Requisitos B

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

Para comecar a navegar, abra um navegador qualquer e digite o URL:
    http://localhost:8080/medicos/home
Essa é a homepage do site, e a partir dela vc pode pesquisar médicos por especialidade ou fazer o login.

No login você tem opção seguir como médico, paciente ou administrador:

-  Admin:
    Email:  admin
    Senha:  admin

  Aqui você poderá vizualizar, criar, editar ou excluir os pacientes e médicos (CRUD).

-  Paciente: um paciente que vc pode facilmente usar pra testar é o
    Email: genivaldo@gmail.com
    Senha: genivaldo

  Aqui voce pode vizualizar suas consultas cadastradas ou criar novas.

- Médico: um médico que vc pode facilmente usar pra testar é o
    Email: mario@gmail.com
    Senha: mario

  Aqui você pode vizualizar suas consultas cadastradas.

Caso queira testar a internacionalização do site, basta alterar o idioma do seu navegador para inglês. 
- Português e inglês são os dois idiomas suportados;
- Recomendo o Firefox como navegador, pois foi onde o programa foi testado.
