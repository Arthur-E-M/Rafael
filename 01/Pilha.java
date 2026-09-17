/**
 * PILHA
 *
 * LIFO: o ultimo que entra e o primeiro que sai.
 * Como uma pilha de pratos: voce poe em cima e tira de cima.
 *
 * Todo trabalho acontece no topo. Nao existe entrar pelo meio nem pelo fim.
 *
 * Compilar e rodar:
 *   javac Pilha.java
 *   java Pilha
 */
public class Pilha<T> {

    private No<T> topo;
    private int quantidade;


  
  
  
    // CRIAR
  
  
  

    // Pilha()
    // Cria uma pilha vazia.
    public Pilha() {
        this.topo = null;
        this.quantidade = 0;
    }


  
  
  
    // AS TRES OPERACOES PRINCIPAIS
  
  
  

    // push(dado)
    // Poe um item em cima da pilha.
    public void push(T dado) {
        No<T> novo = new No<T>(dado);
        novo.proximo = this.topo;
        this.topo = novo;
        this.quantidade++;
    }

    // pop()
    // Tira o item de cima e devolve o dado dele.
    public T pop() {
        if (estaVazia()) return null;

        T dado = this.topo.dado;
        this.topo = this.topo.proximo;
        this.quantidade--;
        return dado;
    }

    // peek()
    // Espia o item de cima, sem tirar.
    public T peek() {
        if (estaVazia()) return null;
        return this.topo.dado;
    }


  
  
  
    // CONSULTAR
  
  
  

    // estaVazia()
    // Responde se a pilha nao tem nenhum item.
    public boolean estaVazia() {
        return this.quantidade == 0;
    }

    // tamanho()
    // Devolve quantos itens a pilha tem.
    public int tamanho() {
        return this.quantidade;
    }

    // contem(dado)
    // Responde se o dado existe na pilha, em qualquer altura.
    public boolean contem(T dado) {
        No<T> atual = this.topo;
        while (atual != null) {
            if (atual.dado.equals(dado)) return true;
            atual = atual.proximo;
        }
        return false;
    }

    // alturaDe(dado)
    // Devolve a quantos itens do topo o dado esta. Topo e 0. Devolve -1 se nao achar.
    public int alturaDe(T dado) {
        No<T> atual = this.topo;
        int altura = 0;
        while (atual != null) {
            if (atual.dado.equals(dado)) return altura;
            atual = atual.proximo;
            altura++;
        }
        return -1;
    }


  
  
  
    // LIMPAR
  
  
  

    // limpar()
    // Esvazia a pilha inteira de uma vez.
    public void limpar() {
        this.topo = null;
        this.quantidade = 0;
    }


  
  
  
    // PERCORRER
  
  
  

    // imprimir()
    // Mostra a pilha do topo para a base.
    public void imprimir() {
        System.out.println("Pilha (" + this.quantidade + " itens) - do topo para a base:");

        if (estaVazia()) {
            System.out.println("   (vazia)");
            return;
        }

        No<T> atual = this.topo;
        int altura = 0;
        while (atual != null) {
            String marca = (altura == 0) ? "  <-- topo" : "";
            System.out.println("   [" + altura + "] " + atual.dado + marca);
            atual = atual.proximo;
            altura++;
        }
    }


  
  
  
    // O NO
  
  
  

    // No
    // A caixa que guarda um dado e uma seta para o item de baixo.
    private static class No<T> {
        T dado;
        No<T> proximo;

        No(T dado) {
            this.dado = dado;
            this.proximo = null;
        }
    }


  
  
  
    // TESTE
  
  
  

    public static void main(String[] args) {

        Pilha<String> pilha = new Pilha<String>();

        System.out.println("--- CRIAR ---");
        System.out.println("estaVazia() = " + pilha.estaVazia());

        System.out.println();
        System.out.println("--- PUSH: POR EM CIMA ---");
        pilha.push("Prato 1"); System.out.println("push(Prato 1)");
        pilha.push("Prato 2"); System.out.println("push(Prato 2)");
        pilha.push("Prato 3"); System.out.println("push(Prato 3)");
        System.out.println();
        pilha.imprimir();

        System.out.println();
        System.out.println("--- CONSULTAR ---");
        System.out.println("tamanho()          = " + pilha.tamanho());
        System.out.println("peek()             = " + pilha.peek());
        System.out.println("contem(Prato 1)    = " + pilha.contem("Prato 1"));
        System.out.println("alturaDe(Prato 1)  = " + pilha.alturaDe("Prato 1"));

        System.out.println();
        System.out.println("--- POP: TIRAR DE CIMA ---");
        System.out.println("pop() -> tirou: " + pilha.pop());
        System.out.println("pop() -> tirou: " + pilha.pop());
        System.out.println();
        pilha.imprimir();
        System.out.println();
        System.out.println("O ultimo que entrou (Prato 3) foi o primeiro a sair.");

        System.out.println();
        System.out.println("--- LIMPAR ---");
        pilha.limpar();
        System.out.println("limpar()");
        System.out.println();
        pilha.imprimir();

        System.out.println();
        System.out.println("--- EXEMPLO DE USO: INVERTER UMA PALAVRA ---");
        String palavra = "ESTRUTURA";
        Pilha<Character> letras = new Pilha<Character>();

        for (int i = 0; i < palavra.length(); i++) {
            letras.push(palavra.charAt(i));
        }

        String invertida = "";
        while (!letras.estaVazia()) {
            invertida = invertida + letras.pop();
        }

        System.out.println("Original:  " + palavra);
        System.out.println("Invertida: " + invertida);
        System.out.println("A pilha inverte de graca, porque devolve na ordem contraria.");
    }
}
