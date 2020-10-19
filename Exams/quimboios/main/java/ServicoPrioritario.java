public class ServicoPrioritario implements ServicoABordo {
    private String descricao;

    public ServicoPrioritario(String descricao) {
        this.descricao = descricao;
    }

    public ServicoPrioritario() {
        this("Servico prioritario.");
    }

    @Override
    public String getDescricaoServico() {
        return descricao;
    }
}
