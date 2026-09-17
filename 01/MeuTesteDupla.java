/**
 * MEU TESTE DUPLA
 *
 * Coloca objetos Aluno dentro da ListaDupla.
 * Aqui voce so da ordens. Nao mexe nas classes.
 *
 * Precisa estar na mesma pasta que ListaDupla.java e Aluno.java
 *
 * Rodar:
 *   javac *.java
 *   java MeuTesteDupla
 */
public class MeuTesteDupla {

    public static void main(String[] args) {

       
       
        // 1. CRIAR A LISTA
        // Entre < > vai o tipo que a lista aceita. Aqui e Aluno.
       
       

        ListaDupla<Aluno> lista = new ListaDupla<Aluno>();


       
       
        // 2. CRIAR OS OBJETOS
        // Cada linha cria um Aluno com nome, idade e turma.
       
       

        Aluno a1 = new Aluno("Arthur", 18, 4);
        Aluno a2 = new Aluno("Maria", 20, 3);
        Aluno a3 = new Aluno("Bruno", 19, 2);


       
       
        // 3. COLOCAR OS OBJETOS NA LISTA
        // Passa a variavel do objeto, sem aspas.
       
       

        lista.addFinal(a1);
        lista.addFinal(a2);
        lista.addFinal(a3);


       
       
        // 4. VER O QUE TEM DENTRO
       
       

        lista.imprimirFrente();

        System.out.println();
        lista.imprimirTras();


       
       
        // 5. TIRAR UM OBJETO
       
       

        System.out.println();
        Aluno saiu = lista.removeInicio();
        System.out.println("Saiu: " + saiu);

        System.out.println();
        lista.imprimirFrente();


       
       
        // 6. LER UM DADO DE DENTRO DO OBJETO
       
       

        System.out.println();
        Aluno primeiro = lista.primeiro();
        System.out.println("Nome do primeiro:  " + primeiro.getNome());
        System.out.println("Idade do primeiro: " + primeiro.getIdade());
        System.out.println("Turma do primeiro: " + primeiro.getTurma());


       
       
        // DAQUI PARA BAIXO E SEU ESPACO
        // Apague as // da frente da linha que quiser usar.
       
       

        // Criar e colocar de uma vez, sem variavel no meio:
        // lista.addFinal(new Aluno("Gabi", 20, 8));
        // lista.addInicio(new Aluno("Carla", 21, 1));
        // lista.addNaPosicao(1, new Aluno("Joao", 22, 5));

        // Tirar:
        // lista.removeInicio();
        // lista.removeFinal();
        // lista.removeNaPosicao(1);
        // lista.remove(a2);                 // tira o objeto a2, onde ele estiver

        // Trocar:
        // lista.atualizar(0, new Aluno("Arthur Silva", 18, 4));

        // Consultar:
        // lista.imprimirFrente();
        // lista.imprimirTras();
        // System.out.println(lista.tamanho());
        // System.out.println(lista.primeiro());
        // System.out.println(lista.ultimo());
        // System.out.println(lista.obter(1));
        // System.out.println(lista.contem(a2));
        // System.out.println(lista.posicaoDe(a2));

        // Mexer no objeto depois de ele estar na lista:
        // a1.setIdade(19);                  // muda a idade do Arthur

        // Virar e apagar:
        // lista.inverter();
        // lista.limpar();

    }
}
