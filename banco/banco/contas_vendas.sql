-- Active: 1787177433004@@127.0.0.1@5432@bd_vendas@public
/*
╔══════════════════════════════════════════════════════════════════════╗
║   CONTAS EM SQL: SOMA, SUBTRAÇÃO, MULTIPLICAÇÃO, DIVISÃO E MAIS       ║
║   Banco: bd_vendas | Tabelas: vendas_itens, vendas_itens2             ║
╚══════════════════════════════════════════════════════════════════════╝
 Se a linha "-- Active" der erro, clique no banco bd_vendas no painel
 da extensão Database Client para ela ser recriada.

 Nenhum bloco altera dados: todos são SELECT. Execute um bloco por vez.

 ÍNDICE
 ┌──────┬────────────────────────────────────────────────────────────┐
 │ 1    │ SOMA (+ e SUM)                                             │
 │ 2    │ SUBTRAÇÃO (-)                                              │
 │ 3    │ MULTIPLICAÇÃO (*)                                          │
 │ 4    │ DIVISÃO (/) e as armadilhas                                │
 │ 5    │ RESTO DA DIVISÃO (%)                                       │
 │ 6    │ ARREDONDAMENTO (ROUND, TRUNC, CEIL, FLOOR)                 │
 │ 7    │ MÉDIAS (AVG e média ponderada)                             │
 │ 8    │ PORCENTAGEM                                                │
 │ 9    │ SOMA COM CONDIÇÃO (CASE e FILTER)                          │
 │ 10   │ CONTAS COMBINADAS (relatório completo)                     │
 └──────┴────────────────────────────────────────────────────────────┘

 LEMBRETES
 • Conta de LINHA  (+ - * /)      → acontece em cada linha
 • Conta de GRUPO  (SUM AVG ...)  → junta várias linhas em uma
 • Ordem: ( ) → ^ → * / % → + -
 • Inteiro / inteiro corta as decimais: 7 / 2 = 3. Use 7 / 2.0 = 3.5
*/


/* ═════════════════════════════════════════════════════════════════════
   PARTE 1. SOMA
   ═════════════════════════════════════════════════════════════════════*/

-- 1.1 SOMA ENTRE COLUNAS (conta de linha)
-- Soma o valor unitário com um frete fixo de R$ 5,00 em cada item.
SELECT
    venda_id,
    valor_unitario,
    valor_unitario + 5.00 AS valor_com_frete
FROM vendas_itens;

-- 1.2 SOMA DE TODOS OS VALORES (conta de grupo)
-- Soma o valor unitário das 50 linhas. Resultado: 1 linha só.
SELECT
    SUM(valor_unitario) AS soma_geral
FROM vendas_itens;

-- 1.3 SOMA POR VENDA
-- Soma os valores dos itens de cada venda (uma linha por venda).
SELECT
    venda_id,
    SUM(valor_unitario) AS soma_da_venda
FROM vendas_itens
GROUP BY venda_id
ORDER BY venda_id;

-- 1.4 SOMA POR DIA
-- Quanto foi vendido em cada data.
SELECT
    data_venda,
    SUM(valor_unitario) AS soma_do_dia
FROM vendas_itens
GROUP BY data_venda
ORDER BY data_venda;

-- 1.5 SOMA DAS QUANTIDADES POR PRODUTO
-- Total de unidades ou quilos vendidos de cada produto.
-- A unidade entra no GROUP BY para não somar kg com unidades.
SELECT
    produto_id,
    unidade,
    SUM(quantidade) AS qtd_total
FROM vendas_itens2
GROUP BY produto_id, unidade
ORDER BY produto_id;

-- 1.6 SOMA ACUMULADA (vai somando dia após dia)
-- SUM(...) OVER (ORDER BY ...) soma a linha atual com todas as anteriores.
SELECT
    data_venda,
    SUM(valor_unitario)                                  AS soma_do_dia,
    SUM(SUM(valor_unitario)) OVER (ORDER BY data_venda)  AS soma_acumulada
FROM vendas_itens
GROUP BY data_venda
ORDER BY data_venda;

-- 1.7 SOMA SEM REPETIDOS
-- SUM(DISTINCT ...) conta cada valor diferente só uma vez.
-- Produto 4: 5 linhas de 220,00 → SUM = 1100,00 | SUM DISTINCT = 220,00
SELECT
    SUM(valor_unitario)          AS soma_normal,
    SUM(DISTINCT valor_unitario) AS soma_sem_repetidos
