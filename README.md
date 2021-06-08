# HtmlFormatterForJavaCode
Aplicação Java para formatar código Java para que possa ser inserido e exibido em um documento HTML.

Você já quis inserir o seu código fonte Java em um documento HTML para ser exibido em uma página da web? O problema é que as tags CODE e PRE apenas 
permitem que o texto seja mostrado sem alterar indentações mas não colorem o fonte de acordo com a sintaxe e também não numeram as linhas para que se possa 
fazer referência a determinada parte do código e, além disso, sinais de maior (>) e menor (<) que porventura estiverem no fonte serão interpretados como
delimitadores de tags, e não como operadores relacionais matemáticos.

Para resolver este problema estou desenvolvendo esta pequena aplicação Java que tem por objetivo editar um arquivo com código fonte Java e gerar outro
arquivo texto que deve fornecer o código Java original formatado para ser inserido em um documento HTML.

Este texto formatado terá palavras reservadas da linguagem, constantes, funções, comentários, strings literais e caracteres delimitados por tags
que relacionam estes elementos com suas classes específicas, de modo que possam receber, cada um deles, uma formatação especial via CSS3.

Também, o arquivo gerado, irá numerar automaticamente as linhas de código.

O projeto já atinge funcionalmente estes objetivos. Futuramente pretendo atualizar este repositório com melhorias e novos recursos.
