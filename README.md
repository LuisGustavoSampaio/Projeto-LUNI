# Starter — Refatoração Startup Game (POO Avançado)

## Sobre o Projeto
Este é o **starter** do projeto de refatoração do Startup Game.  
Ele fornece a estrutura mínima com pacotes, classes *stub* e classes VO prontas para uso.  
Os alunos devem completar o projeto conforme o **Enunciado_Projeto.md**.

⚠️ **Versionamento no Git**:  
O projeto deve ser versionado em um repositório Git. O professor será incluído como colaborador para verificar commits.  
**A frequência, autoria e qualidade dos commits serão avaliadas** como parte da nota.

---

## Estrutura do Projeto
```
database/
  startupdb.mv.db
  startupdb.trace.db
lib/
  h2.jar
resources/
  game.properties (total.rodadas=3 e max.decisoes.por.rodada=3)
  schema.sql
src/
  config/Config.java
  config/game.properties
  model/Startup.java
  model/Deltas.java
  model/vo/Dinheiro.java
  model/vo/Percentual.java
  model/vo/Humor.java
  model/old/Main.java.txt
  modelo/old/Main.java2.txt
  actions/DecisaoStrategy.java
  actions/DecisaoFactory.java
  actions/[estratégias].java
  persistence/DataSourceProvider.java
  persistence/[repositories].java
  engine/GameEngine.java
  engine/ScoreService.java
  ui/ConsoleApp.java
  Main.java
  TestaConexao.java
.gitignore
```

---

## Como Rodar (console)
Compile e execute o `Main` **incluindo `resources` no classpath**:

```bash
# Compilar (Windows)
javac Main.java

# Executar (Windows) 
java Main
```

---

## Configurações
O arquivo `resources/game.properties` já vem configurado com:
- `total.rodadas=3`
- `max.decisoes.por.rodada=3`

---

## Banco de Dados
- **H2 (arquivo)**: URL padrão `jdbc:h2:file:./data/game;AUTO_SERVER=TRUE` (ver `DataSourceProvider`).
- Execute o SQL de `resources/schema.sql` na inicialização para criar as tabelas necessárias.

---

## Entregáveis
- Código-fonte completo no **Git** + link do repositório para o professor.
- `schema.sql` completo com tabelas do H2.
- `README.md` e `RELATORIO.md` com instruções, evidências e resultados.
- Commits frequentes e autoria verificada no Git.
