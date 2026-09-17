/**
 * LISTA DUPLAMENTE ENCADEADA
 *
 * Cada no aponta para o anterior e para o proximo. Anda nos dois sentidos.
 *
 * Compilar e rodar:
 *   javac ListaDupla.java
 *   java ListaDupla
 */
public class ListaDupla<T> {

    private NoDuplo<T> primeiro;
    private NoDuplo<T> ultimo;
    private int quantidade;


  
    // CRIAR
    
    

    // ListaDupla()
    // Cria uma lista vazia.
    public ListaDupla() {
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
        NoDuplo<T> no = noDaPosicao(posicao);
        if (no == null) return null;
        return no.dado;
    }

    // posicaoDe(dado)
    // Devolve em que posicao o dado esta. Devolve -1 se nao achar.
    public int posicaoDe(T dado) {
        NoDuplo<T> atual = this.primeiro;
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
        NoDuplo<T> novo = new NoDuplo<T>(dado);

        if (estaVazia()) {
            this.primeiro = novo;
            this.ultimo = novo;
        } else {
            novo.proximo = this.primeiro;
            this.primeiro.anterior = novo;
            this.primeiro = novo;
        }
        this.quantidade++;
    }

    // addFinal(dado)
    // Coloca um item novo depois de todos.
    public void addFinal(T dado) {
        NoDuplo<T> novo = new NoDuplo<T>(dado);

        if (estaVazia()) {
            this.primeiro = novo;
            this.ultimo = novo;
        } else {
            this.ultimo.proximo = novo;
            novo.anterior = this.ultimo;
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

        NoDuplo<T> depois = noDaPosicao(posicao);
        NoDuplo<T> antes = depois.anterior;
        NoDuplo<T> novo = new NoDuplo<T>(dado);

        novo.anterior = antes;
        novo.proximo = depois;
        antes.proximo = novo;
        depois.anterior = novo;

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
            this.primeiro.anterior = null;
        }

        this.quantidade--;
        return dado;
    }

