# Treinando Spring: serviço web

Pequeno exemplo ("toy") de serviço de reserva de quartos em hotel, abrangendo vários aspectos do desenvolvimento com Java e Spring.

Aqui tratamos um pouco de cada coisa:

* *Perfis de usuários:* _ADMIN_ cadastra os quartos, _AGENDADOR_ realiza agendamentos
* *Regras de negócio:*
  - checkin a partir de 15h, checkout até 12h: um ocupante pode sair e outro entrar no mesmo dia
  - agendamentos para o mesmo quarto não podem sobrepôr os períodos
* *Concorrência:*
  - verificação de conflitos de horário realizada em código Java: consulta, decide, agenda (ou não)
  - lock é realizado na linha do *quarto* (agregado) para que dois agendadores não leiam um quarto como disponível ao mesmo tempo
* *Segurança:* ah, o Spring Security!
  - Geração de tokens
  - Filtro
  - Tratamento dos erros e retorno dos códigos certos (401, 403)
* *Testes:* não fiz testes para _todos_ os casos de uso (estou ciente!), mas alguns representativos de aspectos técnicos:
  - testes de classe de serviço
  - testes de endpoint com autenticação