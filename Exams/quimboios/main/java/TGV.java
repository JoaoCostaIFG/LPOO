public class TGV extends Comboio {
    public TGV(String nome) {
        super(nome);
        this.servicoABordo = new ServicoPrioritario();
    }

    @Override
    public String toString() {
        return "TGV " + nome + ", " +
                getNumCarruagens() + (getNumCarruagens() != 1 ? " carruagens, " : " carruagem, ") +
                getFreeSpots() + (getFreeSpots() != 1 ? " lugares, " : " lugar, ") +
                numPassageiros + (numPassageiros != 1 ? " passageiros" : " passageiro");
    }
}