    // removeFinal()
    // Tira o ultimo item e devolve o dado dele.
    // Aqui e rapido: o ultimo no ja sabe quem esta atras dele.
    public T removeFinal() {
        if (estaVazia()) return null;

        T dado = this.ultimo.dado;

        if (this.quantidade == 1) {
            this.primeiro = null;
            this.ultimo = null;
        } else {
            this.ultimo = this.ultimo.anterior;
            this.ultimo.proximo = null;
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

        NoDuplo<T> alvo = noDaPosicao(posicao);
        NoDuplo<T> antes = alvo.anterior;
        NoDuplo<T> depois = alvo.proximo;

        antes.proximo = depois;
        depois.anterior = antes;

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
        NoDuplo<T> no = noDaPosicao(posicao);
        if (no == null) return false;
        no.dado = dado;
        return true;
    }


    
    
    // PERCORRER
    
    

    // imprimirFrente()
    // Mostra os itens do primeiro para o ultimo.
    public void imprimirFrente() {
        System.out.println("Lista dupla (" + this.quantidade + " itens) - do inicio ao fim:");

        if (estaVazia()) {
            System.out.println("   (vazia)");
            return;
        }

        NoDuplo<T> atual = this.primeiro;
        int posicao = 0;
        while (atual != null) {
            System.out.println("   [" + posicao + "] " + atual.dado);
            atual = atual.proximo;
            posicao++;
        }
    }

    // imprimirTras()
    // Mostra os itens do ultimo para o primeiro. So a lista dupla faz isso.
    public void imprimirTras() {
        System.out.println("Lista dupla (" + this.quantidade + " itens) - do fim ao inicio:");

        if (estaVazia()) {
            System.out.println("   (vazia)");
            return;
        }

        NoDuplo<T> atual = this.ultimo;
        int posicao = this.quantidade - 1;
        while (atual != null) {
            System.out.println("   [" + posicao + "] " + atual.dado);
            atual = atual.anterior;
            posicao--;
        }
    }

    // inverter()
    // Vira a lista do avesso: troca as duas setas de cada no de lugar.
    public void inverter() {
        NoDuplo<T> atual = this.primeiro;

        while (atual != null) {
            NoDuplo<T> guardaProximo = atual.proximo;
            atual.proximo = atual.anterior;
            atual.anterior = guardaProximo;
            atual = guardaProximo;
        }

        NoDuplo<T> guardaPrimeiro = this.primeiro;
        this.primeiro = this.ultimo;
        this.ultimo = guardaPrimeiro;
    }


    
    
    // AUXILIAR
    
    

    // noDaPosicao(posicao)
    // Acha o no daquela posicao. Comeca pela ponta mais perto para andar menos.
    private NoDuplo<T> noDaPosicao(int posicao) {
        if (posicao < 0 || posicao >= this.quantidade) return null;

        if (posicao < this.quantidade / 2) {
            NoDuplo<T> atual = this.primeiro;
            for (int i = 0; i < posicao; i++) {
                atual = atual.proximo;
            }
            return atual;
        } else {
            NoDuplo<T> atual = this.ultimo;
            for (int i = this.quantidade - 1; i > posicao; i--) {
                atual = atual.anterior;
            }
            return atual;
        }
    }


    
    
    // O NO
    
    

    // NoDuplo
    // A caixa que guarda um dado e duas setas: anterior e proximo.
    private static class NoDuplo<T> {
        T dado;
        NoDuplo<T> anterior;
        NoDuplo<T> proximo;

        NoDuplo(T dado) {
            this.dado = dado;
            this.anterior = null;
            this.proximo = null;
        }
    }


    
    
    // TESTE
    
    

    public static void main(String[] args) {

        ListaDupla<String> lista = new ListaDupla<String>();

        System.out.println("--- CRIAR ---");
        System.out.println("estaVazia() = " + lista.estaVazia());

        System.out.println();
        System.out.println("--- INSERIR ---");
        lista.addFinal("Bruno");        System.out.println("addFinal(Bruno)");
        lista.addFinal("Maria");        System.out.println("addFinal(Maria)");
        lista.addInicio("Arthur");      System.out.println("addInicio(Arthur)");
        lista.addNaPosicao(2, "Carla"); System.out.println("addNaPosicao(2, Carla)");
        System.out.println();
        lista.imprimirFrente();

        System.out.println();
        System.out.println("--- CONSULTAR ---");
        System.out.println("tamanho()        = " + lista.tamanho());
        System.out.println("primeiro()       = " + lista.primeiro());
        System.out.println("ultimo()         = " + lista.ultimo());
        System.out.println("obter(1)         = " + lista.obter(1));
        System.out.println("posicaoDe(Carla) = " + lista.posicaoDe("Carla"));
        System.out.println("contem(Maria)    = " + lista.contem("Maria"));

        System.out.println();
        System.out.println("--- PERCORRER NOS DOIS SENTIDOS ---");
        lista.imprimirFrente();
        System.out.println();
        lista.imprimirTras();

        System.out.println();
        System.out.println("--- ALTERAR ---");
        lista.atualizar(0, "Arthur Silva");
        System.out.println("atualizar(0, Arthur Silva)");
        System.out.println();
        lista.imprimirFrente();

        System.out.println();
        System.out.println("--- INVERTER ---");
        lista.inverter();
        System.out.println("inverter()");
        System.out.println();
        lista.imprimirFrente();

        System.out.println();
        System.out.println("--- REMOVER ---");
        System.out.println("removeInicio()     -> saiu: " + lista.removeInicio());
        System.out.println("removeFinal()      -> saiu: " + lista.removeFinal());
        System.out.println("removeNaPosicao(0) -> saiu: " + lista.removeNaPosicao(0));
        System.out.println();
        lista.imprimirFrente();

        System.out.println();
        System.out.println("--- LIMPAR ---");
        lista.limpar();
        System.out.println("limpar()");
        System.out.println();
        lista.imprimirFrente();
    }
}
