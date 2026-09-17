/**
 * LISTA SIMPLES ENCADEADA
 *
 * Cada no aponta so para o proximo. Anda so para frente.
 *
 * Compilar e rodar:
 *   javac ListaSimples.java
 *   java ListaSimples
 */
public class ListaSimples<T> {

    private No<T> primeiro;
    private No<T> ultimo;
    private int quantidade;


   
   
    // CRIAR
   
   

    // ListaSimples()
    // Cria uma lista vazia.
    public ListaSimples() {
        this.primeiro = null;
        this.ultimo = null;
        this.quantidade = 0;
    }


   
   
    // CONSULTAR
   
   

    // estaVazia()
    // Responde se a lista nao tem nenhum item.
    public boolean estaVazia() {
        return this.quantidade == 0;
    }

    // tamanho()
    // Devolve quantos itens a lista tem.
    public int tamanho() {
        return this.quantidade;
    }

    // primeiro()
    // Devolve o dado do primeiro item, sem remover.
    public T primeiro() {
        if (estaVazia()) return null;
        return this.primeiro.dado;
    }

    // ultimo()
    // Devolve o dado do ultimo item, sem remover.
    public T ultimo() {
        if (estaVazia()) return null;
        return this.ultimo.dado;
    }

    // obter(posicao)
    // Devolve o dado daquela posicao. Posicao comeca em 0.
    public T obter(int posicao) {
        if (posicao < 0 || posicao >= this.quantidade) return null;

        No<T> atual = this.primeiro;
        for (int i = 0; i < posicao; i++) {
            atual = atual.proximo;
        }
        return atual.dado;
    }

    // posicaoDe(dado)
    // Devolve em que posicao o dado esta. Devolve -1 se nao achar.
    public int posicaoDe(T dado) {
        No<T> atual = this.primeiro;
        int posicao = 0;
        while (atual != null) {
            if (atual.dado.equals(dado)) return posicao;
            atual = atual.proximo;
            posicao++;
        }
        return -1;
    }

    // contem(dado)
    // Responde se o dado existe na lista.
    public boolean contem(T dado) {
        return posicaoDe(dado) >= 0;
    }


   
   
    // INSERIR
   
   

    // addInicio(dado)
    // Coloca um item novo na frente de todos.
    public void addInicio(T dado) {
        No<T> novo = new No<T>(dado);

        if (estaVazia()) {
            this.primeiro = novo;
            this.ultimo = novo;
        } else {
            novo.proximo = this.primeiro;
            this.primeiro = novo;
        }
        this.quantidade++;
    }

    // addFinal(dado)
    // Coloca um item novo depois de todos.
    public void addFinal(T dado) {
        No<T> novo = new No<T>(dado);

        if (estaVazia()) {
            this.primeiro = novo;
            this.ultimo = novo;
        } else {
            this.ultimo.proximo = novo;
            this.ultimo = novo;
        }
        this.quantidade++;
    }

    // addNaPosicao(posicao, dado)
    // Coloca um item novo no meio, naquela posicao.
    public boolean addNaPosicao(int posicao, T dado) {
        if (posicao < 0 || posicao > this.quantidade) return false;
        if (posicao == 0) { addInicio(dado); return true; }
        if (posicao == this.quantidade) { addFinal(dado); return true; }

        No<T> antes = this.primeiro;
        for (int i = 0; i < posicao - 1; i++) {
            antes = antes.proximo;
        }

        No<T> novo = new No<T>(dado);
        novo.proximo = antes.proximo;
        antes.proximo = novo;

        this.quantidade++;
        return true;
    }


   
   
    // REMOVER
   
   

    // removeInicio()
    // Tira o primeiro item e devolve o dado dele.
    public T removeInicio() {
        if (estaVazia()) return null;

        T dado = this.primeiro.dado;

        if (this.quantidade == 1) {
            this.primeiro = null;
            this.ultimo = null;
        } else {
            this.primeiro = this.primeiro.proximo;
        }

        this.quantidade--;
        return dado;
    }

    // removeFinal()
    // Tira o ultimo item e devolve o dado dele.
    // Precisa caminhar do comeco ate o penultimo, porque as setas so vao para frente.
    public T removeFinal() {
        if (estaVazia()) return null;

        T dado = this.ultimo.dado;

        if (this.quantidade == 1) {
            this.primeiro = null;
            this.ultimo = null;
        } else {
            No<T> atual = this.primeiro;
            while (atual.proximo != this.ultimo) {
                atual = atual.proximo;
            }
            atual.proximo = null;
            this.ultimo = atual;
        }

        this.quantidade--;
        return dado;
    }

