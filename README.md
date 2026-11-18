# Serviço de Assinaturas de Marmitas

Implementação acadêmica em Java 17+ baseada nos diagramas UML do pacote `Parte 3.zip` (cenários I01 a I04).

## Camadas
- `boundary`: `TelaAssinatura` (interface de terminal) e interfaces de integração.
- `control`: `ControladorAssinatura`, coordenando o caso de uso.
- `domain`: entidades, tipos e regras do domínio.
- `collection`: catálogo de planos e coleção de pratos.
- `persistence`: gravação CSV de assinaturas e pedidos.
- `integration`: simuladores locais de SMS, e-mail e operadora de cartão.

## Requisitos
JDK 17 ou superior. Não usa bibliotecas externas.

## Como executar
No terminal, na pasta do projeto:

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out br.com.marmitas.app.Main
```

No Windows PowerShell, se o comando `find` não estiver disponível:

```powershell
Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName } | Set-Content fontes.txt
javac -encoding UTF-8 -d out @fontes.txt
java -cp out br.com.marmitas.app.Main
```

Os arquivos `assinaturas.csv` e `pedidos.csv` serão criados na pasta `dados/` após a conclusão de uma assinatura.

## Fluxo implementado
1. I01 inicia a contratação e direciona para identificação.
2. I02 cadastra nome, e-mail e celular; gera código SMS com validade de cinco minutos e permite reenvio.
3. I03 apresenta planos, filtra cardápio pela preferência (`TRADICIONAL`, `VEGETARIANA`, `SEM_LACTOSE`), valida pratos/quantidades e endereço/dia de entrega.
4. I04 valida referência do cartão, solicita autorização simulada, cria pedido apenas se autorizado, ativa assinatura, gera protocolo e envia confirmação.

## Dados demonstrativos
Os diagramas fornecem os atributos e operações, mas não definem preços, periodicidades, limites dos planos nem nomes/descrições dos pratos. Para permitir testar o sistema, `CatalogoPlanos` e `ColecaoPratos` contêm valores demonstrativos claramente identificados no código. Substitua-os pelos dados aprovados pelo grupo.

## Testes da simulação
- SMS: o código é exibido no console pelo simulador `ServicoMensageria`.
- Cartão: informe 16 dígitos. Para simular recusa, use um número terminado em `0000`; os demais números válidos são aprovados pelo simulador. Nenhum número completo ou CVV é persistido.
- Fluxos alternativos: código incorreto/expirado, seleção vazia/incompatível/fora do limite, endereço incompleto, cartão inválido e pagamento recusado.

## Persistência e segurança (escopo acadêmico)
A persistência é feita em CSV, sem armazenar código SMS, CVV ou número completo do cartão. As integrações externas são simuladas; o projeto não processa pagamentos reais nem envia SMS/e-mails reais.

## Relação com UML
`TelaAssinatura` = boundary; `ControladorAssinatura` = control; classes em `domain` = entity/datatype; `CatalogoPlanos` e `ColecaoPratos` = collection. As chamadas seguem os quatro diagramas de sequência. O `Map<PratoPrincipal,Integer>` é transitório, como no modelo, e `Pedido`/`ItemPedido` só são criados após autorização.