FROM vendas_itens
WHERE produto_id = 4;


/* ═════════════════════════════════════════════════════════════════════
   PARTE 2. SUBTRAÇÃO
   ═════════════════════════════════════════════════════════════════════*/

-- 2.1 DESCONTO FIXO
-- Tira R$ 10,00 de cada item.
SELECT
    venda_id,
    valor_unitario,
    valor_unitario - 10.00 AS valor_com_desconto
FROM vendas_itens;

-- 2.2 DIFERENÇA ENTRE O MAIOR E O MENOR PREÇO DE CADA PRODUTO
-- Mostra quanto o preço variou entre as vendas.
SELECT
    produto_id,
    MIN(valor_unitario)                       AS menor_preco,
    MAX(valor_unitario)                       AS maior_preco,
    MAX(valor_unitario) - MIN(valor_unitario) AS variacao
FROM vendas_itens2
GROUP BY produto_id
ORDER BY variacao DESC;

-- 2.3 DIFERENÇA ENTRE AS DUAS AULAS
-- Mesmo item (venda + produto): preço da aula 4 menos preço da aula 3.
SELECT
    a3.venda_id,
    a3.produto_id,
    a3.valor_unitario                     AS preco_aula3,
    a4.valor_unitario                     AS preco_aula4,
    a4.valor_unitario - a3.valor_unitario AS diferenca
FROM vendas_itens a3
JOIN vendas_itens2 a4
  ON a4.venda_id = a3.venda_id
 AND a4.produto_id = a3.produto_id
WHERE a4.valor_unitario <> a3.valor_unitario
ORDER BY diferenca DESC;

-- 2.4 SUBTRAÇÃO DE DATAS
-- Data menos data = número de dias entre elas.
SELECT
    venda_id,
    data_venda,
    data_venda - DATE '2025-09-01' AS dias_desde_o_inicio
FROM vendas_itens2
GROUP BY venda_id, data_venda
ORDER BY venda_id;


/* ═════════════════════════════════════════════════════════════════════
   PARTE 3. MULTIPLICAÇÃO
   ═════════════════════════════════════════════════════════════════════*/

-- 3.1 VALOR DO ITEM = QUANTIDADE × PREÇO
-- Conta de linha. Resultado com 5 casas (3 da quantidade + 2 do valor).
SELECT
    venda_id,
    produto_id,
    quantidade,
    valor_unitario,
    quantidade * valor_unitario AS valor_item
FROM vendas_itens2;

-- 3.2 VALOR TOTAL DE CADA VENDA
-- Multiplica DENTRO do SUM: primeiro cada linha, depois soma.
SELECT
    venda_id,
    data_venda,
    ROUND(SUM(quantidade * valor_unitario), 2) AS total_venda
FROM vendas_itens2
GROUP BY venda_id, data_venda
ORDER BY venda_id;

-- 3.3 FATURAMENTO TOTAL DA LOJA
-- Soma de quantidade × preço de todas as linhas. Resultado: 7320.06
SELECT
    ROUND(SUM(quantidade * valor_unitario), 2) AS faturamento_total
FROM vendas_itens2;

-- 3.4 ACRÉSCIMO DE 10%
-- Multiplicar por 1.10 = valor + 10%.
SELECT
    venda_id,
    valor_unitario,
    ROUND(valor_unitario * 1.10, 2) AS com_10_porcento
FROM vendas_itens;

-- 3.5 DESCONTO DE 15%
-- Multiplicar por 0.85 = valor - 15%.
SELECT
    venda_id,
    valor_unitario,
    ROUND(valor_unitario * 0.15, 2) AS valor_do_desconto,
    ROUND(valor_unitario * 0.85, 2) AS com_desconto
FROM vendas_itens;

-- 3.6 COMPARAÇÃO: FORMA CERTA x FORMA ERRADA
-- Certa:  SUM(quantidade * valor_unitario)
-- Errada: SUM(quantidade) * SUM(valor_unitario)  (multiplica os totais)
SELECT
    venda_id,
    ROUND(SUM(quantidade * valor_unitario), 2)        AS certo,
    ROUND(SUM(quantidade) * SUM(valor_unitario), 2)   AS errado
FROM vendas_itens2
WHERE venda_id = 2001
GROUP BY venda_id;

-- 3.7 POTÊNCIA (^)
-- Exemplo didático: valor ao quadrado.
SELECT
    produto_id,
    valor_unitario,
    valor_unitario ^ 2 AS ao_quadrado
