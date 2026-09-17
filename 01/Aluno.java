/**
 * ALUNO
 *
 * O molde do objeto. E o dado que vai dentro dos nos da lista.
 */
public class Aluno {

    private String nome;
    private int idade;
    private int turma;

    // Construtor: recebe os dados e deixa o objeto pronto
    public Aluno(String nome, int idade, int turma) {
        this.nome = nome;
        this.idade = idade;
        this.turma = turma;
    }

    // Getters: leem os dados
    public String getNome() { return this.nome; }
    public int getIdade()   { return this.idade; }
    public int getTurma()   { return this.turma; }

    // Setters: mudam os dados
    public void setNome(String nome) { this.nome = nome; }
    public void setIdade(int idade)  { this.idade = idade; }
    public void setTurma(int turma)  { this.turma = turma; }

    // toString: ensina o objeto a virar texto na hora de imprimir
    @Override
    public String toString() {
        return this.nome + ", " + this.idade + " anos, turma " + this.turma;
    }
}
