public class ServicoSemEnjoos implements ServicoABordo{
    private String descricao;

    public ServicoSemEnjoos(String descricao) {
        this.descricao = descricao;
    }

    public ServicoSemEnjoos() {
        this("Servico sem enjoos.");
    }

    @Override
    public String getDescricaoServico() {
        return descricao;
    }
}