FROM vendas_itens
WHERE venda_id = 2001;


/* ═════════════════════════════════════════════════════════════════════
   PARTE 4. DIVISÃO
   ═════════════════════════════════════════════════════════════════════*/

-- 4.1 DIVISÃO INTEIRA x DIVISÃO DECIMAL
-- produto_id é INTEGER. 10 / 3 corta as decimais.
SELECT DISTINCT
    produto_id,
    produto_id / 3            AS divisao_inteira,   -- 3
    produto_id / 3.0          AS divisao_decimal,   -- 3.333...
    produto_id::numeric / 3   AS com_conversao,     -- 3.333...
    ROUND(produto_id / 3.0, 2) AS arredondado       -- 3.33
FROM vendas_itens
WHERE produto_id = 10;

-- 4.2 VALOR DIVIDIDO EM PARCELAS
-- Total de cada venda dividido em 3 parcelas.
SELECT
    venda_id,
    ROUND(SUM(quantidade * valor_unitario), 2)     AS total,
    ROUND(SUM(quantidade * valor_unitario) / 3, 2) AS parcela_3x
FROM vendas_itens2
GROUP BY venda_id
ORDER BY venda_id;

-- 4.3 PREÇO MÉDIO PONDERADO (divisão de duas somas)
-- Dinheiro total ÷ quantidade total.
-- NULLIF(..., 0) evita o erro "division by zero".
SELECT
    produto_id,
    unidade,
    SUM(quantidade)                                                        AS qtd_total,
    ROUND(SUM(quantidade * valor_unitario), 2)                             AS total,
    ROUND(SUM(quantidade * valor_unitario) / NULLIF(SUM(quantidade), 0), 2) AS preco_medio
FROM vendas_itens2
GROUP BY produto_id, unidade
ORDER BY produto_id;

-- 4.4 TICKET MÉDIO (faturamento ÷ número de vendas)
-- Quanto, em média, cada cliente gastou por venda.
SELECT
    ROUND(SUM(quantidade * valor_unitario), 2)                          AS faturamento,
    COUNT(DISTINCT venda_id)                                            AS qtd_vendas,
    ROUND(SUM(quantidade * valor_unitario) / COUNT(DISTINCT venda_id), 2) AS ticket_medio
FROM vendas_itens2;

-- 4.5 ITENS POR VENDA (inteiros convertidos para decimal)
-- COUNT devolve inteiro. Sem ::numeric, 50 / 16 daria 3.
SELECT
    COUNT(*)                                            AS itens,
    COUNT(DISTINCT venda_id)                            AS vendas,
    COUNT(*) / COUNT(DISTINCT venda_id)                 AS errado_inteiro,
    ROUND(COUNT(*)::numeric / COUNT(DISTINCT venda_id), 2) AS certo_decimal
FROM vendas_itens;

-- 4.6 DIVISÃO PROTEGIDA
-- Se o divisor for zero, o resultado vira NULL em vez de erro.
SELECT
    10 / NULLIF(0, 0)                   AS resultado_nulo,
    COALESCE(10 / NULLIF(0, 0), 0)      AS resultado_zero;


/* ═════════════════════════════════════════════════════════════════════
   PARTE 5. RESTO DA DIVISÃO (%)
   ═════════════════════════════════════════════════════════════════════*/

-- 5.1 VENDA PAR OU ÍMPAR
-- Resto 0 = par | resto 1 = ímpar.
SELECT DISTINCT
    venda_id,
    venda_id % 2 AS resto,
    CASE WHEN venda_id % 2 = 0 THEN 'Par' ELSE 'Impar' END AS tipo
FROM vendas_itens
ORDER BY venda_id;

-- 5.2 UNIDADES EM CAIXAS DE 6
-- Divisão inteira = caixas cheias | resto = unidades soltas.
SELECT
    venda_id,
    produto_id,
    quantidade::integer            AS unidades,
    quantidade::integer / 6        AS caixas_cheias,
    quantidade::integer % 6        AS unidades_soltas
FROM vendas_itens2
WHERE unidade = 'UN' AND quantidade >= 6;


/* ═════════════════════════════════════════════════════════════════════
   PARTE 6. ARREDONDAMENTO
   ═════════════════════════════════════════════════════════════════════*/