    // removeNaPosicao(posicao)
    // Tira o item daquela posicao e devolve o dado dele.
    public T removeNaPosicao(int posicao) {
        if (posicao < 0 || posicao >= this.quantidade) return null;
        if (posicao == 0) return removeInicio();
        if (posicao == this.quantidade - 1) return removeFinal();

        No<T> antes = this.primeiro;
        for (int i = 0; i < posicao - 1; i++) {
            antes = antes.proximo;
        }

        No<T> alvo = antes.proximo;
        antes.proximo = alvo.proximo;

        this.quantidade--;
        return alvo.dado;
    }

    // remove(dado)
    // Procura o dado e tira a primeira ocorrencia dele.
    public boolean remove(T dado) {
        int posicao = posicaoDe(dado);
        if (posicao < 0) return false;
        removeNaPosicao(posicao);
        return true;
    }

    // limpar()
    // Esvazia a lista inteira de uma vez.
    public void limpar() {
        this.primeiro = null;
        this.ultimo = null;
        this.quantidade = 0;
    }


   
   
    // ALTERAR
   
   

    // atualizar(posicao, dado)
    // Troca o dado daquela posicao por outro.
    public boolean atualizar(int posicao, T dado) {
        if (posicao < 0 || posicao >= this.quantidade) return false;

        No<T> atual = this.primeiro;
        for (int i = 0; i < posicao; i++) {
            atual = atual.proximo;
        }
        atual.dado = dado;
        return true;
    }


   
   
    // PERCORRER
   
   

    // imprimir()
    // Mostra os itens do primeiro para o ultimo.
    public void imprimir() {
        System.out.println("Lista simples (" + this.quantidade + " itens):");

        if (estaVazia()) {
            System.out.println("   (vazia)");
            return;
        }

        No<T> atual = this.primeiro;
        int posicao = 0;
        while (atual != null) {
            System.out.println("   [" + posicao + "] " + atual.dado);
            atual = atual.proximo;
            posicao++;
        }
    }

    // inverter()
    // Vira a lista do avesso: o primeiro passa a ser o ultimo.
    public void inverter() {
        No<T> anterior = null;
        No<T> atual = this.primeiro;

        this.ultimo = this.primeiro;

        while (atual != null) {
            No<T> guardaProximo = atual.proximo;
            atual.proximo = anterior;
            anterior = atual;
            atual = guardaProximo;
        }

        this.primeiro = anterior;
    }


   
   
    // O NO
   
   

    // No
    // A caixa que guarda um dado e uma seta para o proximo.
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

        ListaSimples<String> lista = new ListaSimples<String>();

        System.out.println("--- CRIAR ---");
        System.out.println("estaVazia() = " + lista.estaVazia());

        System.out.println();
        System.out.println("--- INSERIR ---");
        lista.addFinal("Bruno");       System.out.println("addFinal(Bruno)");
        lista.addFinal("Maria");       System.out.println("addFinal(Maria)");
        lista.addInicio("Arthur");     System.out.println("addInicio(Arthur)");
        lista.addNaPosicao(2, "Carla"); System.out.println("addNaPosicao(2, Carla)");
        System.out.println();
        lista.imprimir();

        System.out.println();
        System.out.println("--- CONSULTAR ---");
        System.out.println("tamanho()          = " + lista.tamanho());
        System.out.println("primeiro()         = " + lista.primeiro());
        System.out.println("ultimo()           = " + lista.ultimo());
        System.out.println("obter(1)           = " + lista.obter(1));
        System.out.println("posicaoDe(Carla)   = " + lista.posicaoDe("Carla"));
        System.out.println("contem(Maria)      = " + lista.contem("Maria"));

        System.out.println();
        System.out.println("--- ALTERAR ---");
        lista.atualizar(0, "Arthur Silva");
        System.out.println("atualizar(0, Arthur Silva)");
        System.out.println();
        lista.imprimir();

        System.out.println();
        System.out.println("--- INVERTER ---");
        lista.inverter();
        System.out.println("inverter()");
        System.out.println();
        lista.imprimir();

        System.out.println();
        System.out.println("--- REMOVER ---");
        System.out.println("removeInicio()     -> saiu: " + lista.removeInicio());
        System.out.println("removeFinal()      -> saiu: " + lista.removeFinal());
        System.out.println("removeNaPosicao(0) -> saiu: " + lista.removeNaPosicao(0));
        System.out.println();
        lista.imprimir();

        System.out.println();
        System.out.println("--- LIMPAR ---");
        lista.limpar();
        System.out.println("limpar()");
        System.out.println();
        lista.imprimir();
    }
}
