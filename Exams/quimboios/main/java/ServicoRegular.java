public class ServicoRegular implements ServicoABordo {
    private String descricao;

    public ServicoRegular(String descricao) {
        this.descricao = descricao;
    }

    public ServicoRegular() {
        this("Servico regular.");
    }

    @Override
    public String getDescricaoServico() {
        return descricao;
    }
}