-- 6.1 AS 4 FORMAS DE ARREDONDAR
-- ROUND = normal | TRUNC = corta | CEIL = para cima | FLOOR = para baixo
SELECT
    venda_id,
    produto_id,
    quantidade * valor_unitario               AS valor_original,
    ROUND(quantidade * valor_unitario, 2)     AS round_2_casas,
    TRUNC(quantidade * valor_unitario, 2)     AS trunc_2_casas,
    CEIL(quantidade * valor_unitario)         AS ceil_para_cima,
    FLOOR(quantidade * valor_unitario)        AS floor_para_baixo
FROM vendas_itens2
WHERE unidade = 'Kg';

-- 6.2 QUANTAS SACOLAS SÃO NECESSÁRIAS (CEIL)
-- Cada sacola leva 3 itens. 5 itens → 2 sacolas.
SELECT
    venda_id,
    COUNT(*)                   AS itens,
    CEIL(COUNT(*) / 3.0)       AS sacolas
FROM vendas_itens2
GROUP BY venda_id
ORDER BY venda_id;


/* ═════════════════════════════════════════════════════════════════════
   PARTE 7. MÉDIAS
   ═════════════════════════════════════════════════════════════════════*/

-- 7.1 MÉDIA SIMPLES (AVG) x MÉDIA MANUAL (SUM ÷ COUNT)
-- As duas dão o mesmo resultado.
SELECT
    ROUND(AVG(valor_unitario), 2)                  AS media_avg,
    ROUND(SUM(valor_unitario) / COUNT(*), 2)       AS media_manual
FROM vendas_itens;

-- 7.2 MÉDIA SIMPLES x MÉDIA PONDERADA
-- Simples: todas as vendas pesam igual.
-- Ponderada: vendas com mais quantidade pesam mais.
-- Produto 3: simples = 46.34 | ponderada = 46.16
SELECT
    produto_id,
    ROUND(AVG(valor_unitario), 2)                                   AS media_simples,
    ROUND(SUM(quantidade * valor_unitario) / SUM(quantidade), 2)    AS media_ponderada
FROM vendas_itens2
GROUP BY produto_id
ORDER BY produto_id;

-- 7.3 ITENS ACIMA DA MÉDIA
-- A subconsulta calcula a média; o WHERE compara cada linha com ela.
SELECT
    venda_id,
    produto_id,
    valor_unitario,
    ROUND(valor_unitario - (SELECT AVG(valor_unitario) FROM vendas_itens), 2) AS acima_da_media
FROM vendas_itens
WHERE valor_unitario > (SELECT AVG(valor_unitario) FROM vendas_itens)
ORDER BY acima_da_media DESC;


/* ═════════════════════════════════════════════════════════════════════
   PARTE 8. PORCENTAGEM
   Fórmula: 100.0 * parte / total
   O 100.0 (com ponto) evita a divisão inteira.
   ═════════════════════════════════════════════════════════════════════*/

-- 8.1 PARTICIPAÇÃO DE CADA VENDA NO FATURAMENTO
-- SUM(...) OVER () = total geral, calculado junto com cada grupo.
SELECT
    venda_id,
    ROUND(SUM(quantidade * valor_unitario), 2) AS total_venda,
    ROUND(100.0 * SUM(quantidade * valor_unitario)
          / SUM(SUM(quantidade * valor_unitario)) OVER (), 2) AS percentual
FROM vendas_itens2
GROUP BY venda_id
ORDER BY percentual DESC;

-- 8.2 PERCENTUAL DE ITENS COM OBSERVAÇÃO
-- 14 de 50 = 28%.
SELECT
    COUNT(observacao)                                   AS com_obs,
    COUNT(*)                                            AS total,
    ROUND(100.0 * COUNT(observacao) / COUNT(*), 2)      AS percentual
FROM vendas_itens;

-- 8.3 VARIAÇÃO PERCENTUAL DE PREÇO (aula 3 → aula 4)
-- (novo - antigo) / antigo × 100
SELECT
    a3.venda_id,
    a3.produto_id,
    a3.valor_unitario AS antigo,
    a4.valor_unitario AS novo,
    ROUND(100.0 * (a4.valor_unitario - a3.valor_unitario) / a3.valor_unitario, 2) AS variacao_pct
FROM vendas_itens a3
JOIN vendas_itens2 a4
  ON a4.venda_id = a3.venda_id
 AND a4.produto_id = a3.produto_id
WHERE a4.valor_unitario <> a3.valor_unitario
ORDER BY variacao_pct DESC;


