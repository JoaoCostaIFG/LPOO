import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Comboio {
    protected ServicoABordo servicoABordo;
    protected String nome;
    protected List<Carruagem> carruagens = new ArrayList<>();
    protected int numPassageiros = 0;

    public Comboio(String nome) {
        this.nome = nome;
        this.servicoABordo = new ServicoRegular();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void addCarruagem(Carruagem c) {
        this.carruagens.add(c);
    }

    public int getNumCarruagens() {
        return carruagens.size();
    }

    public Carruagem getCarruagemByOrder(int i) {
        return carruagens.get(i);
    }

    public int getNumLugares() {
        int ret = 0;
        for (Carruagem c : carruagens)
            ret += c.getNumLugares();
        return ret;
    }

    public void removeAllCarruagens(int cap) {
        this.carruagens.removeIf(c -> (c.getNumLugares() == cap));
    }

    public int getNumPassageiros() {
        return numPassageiros;
    }

    protected int getFreeSpots() {
        int freeSpots = 0;
        for (Carruagem c : carruagens)
            freeSpots += c.getNumLugares();
        return freeSpots;
    }

    public int getLugaresLivres() {
        return getFreeSpots() - numPassageiros;
    }

    public void addPassageiros(int n) throws PassengerCapacityExceeded {
        int freeSpots = getFreeSpots();

        if (n > freeSpots)
            throw new PassengerCapacityExceeded();

        this.numPassageiros += n;
    }

    public void removePassageiros(int n) {
        this.numPassageiros = Math.max(0, numPassageiros - n);
    }

    public void removePassageiros() {
        this.numPassageiros = 0;
    }

    @Override
    public String toString() {
        return "Comboio " + nome + ", " +
                getNumCarruagens() + (getNumCarruagens() != 1 ? " carruagens, " : " carruagem, ") +
                getFreeSpots() + (getFreeSpots() != 1 ? " lugares, " : " lugar, ") +
                numPassageiros + (numPassageiros != 1 ? " passageiros" : " passageiro");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comboio comboio = (Comboio) o;

        if (comboio.getNumCarruagens() != this.getNumCarruagens())
            return false;

        for (int i = 0; i < getNumCarruagens(); ++i) {
            if (getCarruagemByOrder(i).getNumLugares() != comboio.getCarruagemByOrder(i).getNumLugares())
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(carruagens);
    }

    public ServicoABordo getServicoABordo() {
        return servicoABordo;
    }

    public String getDescricaoServico() {
        return servicoABordo.getDescricaoServico();
    }

    public void setServicoABordo(ServicoABordo newServico) {
        this.servicoABordo = newServico;
    }
}
