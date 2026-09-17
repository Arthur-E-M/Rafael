/**
 * MEU TESTE
 *
 * Aqui voce so da ordens. Nao mexe nas classes.
 *
 * Precisa estar na mesma pasta que ListaSimples.java, ListaDupla.java e Pilha.java
 *
 * Rodar:
 *   javac *.java
 *   java MeuTeste
 */
public class MeuTeste {

    public static void main(String[] args) {

     
     
        // CRIAR
     
     

        ListaSimples<String> lista = new ListaSimples<String>();


     
     
        // COLOCAR DADOS
     
     

        lista.addFinal("Arthur");
        lista.addFinal("Maria");
        lista.addFinal("Bruno");


     
     
        // VER O QUE TEM DENTRO
     
     

        lista.imprimir();


     
     
        // TIRAR UM DADO
     
     

        System.out.println();
        String saiu = lista.removeInicio();
        System.out.println("Saiu: " + saiu);

        System.out.println();
        lista.imprimir();


     
     
        // DAQUI PARA BAIXO E SEU ESPACO
        // Escreva suas ordens aqui. Exemplos prontos para copiar:
     
     

        // lista.addInicio("Carla");          // coloca na frente de todos
        // lista.addFinal("Pedro");           // coloca depois de todos
        // lista.addNaPosicao(1, "Joao");     // coloca no meio, na posicao 1

        // lista.removeInicio();              // tira o primeiro
        // lista.removeFinal();               // tira o ultimo
        // lista.removeNaPosicao(2);          // tira o da posicao 2
        // lista.remove("Maria");             // tira o Maria, onde ele estiver

        // lista.atualizar(0, "Arthur Silva");// troca o dado da posicao 0

        // lista.imprimir();                              // mostra a lista
        // System.out.println(lista.tamanho());           // quantos itens
        // System.out.println(lista.primeiro());          // quem e o primeiro
        // System.out.println(lista.ultimo());            // quem e o ultimo
        // System.out.println(lista.obter(1));            // quem esta na posicao 1
        // System.out.println(lista.contem("Maria"));     // Maria esta na lista?
        // System.out.println(lista.posicaoDe("Maria"));  // em que posicao Maria esta

        // lista.inverter();                  // vira a lista do avesso
        // lista.limpar();                    // apaga tudo

    }
}