/* ═════════════════════════════════════════════════════════════════════
   PARTE 9. SOMA COM CONDIÇÃO
   ═════════════════════════════════════════════════════════════════════*/

-- 9.1 SOMA SEPARADA COM CASE
-- Soma só quando a condição é verdadeira; senão soma 0.
SELECT
    ROUND(SUM(CASE WHEN unidade = 'Kg' THEN quantidade * valor_unitario ELSE 0 END), 2) AS total_kg,
    ROUND(SUM(CASE WHEN unidade = 'UN' THEN quantidade * valor_unitario ELSE 0 END), 2) AS total_un,
    ROUND(SUM(quantidade * valor_unitario), 2)                                         AS total_geral
FROM vendas_itens2;

-- 9.2 A MESMA SOMA COM FILTER (só PostgreSQL)
SELECT
    ROUND(SUM(quantidade * valor_unitario) FILTER (WHERE unidade = 'Kg'), 2) AS total_kg,
    ROUND(SUM(quantidade * valor_unitario) FILTER (WHERE unidade = 'UN'), 2) AS total_un
FROM vendas_itens2;

-- 9.3 CONTAGEM COM CONDIÇÃO POR VENDA
-- Quantos itens de cada tipo há em cada venda.
SELECT
    venda_id,
    COUNT(*) FILTER (WHERE unidade = 'Kg') AS itens_kg,
    COUNT(*) FILTER (WHERE unidade = 'UN') AS itens_un
FROM vendas_itens2
GROUP BY venda_id
ORDER BY venda_id;

-- 9.4 PERCENTUAL DO FATURAMENTO QUE VEIO DE PRODUTOS POR QUILO
-- Resultado: 7.66
SELECT
    ROUND(100.0
        * SUM(CASE WHEN unidade = 'Kg' THEN quantidade * valor_unitario ELSE 0 END)
        / SUM(quantidade * valor_unitario), 2) AS percentual_kg
FROM vendas_itens2;


/* ═════════════════════════════════════════════════════════════════════
   PARTE 10. CONTAS COMBINADAS (relatório completo por venda)
   Junta soma, subtração, multiplicação, divisão e porcentagem.
   ═════════════════════════════════════════════════════════════════════*/
SELECT
    venda_id,
    data_venda,
    COUNT(*)                                                    AS itens,
    ROUND(SUM(quantidade * valor_unitario), 2)                  AS subtotal,
    ROUND(SUM(quantidade * valor_unitario) * 0.05, 2)           AS desconto_5pct,
    ROUND(SUM(quantidade * valor_unitario) * 0.95, 2)           AS total_com_desconto,
    ROUND(SUM(quantidade * valor_unitario) * 0.95 / 3, 2)       AS parcela_3x,
    ROUND(SUM(quantidade * valor_unitario) / COUNT(*), 2)       AS media_por_item,
    ROUND(100.0 * SUM(quantidade * valor_unitario)
          / SUM(SUM(quantidade * valor_unitario)) OVER (), 2)   AS pct_do_faturamento
FROM vendas_itens2
GROUP BY venda_id, data_venda
HAVING SUM(quantidade * valor_unitario) > 300
ORDER BY subtotal DESC;
/*
 Leitura do bloco 10:
 ┌────────────────────────┬─────────────────────────────────────────────────┐
 │ COUNT(*)               │ quantos itens a venda tem                       │
 │ SUM(q * v)             │ subtotal: multiplica cada item e soma           │
 │ ... * 0.05             │ valor de 5% de desconto                         │
 │ ... * 0.95             │ total já com 5% de desconto                     │
 │ ... * 0.95 / 3         │ total com desconto dividido em 3 parcelas       │
 │ SUM(q * v) / COUNT(*)  │ valor médio de cada item da venda               │
 │ 100.0 * ... / OVER ()  │ quanto essa venda representa do total da loja   │
 │ HAVING ... > 300       │ só vendas com subtotal acima de R$ 300          │
 └────────────────────────┴─────────────────────────────────────────────────┘

 RESUMINDO
 • +  -  *  /  fazem contas em cada linha.
 • SUM, AVG, COUNT fazem contas em grupos.
 • Multiplique DENTRO do SUM.
 • Use 100.0 ou ::numeric para não perder as decimais.
 • Proteja divisões com NULLIF(divisor, 0).
 • Arredonde dinheiro com ROUND(..., 2).
*/
